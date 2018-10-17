package org.fluentcodes.projects.elasticobjects.eo;

/**
 * Created by Werner on 31.08.2016.
 */
public enum JSONSerializationType {
    STANDARD("json"), EO("jsn"), SCALAR("jsns");

    private final String fileExtension;

    JSONSerializationType(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }
}
