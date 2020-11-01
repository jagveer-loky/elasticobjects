package org.fluentcodes.projects.elasticobjects.models;

public interface ModelConfigDbProperties extends Config {
    String ID_KEY = "idKey";
    String NATURAL_KEYS = "naturalKeys";
    String TABLE = "table";
    
    default boolean hasTable() {
        return getTable()!=null && !getTable().isEmpty();
    }

    default String getTable() {
        return hasProperties() ? (String) getProperties().get(TABLE) : null;
    }

    default boolean hasIdKey() {
        return getIdKey()!=null && !getIdKey().isEmpty();
    }

    default String getIdKey() {
        return hasProperties() ? (String) getProperties().get(ID_KEY) : null;
    }

    default boolean hasNaturalKeys() {
        return getNaturalKeys()!=null && !getNaturalKeys().isEmpty();
    }

    default String getNaturalKeys() {
        return hasProperties() ? (String) getProperties().get(NATURAL_KEYS) : null;
    }
}
