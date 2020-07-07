package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoException;
import org.fluentcodes.projects.elasticobjects.eo.EO;

import org.fluentcodes.projects.elasticobjects.eo.EORoot;
import org.fluentcodes.projects.elasticobjects.eo.EOToJSON;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 10.10.2016.
 */
public class EOConfigs implements EOConfigsInterface<Config> {
    public static final Logger LOG = LogManager.getLogger(EOConfigs.class);

    private final Map<String, Config> configMap;
    private final Class<? extends Config> configClass;
    private final EOConfigsCache configsCache;
    private final Scope scope;


    public EOConfigs(final EOConfigsCache eoConfigsCache, final Class<? extends Config> configClass, Scope scope)  {
        this.configClass = configClass;
        this.configsCache = eoConfigsCache;
        this.configMap = new HashMap<>();
        this.scope = scope;
    }

    protected EOConfigs(final EOConfigsCache eoConfigsCache, final Class<? extends Config> configClass, final Map<String, Config> configMap, Scope scope) {
        this.configsCache = eoConfigsCache;
        this.configClass = configClass;
        this.configMap = configMap;
        this.scope = scope;
    }

    public EOConfigsCache getConfigsCache() {
        return configsCache;
    }

    protected void addConfigs()  {
        addAll(new EOConfigReader(configsCache, ModelConfig.class).read());
    }

    public Map<String, Config> getConfigMap() {
        return configMap;
    }

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

    protected void add(Config config) {
        this.configMap.put(config.getNaturalId(), config);
    }

    protected void addAll(final Map<String, Config> configMap) {
        for (String key : configMap.keySet()) {
            this.configMap.put(key, configMap.get(key));
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
                EO adapter = new EORoot(configsCache,config);
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
