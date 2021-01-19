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

    default void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        if (hasShapeType()) {
            setConfigModelKey(getShapeType().getModelConfig().getSimpleName());
        }
    }

    void setFieldBeans(Map<String, FieldBeanInterface> fieldBeans);
    Map<String, FieldBeanInterface> getFieldBeans();
    default boolean hasFieldBeans() {
        return getFieldBeans()!=null && !getFieldBeans().isEmpty();
    }
    default FieldBeanInterface getFieldBean(final String fieldKey) {
        return getFieldBeans().get(fieldKey);
    }

    default void setFinal(Boolean value) {
        getProperties().put(FINAL, value);
    }

    default void setBean(String value) {
        getProperties().put(BEAN, value);
    }

    default void setCreate(Boolean create) {
        if (getProperties()==null) {
            throw new EoException("Could not set create .. properties not defined");
        }
        getProperties().put(CREATE, create);
    }

    default void setShapeType(ShapeTypes shapeType) {
        getProperties().put(SHAPE_TYPE, shapeType);
    }

    default void defaultShapeType() {
        if (!hasShapeType()) return;
    }

    default ShapeTypes convertShapeType(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof ShapeTypes) {
            return (ShapeTypes) value;
        }
        else if (value instanceof String) {
            return ShapeTypes.valueOf((String)value);
        }
        throw new EoException("Could not map " + value + " " + value.getClass());
    }

    default void mergeShapeType(Object value) {
        if (hasShapeType())  {
            return;
        }
        setShapeType(convertShapeType(value));
     }

    default void setDefaultImplementation(String defaultImplementation) {
        getProperties().put(DEFAULT_IMPLEMENTATION, defaultImplementation);
    }

    default void setJavascriptType(String value) {
        getProperties().put(JAVASCRIPT_TYPE, value);
    }

    default String getJavascriptType() {
        return JavascriptFieldTypeCall.createType(getModelKey());
    }

    default boolean hasJavaScriptType() {
        return getJavascriptType()!=null && !getJavascriptType().isEmpty();
    }

    default void setIdKey(String value) {
        getProperties().put(ID_KEY, value);
    }
    default void setNaturalKeys(List<String> value) {
        getProperties().put(NATURAL_KEYS, value);
    }
    default void setTable(String value) {
        getProperties().put(TABLE, value);
    }

    default Boolean isDbAnnotated() {
        return getProperties().containsKey(DB_ANNOTATED)?
                (Boolean)getProperties().get(DB_ANNOTATED) : false;
    }
    default Model setDbAnnotated(Boolean dbAnnotated) {
        getProperties().put(DB_ANNOTATED, dbAnnotated);
        return this;
    }

    default Model setProperty(Boolean value) {
        getProperties().put(PROPERTY, value);
        return this;
    }

    default Boolean isAbstract() {
        return getProperties().containsKey(ABSTRACT)?
                (Boolean)getProperties().get(ABSTRACT) : false;
    }
    default Model setAbstract(Boolean dbAnnotated) {
        getProperties().put(ABSTRACT, dbAnnotated);
        return this;
    }

}
