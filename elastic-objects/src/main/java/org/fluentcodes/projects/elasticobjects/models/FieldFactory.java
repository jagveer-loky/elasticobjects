package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;

import java.util.Map;
import java.util.TreeMap;

import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.NATURAL_ID;

/**
 * Created by Werner on 21.10.2021.
 */

public class FieldFactory extends ConfigFactory<FieldBean, FieldConfig> {
    public FieldFactory(final ConfigMaps configMaps) {
        super(configMaps, FieldBean.class, FieldConfig.class);
    }

    @Override
    public Map<String, FieldBean> createBeanMap() {
        EO eoRoot = EoRoot.ofClass(getConfigMaps(), readConfigFiles(), Map.class);
        Map<String, Map<String, Object>> mapValues = (Map<String, Map<String, Object>>)eoRoot.get();
        Map<String, FieldBean> fieldBeanMap = new TreeMap<>();
        for (Map.Entry<String, Map<String, Object>> entry: mapValues.entrySet()) {
            Map<String, Object> mapValue = entry.getValue();
            FieldBean fieldBean = new FieldBean(mapValue);
            if (!fieldBean.hasFieldKey()) {
                LOG.warn("No modelKey defined for {}.", entry.getKey());
                continue;
            }
            if (!fieldBean.hasNaturalId()) {
                fieldBean.setNaturalId( entry.getKey());
            }
            fieldBeanMap.put(entry.getKey(), fieldBean);
        }
        return fieldBeanMap;
    }
}
