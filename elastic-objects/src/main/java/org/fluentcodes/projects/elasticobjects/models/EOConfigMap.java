package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.EO;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.tools.xpect.IORuntimeException;
import org.fluentcodes.tools.xpect.IOString;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.fluentcodes.projects.elasticobjects.models.Model.NATURAL_ID;

/**
 * Created by Werner on 19.8.2020.
 */
public abstract class EOConfigMap implements EOConfigMapInterface<Config> {
    public static final Logger LOG = LogManager.getLogger(EOConfigMap.class);
    private final Map<String, Config> configMap;
    private final Class<? extends Config> configClass;
    private final EOConfigsCache configsCache;

    public EOConfigMap(final EOConfigsCache eoConfigsCache, final Class<? extends Config> configClass)  {
        this.configClass = configClass;
        this.configsCache = eoConfigsCache;
        configMap = new HashMap<>();
    }

    public EOConfigsCache getConfigsCache() {
        return configsCache;
    }

    public Scope getScope() {
        return configsCache.getScope();
    }

    @Override
    public Config find(String key)  {
        Config item = configMap.get(key);
        if (item == null) {
            throw new EoException("Could not find config entry for '" + key + "' in config class '" + configClass.getSimpleName() + "'");
        }
        return item;
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

    protected void addJsonConfigs()  {
        String providerSource = configClass.getSimpleName() + ".json";
        try {
            List<String> configs = new IOString()
                    .setFileName(providerSource)
                    .readStringList();
            for (String config : configs) {
                EO eo = new EoRoot(configsCache, Map.class);
                eo.mapObject(config);
                addConfigMap((Map) eo.get());
            }
        }
        catch (IORuntimeException e) {
            LOG.info("No configuration file found for '" + getClass().getSimpleName() + "' in the classpath!");
        }
    }

    private void addConfigMap(final Map map)  {
        for (Object key : map.keySet()) {
            addConfigByMap((String) key, (Map)map.get(key));
        }
    }

    protected void addConfigByMap(final String key, final Map map) {
        if (!map.containsKey(NATURAL_ID)) {
            map.put(NATURAL_ID, key);
        }
        addConfigByMap(map);
    }

    protected void addConfigByMap(final Map map) {
        String naturalId = (String)map.get(NATURAL_ID);
        if (naturalId == null) {
            throw new EoInternalException("No naturalid provided for FileConfig");
        }
        if (hasKey(naturalId)) {
            throw new EoInternalException("NaturalId " + naturalId + " already exists FileConfig.");
        }
        if (configMap.containsKey(naturalId)) {
            throw new EoInternalException("NaturalId '" + naturalId + "' already exist in " + this.configClass.getSimpleName());
        }
        String modelKey =
                map.containsKey(ConfigImpl.CONFIG_MODEL_KEY) && map.get(ConfigImpl.CONFIG_MODEL_KEY) !=null && !((String)map.get(ConfigImpl.CONFIG_MODEL_KEY)).isEmpty()
                        ? (String) map.get(ConfigImpl.CONFIG_MODEL_KEY)
                        : configClass.getSimpleName();
        ModelConfig configurationModel = getConfigsCache().findModel(modelKey);
        try {
            Class configurationClass = configurationModel.getModelClass();
            Constructor configurationConstructor = configurationClass.getConstructor(EOConfigsCache.class, Map.class);
            try {
                addConfig((Config)configurationConstructor.newInstance(getConfigsCache(), map));
            } catch (InstantiationException e) {
                throw new EoInternalException("Problem with '" + naturalId + "'/'" + modelKey + "' in " + configClass.getSimpleName(), e);
            } catch (IllegalAccessException e) {
                throw new EoInternalException("Problem with '" + naturalId + "'/'" + modelKey + "' in " + configClass.getSimpleName(), e);
            } catch (InvocationTargetException e) {
                throw new EoInternalException("Problem with '" + naturalId + "'/'" + modelKey + "' in " + configClass.getSimpleName(), e);
            }
            catch (Exception e) {
                throw new EoInternalException(e);
            }
        } catch (NoSuchMethodException e) {
            throw new EoInternalException("Problem with '" + naturalId + "'/'" + modelKey + "' in " + configClass.getSimpleName(), e);
        }
    }

    protected void addConfig(final Config config) {
        this.configMap.put(config.getNaturalId(), config);

        if (!configMap.containsKey(config.getNaturalId())) {
            throw new EoException("Could not set '" + this.configClass.getSimpleName() + "' for " + config.getNaturalId());
        }
    }

    public String toStringx() {
        StringBuilder builder = new StringBuilder();
        int counter = 0;
        for (String key : configMap.keySet()) {
            Config config = (Config) configMap.get(key);

            try {
                config.resolve();
                builder.append("    \"");
                builder.append(key);
                builder.append("\":");
                EO adapter = new EoRoot(configsCache,config);
                builder.append(new EOToJSON()
                        .setStartIndent(3)
                        .toJSON(adapter));
                counter++;
                if (counter < configMap.keySet().size()) {
                    builder.append(",");
                }
                builder.append("\n");
            } catch (Exception e) {
                e.printStackTrace();
                builder.append("{}");
            }
        }
        return builder.toString();
    }


}
