package org.fluentcodes.tools.xpect;

import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.eo.*;

import java.io.File;

public class IOJsonEo<T> extends IOObject<T> {
    private static EOConfigsCache cache;
    private String fileEnding = "json";

    public IOJsonEo() {
        super();
        this.cache = new EOConfigsCache();
    }
    public IOJsonEo(EOConfigsCache cache) {
        super();
        this.cache = cache;
    }


    public String getFileEnding() {
        return fileEnding;
    }
    public IOJsonEo<T> setFileEnding(final String fileEnding) {
        this.fileEnding = fileEnding;
        return this;
    }

    @Override
    public String asString(T object) {
        if (object == null) {
            throw new IORuntimeException("Null object for serialization!");
        }
        try {
            EO eo = new EOBuilder(cache).set(object);
            return new EOToJSON().toJSON(eo);
        } catch (Exception e) {
            throw new IORuntimeException(e);
        }
    }

    @Override
    public T asObject(final String asString) {
        try {
            EO eo = new EOBuilder(cache).map(asString);
            return (T) eo.get();
        } catch (Exception e) {
            throw new IORuntimeException(e);
        }
    }
}
