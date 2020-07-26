package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.config.Config;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.utils.ReplaceUtil;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Call set parts of the config cache to the adapter.
 * Created by werner.diwischek on 10.6.2018
 */
public class ConfigurationCall extends CallResource<String> {
    private String filterConfigName;
    private boolean filterConfigNameDynamic;
    private String filterModule;
    private String filterSubModule;
    private String filterKey;

    public ConfigurationCall() {
        super();
    }

    protected ConfigConfig getConfigConfig() {
        return (ConfigConfig) getConfig();
    }

    @Override
    public void mapAttributes(final Map attributes) {
        if (attributes == null) {
            return;
        }
        super.mapAttributes(attributes);
        setFilterModule(attributes.get(EO_STATIC.F_FILTER_MODULE));
        setFilterSubModule(attributes.get(EO_STATIC.F_FILTER_SUB_MODULE));
        setFilterKey(attributes.get(EO_STATIC.F_FILTER_KEY));
        setFilterConfigName(attributes.get(EO_STATIC.F_FILTER_CONFIG_NAME));
    }

    private Class<? extends Config> createFilterConfigClass(EO adapter, Map attributes)  {
        if (!hasFilterConfigName()) {
            throw new EoException("Null config name!");
        }
        String configName = this.filterConfigName;
        if (filterConfigNameDynamic) {
            configName = ReplaceUtil.replace(configName, adapter, attributes);
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

    public ConfigurationCall setFilterConfigName(Object entry) {
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

    public ConfigurationCall setFilterModule(Object entry) {
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

    public ConfigurationCall setFilterSubModule(Object entry) {
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

    public ConfigurationCall setFilterKey(Object entry) {
        if (entry == null) {
            return this;
        }
        if (hasKey()) {
            return this;
        }
        this.filterKey = ScalarConverter.toString(entry);
        return this;
    }

    public String execute(EO adapter)  {
        return set(adapter, new HashMap());
    }

    protected void mergeConfig() {
        if (!super.hasConfig()) {
            return;
        }
        ConfigConfig config = getConfigConfig();
        setFilterConfigName(config.getFilterConfigName());
        setFilterKey(config.getFilterKey());
        setFilterModule(config.getFilterModule());
        setFilterSubModule(config.getFilterSubModule());
        super.mergeConfig();
    }

    public String set(final EO adapter, final Map attributes)  {
        mapAttributes(attributes);
        mergeConfig();
        Class<? extends Config> filterConfigClass = createFilterConfigClass(adapter, attributes);
        EOConfigsCache configsCache = adapter.getConfigsCache();
        if (configsCache == null) {
            throw new EoException("No config cache in adapter?!");
        }
        EO childAdapter = adapter;
        if (hasPath()) {
            childAdapter = super.createAdapter(adapter, attributes);
        }
        for (String key : configsCache.getConfigKeys(filterConfigClass)) {
            Config configEntry = (Config) configsCache.find(filterConfigClass, key);
            try {
                if (!configEntry.getModule().equals(this.getFilterModule())) {
                    continue;
                }
                if (!configEntry.getSubModule().equals(this.getFilterSubModule())) {
                    continue;
                }
                if (!configEntry.getKey().matches(this.getFilterKey())) {
                    continue;
                }
            } catch (Exception e) {
                throw e;
            }
            childAdapter.set(configEntry, key);
        }
        return "";
    }
}
