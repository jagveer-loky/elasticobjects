package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateParser;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Call set parts of the config cache to the adapter.
 * Created by werner.diwischek on 10.6.2018
 */
public class ConfigurationCallRead extends CallResource<Map> {
    private static final Logger LOG = LogManager.getLogger(ConfigurationCallRead.class);
    private String filterConfigName;
    private boolean filterConfigNameDynamic;
    private String filterModule;
    private String filterSubModule;
    private String filterKey;

    public ConfigurationCallRead() {
        super();
    }

    public ConfigurationCallRead(final String configKey) {
        super(PermissionType.READ);
        setConfigKey(configKey);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return ConfigConfig.class;
    }

    protected ConfigConfig getConfigConfig() {
        return (ConfigConfig) getConfig();
    }


    private Class<? extends Config> createFilterConfigClass(EO adapter, Map attributes)  {
        if (!hasFilterConfigName()) {
            throw new EoException("Null config name!");
        }
        String configName = this.filterConfigName;
        if (filterConfigNameDynamic) {
            configName = new TemplateParser(configName).parse(adapter);
        }
        if (configName.contains(EO_STATIC.DYNAMIC_PATTERN)) {
            throw new EoException("Configname not completely resolved! " + this.filterConfigName + " -> " + configName);
        }
        try {
            return (Class<? extends Config>) Class.forName(EO_STATIC.CP_CONFIG + "." + configName);
        } catch (ClassNotFoundException e) {
            throw new EoException(e);
        }
    }

    public boolean hasFilterConfigName() {
        return filterConfigName != null && !filterConfigName.isEmpty();
    }

    public String getFilterConfigName() {
        return filterConfigName;
    }

    public ConfigurationCallRead setFilterConfigName(Object entry) {
        if (entry == null) {
            return this;
        }
        if (hasFilterConfigName()) {
            return this;
        }
        this.filterConfigName = ScalarConverter.toString(entry);
        this.filterConfigNameDynamic = this.filterConfigName.contains(EO_STATIC.DYNAMIC_PATTERN);
        return this;
    }

    public boolean hasModule() {
        return filterModule != null && !filterModule.isEmpty();
    }

    public String getFilterModule() {
        return filterModule;
    }

    public ConfigurationCallRead setFilterModule(Object entry) {
        if (entry == null) {
            return this;
        }
        if (hasModule()) {
            return this;
        }
        this.filterModule = ScalarConverter.toString(entry);
        return this;
    }

    public boolean hasSubModule() {
        return filterSubModule != null && !filterSubModule.isEmpty();
    }

    public String getFilterSubModule() {
        return filterSubModule;
    }

    public ConfigurationCallRead setFilterSubModule(Object entry) {
        if (entry == null) {
            return this;
        }
        if (hasSubModule()) {
            return this;
        }
        this.filterSubModule = ScalarConverter.toString(entry);
        return this;
    }

    public boolean hasKey() {
        return filterKey != null && !filterKey.isEmpty();
    }

    public String getFilterKey() {
        return filterKey;
    }

    public ConfigurationCallRead setFilterKey(Object entry) {
        if (entry == null) {
            return this;
        }
        if (hasKey()) {
            return this;
        }
        this.filterKey = ScalarConverter.toString(entry);
        return this;
    }

    public CallResource resolve(EOConfigsCache cache) {
        try {
            super.resolve(cache);
        }
        catch(EoException e) {
            LOG.debug("No cache used...");
        }
        if (!hasConfig()) {
            return this;
        }
        ConfigConfig config = (ConfigConfig) getConfig();
        if (filterConfigName == null) {
            filterConfigName = config.getFilterConfigName();
        }
        if (filterKey == null) {
            setFilterKey(config.getFilterKey());
        }
        if (filterModule == null) {
            setFilterModule(config.getFilterModule());
        }
        if (filterSubModule == null) {
            setFilterSubModule(config.getFilterSubModule());
        }
        return this;
    }

    public Map execute(EO eo)  {
        this.resolve(eo.getConfigsCache());
        Map<String, Map> result = new LinkedHashMap<>();
        EOConfigsCache configsCache = eo.getConfigsCache();
        ModelConfig configModel = configsCache.findModel(filterConfigName);
        Class configClass = configModel.getModelClass();
        EoRoot eoTransform = EoRoot.ofMap(eo.getConfigsCache());
        eoTransform.setSerializationType(JSONSerializationType.STANDARD);
        for (String key : configsCache.getConfigKeys(configClass)) {
            Config configEntry = (Config) configsCache.find(configClass, key);
            try {
                if (configEntry.getModule() == null) {
                    continue;
                }
                if (!configEntry.getModule().equals(this.getFilterModule())) {
                    continue;
                }
                if (configEntry.getSubModule() == null) {
                    continue;
                }
                if (!configEntry.getSubModule().equals(this.getFilterSubModule())) {
                    continue;
                }
                if (configEntry.getKey() == null) {
                    continue;
                }
                if (!configEntry.getKey().matches(this.getFilterKey())) {
                    continue;
                }
            } catch (Exception e) {
                throw new EoException(e);
            }
            eoTransform.set(configEntry, key);
        }
        return (Map)eoTransform.get();
    }
}
