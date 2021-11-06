package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Werner on 21.10.2021.
 */

public class FieldFactory extends ConfigFactory<FieldBean, FieldConfig> {
    public FieldFactory() {
        this(Scope.DEV);
    }
    public FieldFactory(Scope scope) {
        super(scope, FieldBean.class, FieldConfig.class);
    }

    @Override
    public Map<String, FieldBean> createBeanMap(ConfigMaps configMaps) {
        EO eoRoot = EoRoot.ofClass(configMaps, readConfigFiles(), Map.class);
        Map<String, Map<String, Object>> mapValues = (Map<String, Map<String, Object>>)eoRoot.get();
        Map<String, FieldBean> fieldBeanMap = new TreeMap<>();
        for (Map.Entry<String, Map<String, Object>> entry: mapValues.entrySet()) {
            FieldBean fieldBean = new FieldBean(entry.getValue());
            fieldBeanMap.put(entry.getKey(), fieldBean);
        }
        return fieldBeanMap;
    }

    public Map<String, FieldBean> createBeanMap() {
        return this.createBeanMap(new ConfigMaps(Scope.DEV));
    }
}
