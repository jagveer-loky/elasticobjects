package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 22.1.2021.
 */
public class ConfigConfigMap implements ConfigConfigMapInterface<ConfigConfigInterface> {
    public static final Logger LOG = LogManager.getLogger(ConfigConfigMap.class);
    private final Map<String, ConfigConfigInterface> configMap;
    private final Scope scope;
    private final Class<? extends ConfigConfigInterface> configClass;

    protected ConfigConfigMap(Scope scope, final Class<? extends ConfigConfigInterface> configClass)  {
        this.configClass = configClass;
        this.scope = scope;
        this.configMap = initMap();
    }

    /**
     * Default init map.
     * @return the expanded final configurations.
     */
    protected Map<String, ConfigConfigInterface> initMap() {
        ConfigBeanMap configBeanMap = new ConfigBeanMap(scope, configClass);
        return configBeanMap.createConfigMap();
    }

    public Scope getScope() {
        return scope;
    }

    public Class<? extends ConfigConfigInterface> getConfigClass() {
        return configClass;
    }

    @Override
    public ConfigConfigInterface find(String key)  {
        if (key == null || key.isEmpty()) {
            throw new EoException("Key for find in '" + configClass.getSimpleName() + "' is empty!");
        }
        if (!configMap.containsKey(key)) {
            throw new EoException("Could not find config entry for '" + key + "' in config class '" + configClass.getSimpleName() + "'");
        }
        return configMap.get(key);
    }

    public Set<String> getKeys() {
        return configMap.keySet();
    }

    public boolean hasKey(final String key) {
        return configMap.containsKey(key);
    }

    public boolean isEmpty() {
        return configMap.isEmpty();
    }
}
