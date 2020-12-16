package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.Permission;

import static org.fluentcodes.projects.elasticobjects.calls.files.FileConfigInterface.TEMPLATE;

/**
 * Created by Werner on 11.12.2020.
 */
public interface FileBeanInterface extends FileConfigInterface {
    FileBeanInterface setFileName(String value);
    default void mergeFileName(Object value) {
        if (value==null) {
            return;
        }
        if (hasFileName()) {
            return;
        }
        setFileName((String) value);
    }


    FileBeanInterface setFilePath(String value);
    default void mergeFilePath(Object value) {
        if (value==null) {
            return;
        }
        if (hasFilePath()) {
            return;
        }
        setFilePath((String) value);
    }


    FileBeanInterface setHostConfigKey(String value);
    default void mergeHostConfigKey(Object value) {
        if (value==null) {
            return;
        }
        if (hasHostConfigKey()) {
            return;
        }
        setHostConfigKey((String) value);
    }

    FileBeanInterface setCached(Boolean value);
    default void mergeCached(Object value) {
        if (value==null) {
            return;
        }
        if (hasCached()) {
            return;
        }
        setCached((Boolean) value);
    }

    default FileBeanInterface setTemplate(String value){
        getProperties().put(TEMPLATE, value);
        return this;
    }

    default void mergeTemplate(Object value) {
        if (value==null) {
            return;
        }
        if (hasTemplate()) {
            return;
        }
        setTemplate((String) value);
    }


}
