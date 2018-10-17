package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 10.5.2018.
 */
public class ConfigConfig extends ConfigImpl {
    private static final Logger LOG = LogManager.getLogger(ConfigConfig.class);
    private final String configKey;
    private final String filterModule;
    private final String filterSubModule;
    private final String filterKey;
    private final String filterConfigName;

    public ConfigConfig(EOConfigsCache configsCache, Builder builder) {
        super(configsCache, builder);
        this.configKey = builder.configKey;
        this.filterKey = builder.filterKey;
        this.filterModule = builder.filterModule;
        this.filterSubModule = builder.filterSubModule;
        this.filterConfigName = builder.filterConfigName;
    }

    @Override
    public String getKey() {
        return configKey;
    }

    public String getConfigKey() {
        return configKey;
    }

    public String getFilterModule() {
        return filterModule;
    }

    public String getFilterSubModule() {
        return filterSubModule;
    }

    public String getFilterKey() {
        return filterKey;
    }

    public String getFilterConfigName() {
        return filterConfigName;
    }

    public static class Builder extends ConfigImpl.Builder {
        private String configKey;
        private String filterModule;
        private String filterSubModule;
        private String filterKey;
        private String filterConfigName;

        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values) throws Exception {
            this.configKey = (String) configsCache.transform(F_CONFIG_KEY, values);
            this.filterModule = (String) configsCache.transform(F_FILTER_MODULE, values);
            this.filterSubModule = (String) configsCache.transform(F_FILTER_SUB_MODULE, values);
            this.filterKey = (String) configsCache.transform(F_FILTER_KEY, values);
            this.filterConfigName = (String) configsCache.transform(F_FILTER_CONFIG_NAME, values);
            super.prepare(configsCache, values);
        }

        public ConfigConfig build(EOConfigsCache configsCache, Map<String, Object> values) throws Exception {
            prepare(configsCache, values);
            return new ConfigConfig(configsCache, this);
        }
    }
}
