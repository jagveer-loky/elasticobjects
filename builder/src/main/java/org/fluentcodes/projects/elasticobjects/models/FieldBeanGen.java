package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;

public class FieldBeanGen extends FieldBean implements
        FieldBeanJavaMethods,
        FieldBeanJavaScriptMethods {

    public FieldBeanGen() {
        super();
    }
    public FieldBeanGen(String key) {
        super(key);
    }

    public FieldBeanGen(final Map values) {
        super(values);
    }

    protected FieldBeanGen(final FieldConfigInterface fieldBean) {
        super();
        merge(fieldBean);
        setSuper(true);
    }


    public void merge(final FieldConfigInterface fieldBean) {
        super.merge(fieldBean);
    }
}
