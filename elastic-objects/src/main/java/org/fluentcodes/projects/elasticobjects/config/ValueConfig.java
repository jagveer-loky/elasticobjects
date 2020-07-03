package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.executor.ExecutorItem;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

import java.util.Map;

/**
 * Created by Werner on 10.10.2016.
 */
public class ValueConfig extends ConfigImpl {
    private final Object value;
    private final String valueKey;
    private final ExecutorItem execute;

    public ValueConfig(EOConfigsCache eoConfigsCache, Builder builder)  {
        super(eoConfigsCache, builder);
        this.value = builder.value;
        this.valueKey = builder.valueKey;
        if (builder.execute != null) {
            this.execute = new ExecutorItem(builder.execute, ExecutorItem.TYPES.value);
        }
        else {
            execute = null;
        }
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

    public ExecutorItem getExecute() {
        return execute;
    }


    public static class Builder extends ConfigIO.Builder {
        //<call keep="JAVA" templateKey="BeanInstanceVars.tpl">
        private Object value;
        private String valueKey;
        private String execute;
//</call>

        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            this.value = values.get(F_VALUE);
            this.execute = (String) values.get("execute");
            this.valueKey = (String) configsCache.transform(F_VALUE_KEY, values);
            super.prepare(configsCache, values);
        }

        public ValueConfig build(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            prepare(configsCache, values);
            return new ValueConfig(configsCache, this);
        }

    }
}
