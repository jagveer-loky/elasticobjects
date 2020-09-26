package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

public interface PropertiesTemplateResourceStoreAccessor extends PropertiesTemplateResourceAccessor {
    public String TARGET_FILE = "targetFile";

    default boolean hasTargetFile() {
        return getTargetFile()!=null && getProperties().containsKey(TARGET_FILE);
    }

    default String getTargetFile() {
        return hasProperties() ? (String) getProperties().get(TARGET_FILE) : null;
    }

    default void setTargetFile(final String value) {
        if (hasProperties()) {
            getProperties().put(TARGET_FILE, value);
        }
    }
}
