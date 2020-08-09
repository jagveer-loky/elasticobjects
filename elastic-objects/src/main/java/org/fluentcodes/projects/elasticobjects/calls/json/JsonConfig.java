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
    private final String jsonKey;
    private final Integer indent;
    private final JSONSerializationType serializationType;

    public JsonConfig(final EOConfigsCache eoCache, JsonConfig.Builder builder) {
        super(eoCache, builder);
        this.jsonKey = builder.jsonKey;
        this.indent = builder.indent;
        this.serializationType =builder.serializationType;
    }


    @Override
    public String getKey() {
        return jsonKey;
    }

    public String getJsonKey() {
        return jsonKey;
    }

    public JSONSerializationType getSerializationType() {
        return serializationType;
    }

    public Integer getIndent() {
        return indent;
    }

    public static class Builder extends FileConfig.Builder {
        private String jsonKey;
        private Integer indent;
        private JSONSerializationType serializationType;

        public Builder() {
            super();
        }
        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            this.jsonKey = (String) configsCache.transform(EO_STATIC.F_JSON_KEY, values);
            if (jsonKey == null || jsonKey.isEmpty()) {
                throw new EoException("jsonKey is not in the map defined!?");
            }
            this.serializationType = JSONSerializationType.EO; //TODO Refactor transform (JSONSerializationType) configsCache.transform(EO_STATIC.F_SERIALIZATION_TYPE, values, JSONSerializationType.STANDARD);
            indent = (Integer) configsCache.transform(EO_STATIC.F_INDENT, values, 1);
            values.put(EO_STATIC.F_FILE_KEY, this.jsonKey);
            super.prepare(configsCache, values);
        }

        @Override
        public JsonConfig build(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            prepare(configsCache, values);
            return new JsonConfig(configsCache, this);
        }
    }
}
