package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

public class ValueParamsHelper {
    protected static void checkValues(int position, Object... values) throws Exception {
        checkValues(values);
        if (values.length < position + 1) {
            throw new Exception("Position " + position + " greater than length " + values.length);
        }
    }


    protected static void checkValues(Object... values) throws Exception {
        if (values == null || values.length == 0) {
            throw new Exception("No values are set");
        }
    }

    protected static void checkValuesMinimal(int min, Object... values) throws Exception {
        checkValues(values);
        if (values.length < min) {
            throw new Exception("Minimal value size " + min + " is not reached but " + values.length);
        }
    }

    protected static void checkValuesAdapter(int position, Object... values) throws Exception {
        checkValues(position, values);
        if (!(values[position] instanceof EO)) {
            throw new Exception("Value at " + position + " is not instance of MODULE_NAME but " + values[0].getClass().getSimpleName() + ".");
        }
    }

    protected static void checkValuesString(int position, Object... values) throws Exception {
        checkValues(position, values);
        if (!(values[position] instanceof String)) {
            throw new Exception("Value at " + position + " is not instance of String but " + values[0].getClass().getSimpleName());
        }
    }

    protected static String getString(int position, Object... values) throws Exception {
        checkValuesString(position, values);
        return (String) values[position];
    }

    protected static String getStringWithDefault(int position, String defaultValue, Object... values) {
        try {
            checkValuesString(position, values);
        } catch (Exception e) {
            return defaultValue;
        }
        return (String) values[position];
    }

    protected static Integer getInt(int position, Object... values) throws Exception {
        checkValues(position, values);
        return ScalarConverter.toInt(values[position]);
    }

    protected static Double getDouble(int position, Object... values) throws Exception {
        checkValues(position, values);
        return ScalarConverter.toDouble(values[position]);
    }

    protected static Boolean getBoolean(int position, Object... values) throws Exception {
        checkValues(position, values);
        return ScalarConverter.toBoolean(values[position]);
    }

    protected static EO getAdapter(int position, Object... values) throws Exception {
        checkValuesAdapter(position, values);
        return (EO) values[position];
    }
}
