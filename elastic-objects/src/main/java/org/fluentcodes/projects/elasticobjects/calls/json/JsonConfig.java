package org.fluentcodes.projects.elasticobjects.calls.json;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;

import java.util.Map;

/**
 * Created by Werner on 02.04.2018.
 * Refactored with write on 18.5.2018
 */
public class JsonConfig extends FileConfig {
    private static final Logger LOG = LogManager.getLogger(JsonConfig.class);
    private final Integer indent;
    private final JSONSerializationType serializationType;

    public JsonConfig(final EOConfigsCache eoCache, JsonConfig.Builder builder) {
        super(eoCache, builder);
        this.indent = builder.indent;
        this.serializationType =builder.serializationType;
    }

    public JSONSerializationType getSerializationType() {
        return serializationType;
    }

    public Integer getIndent() {
        return indent;
    }

    public static class Builder extends FileConfig.Builder {
        private Integer indent;
        private JSONSerializationType serializationType;

        public Builder() {
            super();
        }
        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            this.serializationType = JSONSerializationType.EO; //TODO Refactor transform (JSONSerializationType) configsCache.transform(EO_STATIC.F_SERIALIZATION_TYPE, values, JSONSerializationType.STANDARD);
            indent = (Integer) configsCache.transform(EO_STATIC.F_INDENT, values, 1);
            super.prepare(configsCache, values);
        }

        @Override
        public JsonConfig build(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            prepare(configsCache, values);
            return new JsonConfig(configsCache, this);
        }
    }
}
