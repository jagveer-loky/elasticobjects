package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class InterpreterValue {

    static final String MARK_VALUE = ".";
    private static final String SYSTEM_CHAR = "@";
    private static final String ENV_CHAR = "%";
    private static final Map<String, String> SYSTEM = populateSystem();

    private static final String PARENT = "_parent";
    private static final Pattern PARENT_PATTERN = Pattern.compile(PARENT);
    private static final String VALUE = "_value";

    private static final String HOME = "HOME";
    private static final String HOMEPATH = "HOMEPATH";
    private static final String TMP = "TMP";
    private static final String TMPDIR = "TMPDIR";
    private InterpreterValue() {}


    public static String replace(final EO eo, String pathOrKey) {
        if (pathOrKey.startsWith(SYSTEM_CHAR)) {
            return getSystemValue(pathOrKey.replace(SYSTEM_CHAR, ""));
        }
        if (pathOrKey.startsWith(ENV_CHAR)) {
            return System.getenv(pathOrKey.replace(ENV_CHAR, ""));
        }
        if (eo == null) {
            throw new EoException("Null eo wrapper defined to get '" + pathOrKey + "'");
        }
        if (pathOrKey.equals(VALUE)) {
            return eo.get().toString();
        }
        if (pathOrKey.equals(PARENT)) {
            return eo.getFieldKey();
        }
        pathOrKey = PARENT_PATTERN.matcher(pathOrKey).replaceAll(eo.getFieldKey());
        Object value = eo.get(pathOrKey);
        if (value instanceof Date) {
            return value.toString();
        }
        return ScalarConverter.toString(value);
    }

    private static final Map<String, String> populateSystem() {
        Map<String, String> result = new HashMap<>();
        if (System.getenv(TMPDIR) != null) {
            result.put(TMP, System.getenv(TMPDIR));
        } else if (System.getenv(TMP) != null) {
            result.put("TMP", System.getenv(TMP));
        }
        if (System.getenv(HOMEPATH) != null) {
            result.put(HOME, System.getenv(HOMEPATH));
        }
        return result;
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
