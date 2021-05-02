package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public class ModelFactoryAll extends ModelFactory {
    /**
     * Default init map.
     * @return the expanded final configurations.
     */
    @Override
    public Map<String, ModelBean> createBeanMap(ConfigMaps configMaps) {
        Map<String, ModelBean> beanMap = new TreeMap<>();
        new ModelFactoryBasic().addModelBeans(beanMap);
        new ModelFactoryFromConfigurations().addModelBeans(configMaps, beanMap);
        new ModelFactoryFromModels().addModelBeans(beanMap);
        return beanMap;
    }
}
