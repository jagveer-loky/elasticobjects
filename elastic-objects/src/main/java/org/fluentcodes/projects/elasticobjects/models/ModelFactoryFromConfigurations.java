package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;

import java.util.Map;

/**
 *
 * Created by Werner on 31.10.2021.
 */

public class ModelFactoryFromConfigurations extends ModelFactory {

    public ModelFactoryFromConfigurations(final ConfigMaps configMaps) {
        super(configMaps);
    }

    /**
     * Default init map.
     * @return the expanded final configurations.
     */
    @Override
    public Map<String, ModelBean> createBeanMap() {
        Map<String, ModelBean> beanMap = new ModelFactoryBasic(getConfigMaps()).createBeanMap();
        addModelBeans(beanMap);
        return beanMap;
    }

    protected void addModelBeans(Map<String, ModelBean> beanMap) {
        ConfigMaps devConfigMaps = new ConfigMaps(Scope.DEV);
        EO eoRoot = EoRoot.ofClass(devConfigMaps, readConfigFiles(), Map.class);
        Map<String, Map<String, Object>> mapValues = (Map<String, Map<String, Object>>)eoRoot.get();
        Map<String, FieldBean> fieldBeanMap = new FieldFactory(devConfigMaps).createBeanMap();
        for (Map.Entry<String, Map<String,Object>> entry: mapValues.entrySet()) {
            ModelBean modelBean = new ModelBean(entry.getValue());
            if (!modelBean.hasModelKey()) {
                LOG.warn("No modelKey defined for {}.", entry.getKey());
                continue;
            }
            modelBean.setNaturalId(entry.getKey());
            beanMap.put(entry.getKey(), modelBean);
        }
        for  (Map.Entry<String, ModelBean> entry: beanMap.entrySet()) {
            ModelBean modelBean = entry.getValue();
            modelBean.mergeFieldBeanMap(fieldBeanMap);
        }
    }


}
