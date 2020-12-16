package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.tools.xpect.IORuntimeException;
import org.fluentcodes.tools.xpect.IOString;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 19.8.2020.
 */
public class EOConfigMap implements EOConfigMapInterface<ConfigConfigInterface> {
    public static final Logger LOG = LogManager.getLogger(EOConfigMap.class);
    private final Map<String, ConfigConfigInterface> configMap;
    private final Scope scope;
    private final Class<? extends ConfigConfigInterface> configClass;
    private final EOConfigsCache cache;

    protected EOConfigMap(EOConfigsCache cache, Scope scope, final Class<? extends ConfigConfigInterface> configClass)  {
        this.configClass = configClass;
        this.scope = scope;
        this.cache = cache;
        this.configMap = initMap();
    }

    /**
     * Default init map.
     * @return the expanded final configurations.
     */
    protected Map<String, ConfigConfigInterface> initMap() {
        final String configBeanString = readConfigFiles();
        String configBeanName = configClass.getName().replaceAll("Config$", "Bean");
        Class beanClass = null;
        try {
            beanClass = Class.forName(configBeanName);
        } catch (ClassNotFoundException e) {
            throw new EoException("Could not find bean class for '" + configClass.getName() + "'." );
        }
        EO eoRoot = new EoRoot(cache, Map.class, beanClass);
        eoRoot.mapObject(configBeanString);
        Map<String, ConfigBean> configBeanMap = (Map<String, ConfigBean>)eoRoot.get();
        Map<String, ConfigConfigInterface> configMap = new LinkedHashMap<>();
        for (String naturalId: configBeanMap.keySet()) {
            ConfigBean configBean = configBeanMap.get(naturalId);
            if (configBean == null) {
                throw new EoInternalException("Problem resolving bean with key '" + naturalId + "'.");
            }
            configBean.setNaturalId(naturalId);
            configMap.put(naturalId, configBean.createConfig(cache));
        }
        return configMap;
    }

    public Scope getScope() {
        return scope;
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

    protected final String readConfigFiles() {
        return readConfigFiles(configClass.getSimpleName() + ".json");
    }

    /**
     * Read all files in the classpath and concatenate them so its a valid json file.
     * @param fileName A file name for JSON configurations.
     * @return the concatenated file content.
     */
    protected final String readConfigFiles(final String fileName) {
        try {
            List<String> configContentList = new IOString()
                    .setFileName(fileName)
                    .readStringList();
            if (configContentList.isEmpty()) {
                LOG.warn("No configuration file '" + configClass + ".json' found in the classpath!");
                return "";
            }

            if (configContentList.size() == 1) {
                return configContentList.get(0);
            }
            StringBuilder concatenate = new StringBuilder();
            concatenate.append(configContentList.get(0)
                    .replaceAll("\\}$",","));
            for (int i = 1; i<configContentList.size()-1; i++) {
                concatenate.append(configContentList.get(i)
                        .replaceAll("\\}$",",")
                        .replaceAll("^\\{",""));
            }
            concatenate.append(configContentList.get(configContentList.size()-1)
                    .replaceAll("^\\{",""));
            return concatenate.toString();
        }
        catch (IORuntimeException e) {
            LOG.warn("No configuration file '" + configClass + ".json' found in the classpath!");
            return "";
        }
    }
}
