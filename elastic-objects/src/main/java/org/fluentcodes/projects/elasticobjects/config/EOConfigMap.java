package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.EO;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.tools.xpect.IOString;

import java.lang.reflect.Method;
import java.util.*;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_NATURAL_ID;

/**
 * Created by Werner on 10.10.2016.
 */
public abstract class EOConfigMap implements EOConfigMapInterface<Config> {
    public static final Logger LOG = LogManager.getLogger(EOConfigMap.class);
    private final Map<String, Config> configMap;
    private final Class<? extends Config> configClass;
    private final EOConfigsCache configsCache;
    private final Class builderClass;
    private final Method builderMethod;


    public EOConfigMap(final EOConfigsCache eoConfigsCache, final Class<? extends Config> configClass)  {
        this.configClass = configClass;
        this.configsCache = eoConfigsCache;
        try {
            this.builderClass = Class.forName(configClass.getName() + "$Builder");
            this.builderMethod = builderClass.getMethod("build", EOConfigsCache.class, Map.class);
        }
        catch (Exception e) {
            throw new EoException(e);
        }
        configMap = new HashMap<>();
    }

    public EOConfigsCache getConfigsCache() {
        return configsCache;
    }

    public Scope getScope() {
        return configsCache.getScope();
    }

    public Config get(String key)  {
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
        List<String> configs = new IOString()
                .setFileName(providerSource)
                .readStringList();
        for (String config : configs) {
            EO eo = new EoRoot(configsCache, LogLevel.DEBUG, Map.class);
            eo.mapObject(config);
            addConfigMap((Map)eo.get());
        }
    }

    private void addConfigMap(final Map map)  {
        for (Object key : map.keySet()) {
            addConfigByMap((String) key, (Map)map.get(key));
        }
    }

    protected void addConfigByMap(final String key, final Map map) {
        if (!map.containsKey(F_NATURAL_ID)) {
            map.put(F_NATURAL_ID, key);
        }
        addConfigByMap(map);
    }

    protected void addConfigByMap(final Map map) {
        String naturalId = (String)map.get(F_NATURAL_ID);
        if (naturalId == null) {
            throw new EoException("No naturalid provided");
        }
        try {
            Object object = builderClass.getDeclaredConstructor(null).newInstance();
            configMap.put(naturalId, (Config) builderMethod.invoke(object, configsCache, map));
        } catch (Exception e) {
            throw new EoException(e);
        }
    }

    protected void addConfig(Config config) {
        this.configMap.put(config.getNaturalId(), config);
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
