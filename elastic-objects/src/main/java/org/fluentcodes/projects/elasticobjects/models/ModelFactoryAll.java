package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public class ModelFactoryAll extends ModelFactory {
    ModelFactoryAll(final ConfigMaps configMaps) {
        super(configMaps);
    }

    @Override
    public Map<String, ModelBean> createBeanMap() {
        Map<String, ModelBean> beanMap = new TreeMap<>();
        new ModelFactoryBasic(getConfigMaps()).addModelBeans(beanMap);
        new ModelFactoryFromConfigurations(getConfigMaps())
                .addModelBeans(beanMap);
        new ModelFactoryFromModels(getConfigMaps())
                .addModelBeans(beanMap);
        return beanMap;
    }

}
