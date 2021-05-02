package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public class ModelFactoryFromConfigurations extends ModelFactory {
    /**
     * Default init map.
     * @return the expanded final configurations.
     */
    @Override
    public Map<String, ModelBean> createBeanMap(ConfigMaps configMaps) {
        Map<String, ModelBean> beanMap = new TreeMap<>();
        return addModelBeans(configMaps, beanMap);
    }

    public Map<String, ModelBean> addModelBeans(ConfigMaps configMaps, Map<String, ModelBean> beanMap) {
        EO eoRoot = EoRoot.ofClass(configMaps, readConfigFiles(ModelConfig.class), Map.class);
        Map<String, Map<String, Object>> mapValues = (Map<String, Map<String, Object>>)eoRoot.get();
        for (String naturalId: mapValues.keySet()) {
            ModelBean modelBean = new ModelBean(mapValues.get(naturalId));
            beanMap.put(naturalId, modelBean);
        }
        return (Map<String, ModelBean>)eoRoot.get();
    }
}
