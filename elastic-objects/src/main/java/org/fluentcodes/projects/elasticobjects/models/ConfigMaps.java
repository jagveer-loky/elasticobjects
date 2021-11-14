package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.HostFactory;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileFactory;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * cache map for configurations
 *
 * @author Werner Diwischek
 * @since 13.10.2016.
 */
public class ConfigMaps {
    private final Map<Class<? extends ConfigInterface>, Map<String, ConfigInterface>> configMaps;
    private final Scope scope;

    public ConfigMaps() {
        this(Scope.DEV);
    }

    public ConfigMaps(final Scope scope) {
        configMaps = new LinkedHashMap<>();
        this.scope = scope;
        if (scope == Scope.DEV) {
            configMaps.put(ModelConfig.class, new ModelFactoryBasic().createImmutableConfig(this));
            return;
        }
        configMaps.put(ModelConfig.class, new ModelFactoryAll(scope).createImmutableConfig(new ConfigMaps(Scope.DEV)));
        configMaps.put(HostConfig.class, new HostFactory(scope).createImmutableConfig(this));
        configMaps.put(FileConfig.class, new FileFactory(scope).createImmutableConfig(this));
    }

    public Scope getScope() {
        return scope;
    }

    public Set<Class<? extends ConfigInterface>> getKeys() {
        return configMaps.keySet();
    }

    public List<String> getKeysAsString() {
        return configMaps.keySet().stream().map(x -> x.getSimpleName()).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Find a certain configuration within the configuration map for configClass.
     * @param configClass the associated config class
     * @param configKey   the config key to find
     * @return The configuration class
     */
    public Object find(final Class<? extends ConfigInterface> configClass, final String configKey)  {
        if (configKey == null || configKey.isEmpty()) {
            throw new EoException("Config key is empty within '" + configClass.getSimpleName() + "'!");
        }
        if (!getConfigMap(configClass).containsKey(configKey)) {
            throw new EoException("Could not found config key within '" + configClass.getSimpleName() + "'!");
        }
        ConfigInterface config = getConfigMap(configClass).get(configKey);
        return config;
    }

    public boolean hasKey(final Class configClass, final String configKey)  {
        if (configKey == null || configKey.isEmpty()) throw new EoException("Config key is empty for finder '" + configClass.getSimpleName() + "'!");
        return  getConfigMap(configClass).containsKey(configKey);
    }

    public boolean isEmpty(final Class configClass) {
        return getConfigMap(configClass).isEmpty();
    }

    public Set<String> getConfigKeys(final String configName)  {
        return getConfigKeys(findConfigClass(configName));
    }

    public Set<String> getConfigKeys(Class configClass) {
        return getConfigMap(configClass).keySet();
    }

    public Set<String> getConfigKeys(Class configClass, Expose expose) {
        return getConfigMap(configClass).keySet();
    }

    private Map<String, ConfigInterface> getConfigMap(Class configClass) {
        if (configClass == null) throw new EoException("Config class is null!");
        if (!this.configMaps.containsKey(configClass)) {
            String factoryClassName = configClass.getName().replace("Config", "Factory") ;
            Class factoryClass = null;
            try {
                factoryClass = Class.forName(factoryClassName);
            } catch (ClassNotFoundException e) {
                throw new EoException(e);
            }
            ConfigFactory configFactory = null;
            try {
                configFactory = (ConfigFactory) factoryClass.getConstructor(Scope.class).newInstance(scope);
            } catch (Exception e) {
                throw new EoException(e);
            }
            configMaps.put(configClass, configFactory.createImmutableConfig(this));
        }
        return configMaps.get(configClass);
    }


    private Class findConfigClass(final String configName)  {
        if (configName == null || configName.isEmpty()) throw new EoException("Null search name for configMap entry");
        for (Class configClass: configMaps.keySet()) {
            if (configName.equals(configClass.getSimpleName())) return configClass;
        }
        throw new EoException("Could not find search name '" + configName + "'for configMap entry");
    }

    public ModelConfig findModel(final String modelKey)  {
        return (ModelConfig) find(ModelConfig.class, modelKey);
    }
    public ModelConfig findModel(final Class modelClass)  {
        return (ModelConfig) find(ModelConfig.class, modelClass.getSimpleName());
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

    public boolean hasFile(final String key)  {
        return hasKey(FileConfig.class, key);
    }

    public HostConfig findHost(final String key)  {
        return (HostConfig) find(HostConfig.class, key);
    }



}
