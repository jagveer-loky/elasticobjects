package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;

import java.util.Map;

/**
 * Created by Werner on 10.10.2016.
 */
public class ValueConfig extends ConfigImpl {
    private final Object value;
    private final String valueKey;
    public ValueConfig(EOConfigsCache eoConfigsCache, Builder builder) throws Exception {
        super(eoConfigsCache, builder);
        this.value = builder.value;
        this.valueKey = builder.valueKey;
    }

    @Override
    public String getKey() {
        return valueKey;
    }

    public Object getValue() {
        return value;
    }

    public String getValueKey() {
        return valueKey;
    }

    public static class Builder extends ConfigIO.Builder {
        //<call keep="JAVA" templateKey="BeanInstanceVars.tpl">
        private Object value;
        private String valueKey;
//</call>

        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values) throws Exception {
            this.value = values.get(EO_STATIC.F_VALUE);
            this.valueKey = (String) configsCache.transform(EO_STATIC.F_VALUE_KEY, values);
            super.prepare(configsCache, values);
        }

        public ValueConfig build(final EOConfigsCache configsCache, final Map<String, Object> values) throws Exception {
            prepare(configsCache, values);
            return new ValueConfig(configsCache, this);
        }

    }
}
