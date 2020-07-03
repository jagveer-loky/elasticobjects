package org.fluentcodes.projects.elasticobjects.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Werner on 17.07.2014.
 */

public class ScalarConverter {
    private static final Logger LOG = LogManager.getLogger(ScalarConverter.class);
    private static final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static final SimpleDateFormat myDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat zuluDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final String regExpDate = "[\\d]{4,4}-[\\d]{2,2}-[\\d]{2,2}T[\\d]{2,2}:[\\d]{2,2}:[\\d]{2,2}[.]*[\\d]{0,3}(Z*)";
    private static final Pattern patternDate = Pattern.compile(regExpDate);

    private static final Map<String, SimpleDateFormat> dateFormatter = createDateFormatter();

    private static Map<String, SimpleDateFormat> createDateFormatter() {
        String weekdays = "(Mon|Tue|Wed|Thu|Fri|Sat|Sun)";
        String months = "(Jan|Feb|Mar|Apr|Mai|Jun|Jul|Aug|Sep|Oct|Nov|Dec)";
        Map<String, SimpleDateFormat> formatter = new HashMap<String, SimpleDateFormat>();
        formatter.put("[\\d]{4,4}-[\\d]{2,2}-[\\d]{2,2} [\\d]{2,2}:[\\d]{2,2}:[\\d]{2,2}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        formatter.put("[\\d]{4,4}-[\\d]{2,2}-[\\d]{2,2}T[\\d]{2,2}:[\\d]{2,2}:[\\d]{2,2}", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        formatter.put("[\\d]{4,4}-[\\d]{2,2}-[\\d]{2,2}T[\\d]{2,2}:[\\d]{2,2}:[\\d]{2,2}[.]+[\\d]{0,3}(Z)", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        //http://www.fileformat.info/tip/java/simpledateformat.htm
        formatter.put(weekdays + ", [\\d]{2,2} " + months + " [\\d]{4,4} [\\d]{2,2}:[\\d]{2,2}:[\\d]{2,2} [\\w]+", new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z"));//RSS 2.0
        formatter.put("[\\d]{4,4}-[\\d]{2,2}-[\\d]{2,2}T[\\d]{2,2}:[\\d]{2,2}:[\\d]{2,2}[.][\\w]+", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz"));  //Atom (ISO 8601)
        //GERMAN
        formatter.put("[\\d]{2,2}.[\\d]{2,2}.[\\d]{4,4} [\\d]{2,2}:[\\d]{2,2}:[\\d]{2,2}", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"));
        formatter.put("[\\d]{2,2}.[\\d]{2,2}.[\\d]{4,4} [\\d]{2,2}:[\\d]{2,2}:[\\d]{2,2} [\\w]+", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z"));
        formatter.put("[\\d]{2,2}.[ ]*[\\d]{2,2}.[ ]*[\\d]{4,4}", new SimpleDateFormat("dd.MM.yyyy"));
        formatter.put("[\\d]{2,2}.[ ]*[\\d]{2,2}.[ ]*[\\d]{4,4} [\\w]+", new SimpleDateFormat("dd.MM.yyyy z"));
        //Java to getSerialized
        formatter.put(weekdays + " " + months + " [\\d]{2,2} [\\d]{2,2}:[\\d]{2,2}:[\\d]{2,2} [\\w]+ [\\d]{4,4}", new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US));//RSS 2.0
        //http://tutorials.jenkov.com/java-date-time/java-util-timezone.html
        return formatter;
    }

    public static Object transformToJSON(Object source) {
        if (source == null) {
            return null;
        }
        if ((source instanceof String) || (source instanceof Boolean)) {
            return source;
        }
        if (source instanceof byte[]) {
            //TODO
            //return toJSONArray(source);
        }
        if ((source instanceof Number)) {
            String serialized = source.toString();
            if (serialized.endsWith(".0")) {
                serialized = serialized.replaceAll(".0", "");
                if (((Number) source).longValue() > Integer.MAX_VALUE) {
                    return new Long(serialized);
                }
                return new Integer(serialized);
            }
            if (serialized.contains(".")) {
                return new Double(serialized);
            }
            if (((Number) source).longValue() > Integer.MAX_VALUE) {
                return new Long(serialized);
            }
            return new Integer(serialized);
        }
        if ((source instanceof Class)) {
            return ((Class) source).getName();
        }
        if ((source instanceof StringBuffer)) {
            return source.toString();
        }
        if ((source instanceof Date)) {
            return source.toString();
        }
        return source.toString();
    }

    /**
     * Helper to createHost a source value to a certain clazz in a default way.
     *
     * @param mapClass
     * @param source
     * @return
     */
    public static Object transformScalar(Class<?> mapClass, Object source)  {
        if (mapClass == null || mapClass == Object.class) {
            return source;
        }
        if (mapClass == String.class) {
            return toString(source);
        } else if (mapClass == Date.class) {
            return toDate(source);

        } else if (mapClass == Boolean.class) {
            return toBoolean(source);
        } else if (mapClass == Float.class) {
            return toFloat(source);
        } else if (mapClass == Double.class) {
            return toDouble(source);
        } else if (mapClass == Long.class) {
            return toLong(source);
        } else if (mapClass == Integer.class) {
            return toInt(source);
        } else if (mapClass == Short.class) {
            return toShort(source);
        } else if (mapClass == Class.class) {
            return toClass(source);
        } else if (mapClass.getName().equals("[B")) {
            return getBinaryValue(source);
        } else if (mapClass.isEnum()) {
            try {
                if (source instanceof String) {
                    return mapClass.getDeclaredMethod("valueOf", String.class).invoke(null, source);
                }
                if (source.getClass() == mapClass) {
                    return source;
                }
                return null;
            } catch (Exception e) {
                //e.printStackTrace();
                LOG.warn("Problem transforming enum class with " + source + ": " + e.getMessage());
                return null;
            }
        } else {
            LOG.info("Could not map scalar value! source: \nsourceClazz=" + source.getClass().getSimpleName() + " \nmapClazz=" + mapClass.getSimpleName());
            return null;
        }
    }

    public static Object transform(String value) {
        // Setter call would not work with a null vlue
        if (value.startsWith("(")) {
            if (value.matches("^\\(.*\\).*")) {
                String myClass = value.replaceAll("\\).*", "");
                String myValue = value.replaceAll(".*\\)", "");
                if (myClass.equals("(Date")) {
                    myClass = "java.util.Date";
                } else {
                    myClass = "java.lang." + myClass.replaceAll("\\(", "");
                }
                try {
                    Class mappingClass = Class.forName(myClass);
                    if (mappingClass == value.getClass()) {
                        return myValue;
                    }
                    return transform(mappingClass, myValue);
                } catch (Exception e) {
                    return value;
                }
            }
        }
        return value;
    }


    /**
     * Will add elementary instance rows
     *
     * @param mapClass Class to be mapped
     * @param source
     * @return
     * @throws IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    public static Object transform(Class<?> mapClass, Object source)  {
        // Setter call would not work with a null vlue
        if (mapClass == null || mapClass == Object.class) {
            return source;
        }
        //TODO if (((ItemsCache)getConfigsCache()).getModelConfig(mapClass).isScalar()) {
        return transformScalar(mapClass, source);
        //}
//        Object mappedValue = null;
//        if (source ==null) {
//            return mappedValue;
//        }
//        String stringValue = null;
//        if (source instanceof String) {
//            stringValue= (String) source;
//        }
//        LOG.debug("Field with type " + mapClass.getName() + " value type=" + source.getClass().getName() + " value=" + source);
//        if (stringValue.equals("")) {
//            return null;
//        }
//      if (mapClass.getName().equals("byte[]")) {
//            if (source instanceof String) {
//                mappedValue = stringValue.getBytes();
//            }
//        }
//
//
//        else if (mapClass==Boolean.class) {
//            if (source instanceof String) {
//                String xValue = (String) source;
//                if (xValue.equals("true")) {
//                    mappedValue =  new Boolean(true);
//                } else if (xValue.equals("false")) {
//                    mappedValue =  new Boolean(false);
//                } else if (xValue.equals("")) {
//                    mappedValue =  new Boolean(false);
//                } else {
//                    LOG.warn("Could not add value " + source);
//                    mappedValue =  new Boolean(false);
//                }
//            }
//            else if (source instanceof Double) {
//                long val = Math.round((Double) source);
//                if (val == 1) {
//                    mappedValue = new Boolean(true);
//                } else if (val == 0) {
//                    mappedValue =  new Boolean(false);
//                } else {
//                    LOG.warn("Could not add value " + source + " - " + val);
//                    mappedValue =  new Boolean(false);
//                }
//            }
//        }
//        else {
//            mappedValue=source;
//        }
//        if (mappedValue==null) {
//            LOG.warn("Could not map value " + source + " " + source.getSerialized() + " " + source.getClass().getName() + " " + mapClass.getName());
//        }
//        return mappedValue;
    }

    public static List toJSONArray(Object source) {
        if (source == null) {
            return null;
        }
        List result = new ArrayList();
        if (!(source instanceof byte[])) {
            result.add(source);
            return result;
        }
        for (byte b : (byte[]) source) {
            result.add(b);
        }
        return result;
    }

    public static byte[] getBinaryValue(Object source) {
        if (source == null) {
            return null;
        }
        if (source instanceof byte[]) {
            return (byte[]) source;
        }
        if (source instanceof String) {
            return ((String) source).getBytes();
        }
        if (source instanceof List) {
            List array = (ArrayList) source;
            byte[] jsonBytes = new byte[array.size()];
            for (int i = 0; i < array.size(); i++) {
                //TODO
                //jsonBytes[i]=Byte.parseByte(array.find(i));
            }
            return jsonBytes;
//            byte[] result = new byte[((JSONArray)source).length()];
//            for (int i=0; i<((JSONArray)source).length();i++) {
//                result[i]=((JSONArray)source).optInt(i);
//            }
//            result = source.getBytes();
        }
        return null;
    }

    public static Class toClass(Object source) {
        if (source == null) {
            return null;
        }
        if (source instanceof Class) {
            return (Class) source;
        }
        if (!(source instanceof String)) {
            return source.getClass();
        }
        try {
            return Class.forName((String) source);
        } catch (Exception e) {
            LOG.warn("Problem find stringified mapping class " + source + ": " + e.getMessage());
            return null;
        }
    }


    public static String toString(Object source) {
        if (source == null) {
            return null;
        }
        if (source instanceof String) {
            return new String((String) source);
        }
        if (source instanceof Date) {
            return new Long(((Date) source).getTime()).toString();
        }
        if (source instanceof byte[]) {
            return new String((byte[]) source);
        } else {
            return source.toString();
        }
    }

    public static Date toDate(Object source) {
        if (source instanceof Timestamp) {
            Timestamp time = (Timestamp) source;
            Date date = new Date(time.getTime());
            return date;
        }
        if (source instanceof Date) {
            return new Date(((Date) source).getTime());
        }
        if (source instanceof String) {
            String sourceS = (String) source;
            if (sourceS.matches("[\\+\\-]*[\\d]+")) {
                return new Date(toLong(sourceS));
            }
            for (String pattern : dateFormatter.keySet()) {
                if (sourceS.matches(pattern)) {
                    try {
                        return dateFormatter.get(pattern).parse(sourceS);
                    } catch (ParseException e) {
                        LOG.error("Date ParseException for  " + source + " and pattern " + pattern);
                        return null;
                    }
                }
            }
        }
        if (source instanceof Long) {
            //LOG.info("Now use 0 " + type + " " + valueType);
            return new Date((Long) source);
        }
        if (source instanceof Double) {
            //LOG.info("Now use 0 " + type + " " + valueType);
            Double doubleValue = (Double) source;
            return new Date(doubleValue.longValue());
        }
        return null;
    }

    public static Boolean toBoolean(Object source) {
        if (source == null) {
            return false;
        }
        if (source instanceof Boolean) {
            return new Boolean((Boolean) source);
        }
        if (source instanceof String) {
            if (((String) source).matches("^true|1$")) {
                return true;
            } else if (((String) source).matches("^false|0|$")) {
                return false;
            } else {
                return false;
            }
        }
        if (source instanceof Integer) {
            if (((Integer) source) == 1) {
                return true;
            } else if (((Integer) source) == 0) {
                return false;
            } else {
                return false;
            }
        }
        return false;
    }

    public static Float toFloat(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Float) {
            return new Float((Float) value);
        }

        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }

        if (value instanceof String) {
            if (((String) value).matches("^[\\d]+[\\.,]*[\\d]*$")) {
                return Float.parseFloat(cleanNumberForParse((String) value));
            }
        }
        return null;

    }


    public static Double toDouble(Object value) {
        if (value instanceof Double) {
            return new Double((Double) value);
        }
        if (value instanceof String) {
            String check = (String) value;
            if (check.matches("^[\\d]+[\\.,]*[\\d]*$")) {
                return Double.parseDouble(cleanNumberForParse(check));
            } else {
                return null;
            }
        }

        if (value instanceof Integer) {
            return new Double((Integer) value);
        }
        if (value instanceof Long) {
            return new Double((Long) value);
        }
        if (value instanceof Short) {
            return new Double((Short) value);
        }
        if (value instanceof Float) {
            return new Double(value.toString());
        }
        return null;
    }

    private static String cleanNumberForParse(String toParse) {
        if (toParse.contains(",")) {
            return toParse.replaceAll(",", ".");
        }
        return toParse;
    }

    public static Integer toInt(Object value)  {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            String check = (String) value;
            if (check.matches("^[\\d]+$")) {
                return Integer.parseInt(check);
            } else if (check.matches("^[\\d]+[\\.,]*[\\d]*$")) {
                value = toDouble(value);
            } else {
                return null;
            }
        }
        if (value == null) {
            return null;
        }
        if (!(value instanceof Number)) {
            throw new EoException("Could not transform to integer since value is neither String nor parsable String nor Number with value='" + value.toString() + "': " + value.getClass());
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Long) {
            return ((Long) value).intValue();
        }
        if (value instanceof Short) {
            return ((Short) value).intValue();
        }
        if (value instanceof Float) {
            return ((Float) value).intValue();
        }
        if (value instanceof Double) {
            return ((Double) value).intValue();
        }
        //mappedValue = new Long(Math.round((Float)valueObject)).intValue();
        //mappedValue = new Long(Math.round((Double)valueObject)).intValue();
        return null;
    }

    public static Short toShort(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Short) {
            return new Short((Short) value);
        }
        if (value instanceof String) {
            String check = (String) value;
            if (check.matches("^[\\d]+$")) {
                return Short.parseShort(check);
            } else if (check.matches("^[\\d]+[\\.,]*[\\d]*$")) {
                value = toDouble(value);
            } else {
                return null;
            }
        }
        if (value == null) {
            return null;
        }
        if (!(value instanceof Number)) {
            return null;
        }
        if (value instanceof Integer) {
            return ((Integer) value).shortValue();
        }
        if (value instanceof Long) {
            return ((Long) value).shortValue();
        }

        if (value instanceof Float) {
            return ((Float) value).shortValue();
        }
        if (value instanceof Double) {
            return ((Double) value).shortValue();
        }
        //mappedValue = new Long(Math.round((Float)valueObject)).intValue();
        //mappedValue = new Long(Math.round((Double)valueObject)).intValue();
        return null;
    }


    public static Long toLong(Object value) {
        if (value instanceof String) {
            String check = (String) value;
            if (check.matches("[\\+\\-]*[\\d]+")) {
                return Long.parseLong(check);
            } else if (check.matches("^[\\d]+[\\.,]*[\\d]*$")) {
                value = toDouble(value);
            } else {
                return null;
            }
        }
        if (!(value instanceof Number)) {
            return null;
        }
        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }
        if (value instanceof Long) {
            return new Long(((Long) value));
        }
        if (value instanceof Short) {
            return ((Short) value).longValue();
        }
        if (value instanceof Float) {
            return ((Float) value).longValue();
        }
        if (value instanceof Double) {
            return ((Double) value).longValue();
        }
        return null;
    }
}

