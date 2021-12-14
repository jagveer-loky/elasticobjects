package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.models.Scope;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface IEOModel {
    Models getModels();

    default Class<?> getModelClass() {
        return getModels().getModelClass();
    }

    default ModelConfig getModel() {
        return getModels().getModel();
    }

    default boolean isTransient(final String fieldName) {
        return getModel().hasFieldConfig(fieldName) && getModel().getField(fieldName).isTransient();
    }

    default boolean isContainer() {
        return !isScalar();
    }

    default boolean isList() {
        return getModel().isList();
    }

    default boolean isObject() {
        return getModel().isObject();
    }

    default boolean isScalar() {
        return getModel().isScalar() || getModels().isEnum();
    }

    default boolean isMap() {
        return getModel().isMap();
    }

    default boolean isNull() {
        return getModel().isNull();
    }

    default ConfigMaps getConfigMaps() {
        return getModels().getConfigMaps();
    }

    default Scope getScope() {
        return getConfigMaps().getScope();
    }
}
