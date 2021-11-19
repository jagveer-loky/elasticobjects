package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigFactory;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Created by Werner on 31.10.2021.
 */

public class FileFactory extends ConfigFactory< FileBean, FileConfig> {
    public FileFactory(final ConfigMaps configMaps) {
        super(configMaps, FileBean.class, FileConfig.class);
    }

    /**
     * Create a config map from a bean map.
     * @return the config map
     */
    /*
    @Override
    public Map<String, FileConfig> createConfigMap() {
        Map<String, FileConfig> configMap = new TreeMap<>();
        Map<String, FileBean> beanMap = createBeanMap();
        for (Map.Entry<String, FileBean> entry: beanMap.entrySet()) {
            FileBean bean = entry.getValue();
            String configClassAsString = bean.getConfigModelKey();
            if (configClassAsString != null) {
                try {
                    configClassAsString = FileFactory.class.getPackage()
                            .toString().replace("package ", "") + "." + configClassAsString;
                    Class configClass = Class.forName(configClassAsString);
                    FileConfig fileConfig = (FileConfig) configClass.getConstructor(FileBean.class, ConfigMaps.class).newInstance(entry.getValue(), getConfigMaps());
                    configMap.put(key, fileConfig);
                } catch (Exception e) {
                    LOG.error("Problem initalize {}: ", configClassAsString, e);
                    throw new EoInternalException(e);
                }

            }
            else {
                configMap.put(key, new FileConfig(entry.getValue(), getConfigMaps()));
            }
        }
        return configMap;
    }
    */

}
