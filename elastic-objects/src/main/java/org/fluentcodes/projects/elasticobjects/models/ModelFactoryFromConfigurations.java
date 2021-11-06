package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;

import java.util.Map;

/**
 *
 * Created by Werner on 31.10.2021.
 */

public class ModelFactoryFromConfigurations extends ModelFactory {

    public ModelFactoryFromConfigurations() {
        this(Scope.DEV);
    }

    public ModelFactoryFromConfigurations(Scope scope) {
        super(scope);
    }

    /**
     * Default init map.
     * @return the expanded final configurations.
     */
    @Override
    public Map<String, ModelBean> createBeanMap(ConfigMaps configMaps) {
        Map<String, ModelBean> beanMap = new ModelFactoryBasic().createBeanMap();
        addModelBeans(configMaps, beanMap);
        return beanMap;
    }

    protected Map<String, ModelBean> createBeanMap() {
        return createBeanMap(new ConfigMaps(Scope.DEV));
    }


    protected void addModelBeans(ConfigMaps configMaps, Map<String, ModelBean> beanMap) {
        EO eoRoot = EoRoot.ofClass(configMaps, readConfigFiles(), Map.class);
        Map<String, Map<String, Object>> mapValues = (Map<String, Map<String, Object>>)eoRoot.get();
        Map<String, FieldBean> fieldBeanMap = new FieldFactory(getScope()).createBeanMap(configMaps);
        for (Map.Entry<String, Map<String,Object>> entry: mapValues.entrySet()) {
            ModelBean modelBean = new ModelBean(entry.getValue());
            modelBean.setNaturalId(entry.getKey());
            beanMap.put(entry.getKey(), modelBean);
        }
        for  (Map.Entry<String, ModelBean> entry: beanMap.entrySet()) {
            ModelBean modelBean = entry.getValue();
            modelBean.mergeFieldBeanMap(fieldBeanMap);
        }
    }


}
