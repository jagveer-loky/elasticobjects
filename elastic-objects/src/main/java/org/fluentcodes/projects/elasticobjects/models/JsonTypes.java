package org.fluentcodes.projects.elasticobjects.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JsonTypes {
    private static final Map<Class,String> TYPE_MAP = initTypeMap();
    private static final Map<Class,String> FORMAT_MAP = initFormatMap();

    private static Map<Class,String> initTypeMap() {
        Map<Class,String> typeMap = new HashMap<>();
        typeMap.put(String.class,"string");
        typeMap.put(Long.class,"integer");
        typeMap.put(Integer.class,"integer");
        typeMap.put(Float.class,"number");
        typeMap.put(Double.class,"number");
        typeMap.put(Date.class,"string");
        typeMap.put(LocalDate.class,"string");
        typeMap.put(LocalDateTime.class,"string");
        typeMap.put(Boolean.class,"boolean");
        typeMap.put(byte.class,"string");
        return typeMap;
    }
    private static Map<Class,String> initFormatMap() {
        Map<Class,String> formatMap = new HashMap<>();
        formatMap.put(Integer.class, "int32");
        formatMap.put(Long.class, "int62");
        formatMap.put(Short.class, "int32");
        formatMap.put(Float.class,"float");
        formatMap.put(Double.class,"double");
        formatMap.put(Date.class,"date-time");
        formatMap.put(LocalDateTime.class, "date-time");
        formatMap.put(LocalDate.class, "date");
        formatMap.put(byte.class,"byte");
        return formatMap;
    }

    public static String getType(final Class typeClass) {
        if (TYPE_MAP.containsKey(typeClass)) {
            return TYPE_MAP.get(typeClass);
        }
        return typeClass.getSimpleName();
    }

    public static boolean hasFormat(final Class typeClass) {
        return FORMAT_MAP.containsKey(typeClass);
    }

    public static String getFormat(final Class typeClass) {
        if (FORMAT_MAP.containsKey(typeClass)) {
            return FORMAT_MAP.get(typeClass);
        }
        return null;
    }

}
