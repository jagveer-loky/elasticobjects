package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.UnmodifiableMap;
import org.fluentcodes.projects.elasticobjects.calls.HostBean;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.tools.xpect.IORuntimeException;
import org.fluentcodes.tools.xpect.IOString;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Created by Werner on 22.10.2021.
 */

public abstract class ConfigFactory<T extends ConfigBean, U extends ConfigConfigInterface> {
    public static final Logger LOG = LogManager.getLogger(ConfigFactory.class);
    private final Scope scope;
    private final Class<? extends ConfigConfigInterface> configClass;
    private final Class<? extends ConfigBeanInterface> beanClass;

    protected ConfigFactory(final Scope scope, final Class<? extends ConfigBeanInterface> beanClass, Class<? extends ConfigConfigInterface> configClass) {
        this.scope = scope;
        this.configClass = configClass;
        this.beanClass = beanClass;
    }

    public  Map<String, ConfigConfigInterface> createImmutableConfig(ConfigMaps configMaps){
        return new UnmodifiableMap<>(createConfigMap(configMaps));
    }

    /**
     * Default init map.
     * @return the expanded final configurations.
     */
    public Map<String, T> createBeanMap(ConfigMaps configMaps) {
        EO eoRoot = EoRoot.ofClass(configMaps, readConfigFiles(), Map.class, beanClass);
        Map<String,T> beanMap = (Map<String, T>)eoRoot.get();
        for (Map.Entry<String, T> entry: beanMap.entrySet()) {
            entry.getValue().setNaturalId(entry.getKey());
        }
        return beanMap;
    }

    public Map<String, U> createConfigMap(ConfigMaps configMaps) {
        Map<String, T> beanMap = createBeanMap(configMaps);
        Map<String, U> configMap = new TreeMap<>();
        try {
            for (Map.Entry<String, T> entry: beanMap.entrySet()) {
                U config = (U)entry.getValue().createConfig(configMaps);
                configMap.put(entry.getKey(), config);
            }
        } catch (Exception e) {
            throw new EoInternalException(e);
        }
        return configMap;
    }

    public  String readConfigFiles() {
        return readConfigFiles(configClass.getSimpleName() + ".json");
    }

    /**
     * Read all files in the classpath and concatenate them so its a valid json file.
     * @param fileName A file name for JSON configurations.
     * @return the concatenated file content.
     */
    public static final String readConfigFiles(final String fileName) {
        try {
            List<String> configContentList = new IOString()
                    .setFileName(fileName)
                    .readStringList();
            if (configContentList.isEmpty()) {
                LOG.warn("No configuration file '{}' found in the classpath!", fileName);
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
            throw new EoInternalException("No configuration file '" + fileName + "' found in the classpath!", e);
        }
    }

    public Scope getScope() {
        return scope;
    }
}
