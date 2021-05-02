package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.UnmodifiableMap;
import org.fluentcodes.projects.elasticobjects.calls.files.FileBean;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.tools.xpect.IORuntimeException;
import org.fluentcodes.tools.xpect.IOString;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Werner on 22.10.2021.
 */

public abstract class ConfigFactory<T extends ConfigConfigInterface, U extends ConfigBeanInterface> implements ConfigConfigMapInterface<T, U> {
    public static final Logger LOG = LogManager.getLogger(ConfigFactory.class);

    public Map<String, ConfigConfigInterface> createImmutableConfig(ConfigMaps configMaps){
        return new UnmodifiableMap<>(createConfigMap(configMaps));
    }

    public final static String readConfigFiles(Class<? extends ConfigConfigInterface> defaultConfigClass) {
        return readConfigFiles(defaultConfigClass.getSimpleName() + ".json");
    }

    /**
     * Read all files in the classpath and concatenate them so its a valid json file.
     * @param fileName A file name for JSON configurations.
     * @return the concatenated file content.
     */
    public final static String readConfigFiles(final String fileName) {
        try {
            List<String> configContentList = new IOString()
                    .setFileName(fileName)
                    .readStringList();
            if (configContentList.isEmpty()) {
                LOG.warn("No configuration file '" + fileName + "' found in the classpath!");
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


}
