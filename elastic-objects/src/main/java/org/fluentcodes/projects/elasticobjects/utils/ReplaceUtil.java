/**
 * <p>
 * Some static utility classes....
 * <p>
 * http://www.jdbee.org<br/>
 * date 10.3.2012<br/>
 *
 * @author Werner Diwischek
 * @version 0.1
 */
package org.fluentcodes.projects.elasticobjects.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.EO;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReplaceUtil {
    private static final Logger LOG = LogManager.getLogger(ReplaceUtil.class);
    private static final Pattern varPattern = Pattern.compile("\\$\\[(.*?)\\]");
    private static final Map<String, String> SYSTEM = populateSystem();
    private static SimpleDateFormat dayTimeFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");
    private static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");

    private static final Map<String, String> populateSystem() {
        Map<String, String> result = new HashMap<>();
        if (System.getenv("TMPDIR") != null) {
            result.put("TMP", System.getenv("TMPDIR"));
        } else if (System.getenv("TMPDIR") != null) {
            result.put("TMP", System.getenv("TMPDIR"));
        }

        if (System.getenv("HOMEPATH") != null) {
            result.put("HOME", System.getenv("HOMEPATH"));
        }
        return result;
    }

    public static String replace(String toParse) {
        return replace(toParse, null, null);
    }


    /**
     * Replaces ${param}br/>
     * changing the static final varPattern you can use the var you prefer
     * varPattern = Pattern.compile("${}(.*?)]"); would look for $param
     *
     * @param toParse
     * @return
     */

    public static String replace(final String toParse, final EO adapter) {
        return replace(toParse, adapter, null);
    }

    public static String replace(final String toParse, Map<String, String> attributes) {
        return replace(toParse, null, attributes);
    }

    public static String replace(final String toParse, final EO adapter, Map<String, String> attributes) {
        if (toParse == null || toParse.isEmpty()) {
            return "";
        }
        if (!toParse.contains("[")) {
            LOG.debug("no placeholder $[ $# $! found  : " + toParse);
            return toParse;
        }

        StringBuffer result = new StringBuffer();
        String parseString = toParse;
        Matcher match = varPattern.matcher(parseString);
        int start = 0;
        while (match.find()) {
            int findStart = match.start();
            result.append(parseString.substring(start, findStart));
            start = match.end();
            String value = match.group();

            String pathOrKey = match.group(1);
            // default value when no value could be derived
            if (pathOrKey.contains("|")) {
                String[] pathAndDefault = pathOrKey.split("\\|");
                if (pathAndDefault.length == 1) {
                    value = "";
                } else {
                    value = pathAndDefault[1];
                }
                pathOrKey = pathAndDefault[0];

            }
            pathOrKey = pathOrKey.replaceAll("\\\\", "/");
            if (pathOrKey.startsWith("!")) {
                pathOrKey = pathOrKey.replaceAll("^!", "");
                try {
                    final Map valueAttributes = new HashMap();
                    valueAttributes.putAll(attributes);
                    valueAttributes.put(EO_STATIC.A_EXECUTE, pathOrKey);
                    //ExecutorValues executor = new ExecutorValues(valueAttributes);
                    //value = executor.execute(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    adapter.warn(e.getMessage());
                    continue;
                }
            } else if (getSystemValue(pathOrKey) != null) {
                value = getSystemValue(pathOrKey);
            } else if (System.getenv(pathOrKey) != null) {
                value = System.getenv(pathOrKey);
            } else if (attributes != null && attributes.size() > 0 && attributes.get(pathOrKey) != null) {
                value = attributes.get(pathOrKey);
            } else if (adapter != null) {
                Object valueObject = null;
                try {
                    valueObject = adapter.get(pathOrKey);
                    if (valueObject != null) {
                        value = ScalarConverter.toString(valueObject.toString());
                    }
                } catch (Exception e) {
                    adapter.debug(e.getMessage());
                }
            }

            result.append(value);
        }
        result.append(parseString.substring(start, parseString.length()));
        return result.toString();
    }

    public static final String getSystemValue(String key) {
        if (key == null || key.isEmpty()) {
            return null;
        }
        if (SYSTEM.get(key) != null) {
            return SYSTEM.get(key);
        }
        if (key.equals("TMP")) {
            return SYSTEM.get("TEMP");
        }
        if (key.equals("TEMP")) {
            return SYSTEM.get("TMP");
        }
        return null;
    }


}
