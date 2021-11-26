package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Date;
import java.util.regex.Pattern;

public class EoValueHandler {
    private static final String PARENT = "_parent";
    private static final Pattern PARENT_PATTERN = Pattern.compile(PARENT);
    private static final String VALUE = "_value";

    private EoValueHandler() {
    }

    public static String call(final EO eo, String pathOrKey) {
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
}
