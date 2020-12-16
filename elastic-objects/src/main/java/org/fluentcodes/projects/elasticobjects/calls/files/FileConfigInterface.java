package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.PermissionConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ConfigProperties;

/**
 * Created by Werner on 09.10.2016.
 */
public interface FileConfigInterface extends PermissionConfigInterface, ConfigProperties {
    String TEMPLATE = "template";

    Boolean getCached();
    default boolean hasCached() {
        return getCached()!=null;
    }
    String getFileName();
    default boolean hasFileName() {
        return getFileName()!=null && !getFileName().isEmpty();
    }
    String getFilePath();

    default boolean hasFilePath() {
        return getFilePath()!=null && !getFilePath().isEmpty();
    }

    String getHostConfigKey();

    default boolean hasHostConfigKey() {
        return getHostConfigKey()!=null && !getHostConfigKey().isEmpty();
    }

    default Boolean hasTemplate() {
        return isTemplate() != null;
    }
    default Boolean isTemplate() {
        return (Boolean) getProperties().get(TEMPLATE);
    }
    default FileConfigInterface getTemplate(Boolean template) {
        getProperties().put(TEMPLATE, template);
        return this;
    }

}
