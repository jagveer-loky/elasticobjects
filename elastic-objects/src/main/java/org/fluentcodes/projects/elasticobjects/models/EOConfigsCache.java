package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Storage for configurations
 *
 * @author Werner Diwischek
 * @since 13.10.2016.
 */
public class EOConfigsCache {
    private static final Logger LOG = LogManager.getLogger(EOConfigsCache.class);
    private final Map<Class, EOConfigMap> eoConfigsMap;
    private final Scope scope;

    public EOConfigsCache() {
        this(Scope.DEV);
    }

    public EOConfigsCache(final Scope scope) {
        eoConfigsMap = new LinkedHashMap<>();
        this.scope = scope == null ? Scope.DEV : scope;
        eoConfigsMap.put(ModelConfig.class, new EOConfigMapModels(this, scope));
        if (scope == Scope.DEV) {
            return;
        }
        eoConfigsMap.put(HostConfig.class, new EOConfigMap(this, scope, HostConfig.class));
        eoConfigsMap.put(FileConfig.class, new EOConfigMap(this, scope, FileConfig.class));
    }

    public Set<Class> getKeys() {
        return eoConfigsMap.keySet();
    }


    public List<String> getConfigClassesAsStringList() {
        return eoConfigsMap.keySet().stream().map(x -> x.getSimpleName()).collect(Collectors.toCollection(ArrayList::new));
    }

    public Set<String> getConfigNames(final String configName)  {
        return getConfigMap(configName).getKeys();
    }

    public EOConfigMap getConfigMap(final String configName)  {
        if (configName == null) {
            throw new EoException("Null search name for configMap entry");
        }
        for (Class configClass: getKeys()) {
            if (configName.equals(configClass.getSimpleName())) {
                return eoConfigsMap.get(configClass);
            }
        }

        throw new EoException("Could not find search name '" + configName + "'for configMap entry");
    }

    public Object find(final Class configClass, final String configKey)  {
        if (configClass == null) {
            throw new EoException("Cacheclass is null for finder!");
        }
        if (configKey == null || configKey.isEmpty()) {
            throw new EoException("configKey is empty for finder '" + configClass.getSimpleName() + "'!");
        }
        Object config = getConfigMap(configClass).find(configKey);
        if (config == null) {
            throw new EoException("Config not resolved configClass " + configClass.getSimpleName() + " for configKey=" + configKey + ".");
        }
        return config;
    }

    public Set<String> getConfigKeys(Class configClass) {
        return getConfigKeys(configClass, Expose.NONE);
    }

    public Set<String> getConfigKeys(Class configClass, Expose expose) {
        if (expose == null || expose == Expose.NONE || getConfigMap(configClass).isEmpty()) {
            return getConfigMap(configClass).getKeys();
        }
        Set<String> configKeys = new LinkedHashSet<>();
        for (String naturalId: getConfigMap(configClass).getKeys()) {
            ConfigConfigInterface config = getConfigMap(configClass).find(naturalId);
            if (config.hasExpose() && config.getExpose().ordinal() <= expose.ordinal()) {
                configKeys.add(naturalId);
            }
        }
        return configKeys;
    }

    private EOConfigMap getConfigMap(Class configClass) {
        if (eoConfigsMap.get(configClass) == null) {
            eoConfigsMap.put(configClass, new EOConfigMap(this, scope, configClass));
        }
        EOConfigMap configs = eoConfigsMap.get(configClass);
        if (configs == null) {
            throw new EoException("No provider defined for " + configClass.getSimpleName());
        }
        return configs;
    }

    public ModelConfig findModel(final String modelKey)  {
        return (ModelConfig) find(ModelConfig.class, modelKey);
    }
    public ModelConfig findModel(final Class modelClass)  {
        return findModel(modelClass.getSimpleName());
    }
    public ModelConfig findModel(final Object modelValue)  {
        if (modelValue == null) {
            throw new EoException("null model value");
        }
        return findModel(modelValue.getClass());
    }

    public FileConfig findFile(final String key)  {
        return (FileConfig) find(FileConfig.class, key);
    }
    public HostConfig findHost(final String key)  {
        return (HostConfig) find(HostConfig.class, key);
    }



}
