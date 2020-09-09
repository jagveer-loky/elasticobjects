package org.fluentcodes.projects.elasticobjects.calls.files;


import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.*;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by Werner on 4.8.2020
 */
public enum FileConfigTypes {
    FLAT(FileConfig.class);
    private Class<? extends FileConfigInterface> fileTypeClass;
    private Constructor fileTypeConstructor;
    FileConfigTypes(Class<? extends FileConfigInterface> fileTypeClass) {
        this.fileTypeClass = fileTypeClass;
        try {
            this.fileTypeConstructor = fileTypeClass.getConstructor(EOConfigsCache.class, FileConfig.Builder.class);
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }

    public FileConfig createConfig(EOConfigsCache cache, Map values) {
        FileConfig.Builder builder = new FileConfig.Builder();
        builder.prepare(cache, values);
        try {
            return (FileConfig)this.fileTypeConstructor.newInstance(cache, builder);
        } catch (InstantiationException e) {
            throw new EoException(e);
        } catch (IllegalAccessException e) {
            throw new EoException(e);
        } catch (InvocationTargetException e) {
            throw new EoException(e);
        }
    }
}
