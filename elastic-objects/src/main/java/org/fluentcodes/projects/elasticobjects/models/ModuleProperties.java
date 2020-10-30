package org.fluentcodes.projects.elasticobjects.models;

import static org.fluentcodes.projects.elasticobjects.models.Config.MODULE;

public interface ModuleProperties extends Properties {
    String CLASS_PATH = "classPath";
    default boolean hasClassPath() {
        return getClassPath()!=null && !getClassPath().isEmpty();
    }

    default String getClassPath() {
        return hasProperties()?(String)getProperties().get(CLASS_PATH):null;
    }

    default void setClassPath(final String value) {
        if (hasProperties()) {
            getProperties().put(value, CLASS_PATH);
        }
    }

    default boolean hasModule() {
        return getModule()!=null && !getModule().isEmpty();
    }

    default String getModule() {
        return hasProperties()?(String)getProperties().get(MODULE):null;
    }
}
