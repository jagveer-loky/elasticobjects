package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;


public interface Model extends ModelConfigInterface {
    String JAVASCRIPT_TYPE = "javascriptType";
    String DB_ANNOTATED = "dbAnnotated";
    String ABSTRACT = "abstract";

    void setModelKey(String modelKey);
    void setPackagePath(String packagePath);
    void setSuperKey(String superKey);
    void setInterfaces(String interfaces);

    void setFieldBeans(Map<String, FieldBean> fieldBeans);
    Map<String, FieldBean> getFieldBeans();
    default boolean hasFieldBeans() {
        return getFieldBeans()!=null && !getFieldBeans().isEmpty();
    }
    default FieldBean getFieldBean(final String fieldKey) {
        return getFieldBeans().get(fieldKey);
    }

}
