package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;

public interface CsvConfigInterface extends ConfigInterface {
    public static final String F_FIELD_DELIMITER = "fieldDelimiter";
    public static final String F_ROW_DELIMITER = "rowDelimiter";
    public static final String F_DEFAULT_FIELD_DELIMITER = ";";
    public static final String F_DEFAULT_ROW_DELIMITER = "\n";

    default boolean hasFieldDelimiter() {
        return getFieldDelimiter()!=null && !getFieldDelimiter().isEmpty();
    }

    default String getFieldDelimiter() {
        return hasProperties()&&getProperties().containsKey(F_FIELD_DELIMITER)?
                (String)getProperties().get(F_FIELD_DELIMITER):
                F_DEFAULT_FIELD_DELIMITER;
    }

    default void setFieldDelimiter(final String value) {
        if (hasProperties()) {
            getProperties().put(value, F_FIELD_DELIMITER);
        }
    }

    default boolean hasRowDelimiter() {
        return getRowDelimiter()!=null && !getRowDelimiter().isEmpty();
    }

    default String getRowDelimiter() {
        return hasProperties()&&getProperties().containsKey(F_ROW_DELIMITER)?
                (String)getProperties().get(F_ROW_DELIMITER):
                F_DEFAULT_ROW_DELIMITER;
    }

    default void setRowDelimiter(final String value) {
        if (hasProperties()) {
            getProperties().put(value, F_ROW_DELIMITER);
        }
    }
}
