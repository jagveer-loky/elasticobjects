package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.FIELD_KEYS;

public class ModelBeanGen extends ModelBean <FieldBeanGen> implements Model {
    public ModelBeanGen() {
        super();
    }
    public ModelBeanGen(String key) {
        this();
        setNaturalId(key);
    }

    public ModelBeanGen(final Map values) {
        super(values);
        if (!values.containsKey(FIELD_KEYS)) {
            return;
        }
        Object raw = values.get(FIELD_KEYS);
    }

    public void merge(final ModelBean modelBean) {
        super.merge(modelBean);
    }

}
