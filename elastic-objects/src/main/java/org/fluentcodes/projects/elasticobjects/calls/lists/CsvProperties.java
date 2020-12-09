package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ConfigProperties;

public interface CsvProperties extends Config, ConfigProperties {
    public static final String FIELD_DELIMITER = "fieldDelimiter";
    public static final String ROW_DELIMITER = "rowDelimiter";
    public static final String DEFAULT_FIELD_DELIMITER = ";";
    public static final String DEFAULT_ROW_DELIMITER = "\n";
    default boolean hasFieldDelimiter() {
        return getFieldDelimiter()!=null && !getFieldDelimiter().isEmpty();
    }

    default String getFieldDelimiter() {
        return hasProperties()&&getProperties().containsKey(FIELD_DELIMITER)?
                (String)getProperties().get(FIELD_DELIMITER):
                DEFAULT_FIELD_DELIMITER;
    }

    default void setFieldDelimiter(final String value) {
        if (hasProperties()) {
            getProperties().put(value, FIELD_DELIMITER);
        }
    }

    default boolean hasRowDelimiter() {
        return getRowDelimiter()!=null && !getRowDelimiter().isEmpty();
    }

    default String getRowDelimiter() {
        return hasProperties()&&getProperties().containsKey(ROW_DELIMITER)?
                (String)getProperties().get(ROW_DELIMITER):
                DEFAULT_ROW_DELIMITER;
    }

    default void setRowDelimiter(final String value) {
        if (hasProperties()) {
            getProperties().put(value, ROW_DELIMITER);
        }
    }
}
