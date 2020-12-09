package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.generate.javascript.JavascriptFieldTypeCall;
import org.fluentcodes.projects.elasticobjects.calls.values.StringLowerCall;
import org.fluentcodes.projects.elasticobjects.calls.values.StringPluralCall;
import org.fluentcodes.projects.elasticobjects.domain.Base;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;
import java.util.Map;


public interface ModelBeanProperties extends ModelConfigProperties, Base {
    String MODEL_KEY_LOWER = "modelKeyLower";
    String MODEL_KEY_PLURAL = "modelKeyPlural";
    String MODEL_KEY_PLURAL_LOWER = "modelKeyPluralLower";
    String JAVASCRIPT_TYPE = "javascriptType";
    String DB_ANNOTATED = "dbAnnotated";
    String ABSTRACT = "abstract";

    void setModelKey(String modelKey);
    void setPackagePath(String packagePath);
    void setSuperKey(String superKey);
    void setInterfaces(String interfaces);
    Map<String, FieldBean> getFieldBeans();

    default void setModelKeyUp(String classPath) {
        if (getProperties()==null) {
            throw new EoException("Could not set create .. properties not defined");
        }
        getProperties().put(CLASS_PATH, classPath);
    }


    default void setClassPath(String classPath) {
        if (getProperties()==null) {
            throw new EoException("Could not set create .. properties not defined");
        }
        getProperties().put(CLASS_PATH, classPath);
    }

    default void setCreate(Boolean create) {
        if (getProperties()==null) {
            throw new EoException("Could not set create .. properties not defined");
        }
        getProperties().put(CREATE, create);
    }
    default void setShapeType(ShapeTypes shapeType) {
        if (getProperties()==null) {
            throw new EoException("Could not set shapeType .. properties not defined");
        }
        getProperties().put(SHAPE_TYPE, shapeType);
    }

    default void setDefaultImplementation(String defaultImplementation) {
        getProperties().put(DEFAULT_IMPLEMENTATION, defaultImplementation);
    }

    default void setModelKeyLower(String value) {
        getProperties().put(MODEL_KEY_LOWER, value);
    }

    default String getModelKeyLower() {
        return StringLowerCall.lower(getModelKey());
    }

    default void setModelKeyPlural(String value) {
        getProperties().put(MODEL_KEY_PLURAL, value);
    }

    default String getModelKeyPlural() {
        return StringPluralCall.plural(getModelKey());
    }

    default void setModelKeyPluralLower(String value) {
        getProperties().put(MODEL_KEY_PLURAL_LOWER, value);
    }

    default String getModelKeyPluralLower() {
        return StringLowerCall.lower(StringPluralCall.plural(getModelKey()));
    }

    default void setJavascriptType(String value) {
        getProperties().put(MODEL_KEY_PLURAL_LOWER, value);
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
    default ModelBeanProperties setDbAnnotated(Boolean dbAnnotated) {
        getProperties().put(DB_ANNOTATED, dbAnnotated);
        return this;
    }

    default Boolean isAbstract() {
        return getProperties().containsKey(ABSTRACT)?
                (Boolean)getProperties().get(ABSTRACT) : false;
    }
    default ModelBeanProperties setAbstract(Boolean dbAnnotated) {
        getProperties().put(ABSTRACT, dbAnnotated);
        return this;
    }

}
