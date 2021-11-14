package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.JavascriptFieldTypeCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.FieldConfigInterface.FINAL;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfigInterface.PROPERTY;


public interface Model extends ModelConfigInterface, ConfigBeanInterface {
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
    default FieldBeanInterface getFieldBean(final String fieldKey) {
        return getFieldBeans().get(fieldKey);
    }

}
