package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public class ModelFactoryAll extends ModelFactory {
    public ModelFactoryAll() {
        this(Scope.DEV);
    }
    ModelFactoryAll(Scope scope) {
        super(scope);
    }

    @Override
    public Map<String, ModelBean> createBeanMap(ConfigMaps configMaps) {
        Map<String, ModelBean> beanMap = new TreeMap<>();
        new ModelFactoryBasic().addModelBeans(beanMap);
        new ModelFactoryFromConfigurations(configMaps.getScope())
                .addModelBeans(configMaps, beanMap);
        new ModelFactoryFromModels(configMaps.getScope())
                .addModelBeans(beanMap);
        return beanMap;
    }

    public Map<String, ModelBean> createBeanMap() {
        return createBeanMap(new ConfigMaps(Scope.DEV));
    }
}
