package org.fluentcodes.tools.xpect;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Arrays;

public class IOJsonEo<T> extends IOObject<T> {
    private EOConfigsCache cache;
    private JSONSerializationType type = JSONSerializationType.STANDARD;
    private String fileEnding = "json";

    public IOJsonEo() {
        super();
    }

    public IOJsonEo(XpectEo.Builder<T> builder) {
        super();
        this.type = builder.type;
        this.setMappingClasses(Arrays.asList(builder.classes));

    }

    public IOJsonEo(EOConfigsCache cache) {
        super();
        this.cache = cache;
    }

    public JSONSerializationType getType() {
        return type;
    }

    public void setType(JSONSerializationType type) {
        this.type = type;
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
            if (object instanceof EO) {
                return new EOToJSON().toJSON((EO)object);
            }
            if (cache == null) {
                cache = new EOConfigsCache(Scope.DEV);
            }
            EO eo = EoRoot.ofValue(cache, object);
            return new EOToJSON().setSerializationType(type).toJSON(eo);
        } catch (Exception e) {
            throw new IORuntimeException(e);
        }
    }

    @Override
    public T asObject(final String asString) {
        try {
            return (T) asEo(asString).get();
        } catch (Exception e) {
            throw new IORuntimeException(e);
        }
    }

    public EO asEo(final String asString) {
        try {
            return new EoRoot(cache, getMappingClass()).mapObject(asString);
        } catch (Exception e) {
            throw new IORuntimeException(e);
        }
    }
}
