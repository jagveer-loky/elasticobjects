package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface.MODULE_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;

public class ModelConfigsMapTest {
    public static final ModelConfigsMap MODEL_CONFIG_MAP = new ModelConfigsMap(Scope.TEST);

    @Test
    public void new_modelConfigsMap_Scope_Dev__find_Map__notNull() {
        ModelConfig config = (ModelConfig)new ModelConfigsMap(Scope.DEV).find(Map.class.getSimpleName());
        Assertions.assertThat(config).isNotNull();
    }

    @Test
    public void TEST_modelConfigMap__find_Map__notNull() {
        ModelConfig config = (ModelConfig) MODEL_CONFIG_MAP.find(Map.class.getSimpleName());
        Assertions.assertThat(config).isNotNull();
    }

    @Test
    public void TEST_modelConfigMap__find_AnObject__notNull() {
        ModelConfig config = (ModelConfig) MODEL_CONFIG_MAP.find(AnObject.class.getSimpleName());
        Assertions.assertThat(config).isNotNull();
    }

    @Test
    public void TEST_modelConfigMap__find_ModelConfigObject__notNull() {
        ModelConfig config = (ModelConfig) MODEL_CONFIG_MAP.find(ModelConfigObject.class.getSimpleName());
        Assertions.assertThat(config).isNotNull();
    }

    @Test
    public void TEST_find_ModelBean__getField_packagePath__notNull() {
        ModelConfigObject config = (ModelConfigObject) MODEL_CONFIG_MAP.find(ModelBean.class.getSimpleName());
        Assertions.assertThat(config.hasField(PACKAGE_PATH)).isTrue();
        FieldConfigInterface fieldConfig = config.getField(PACKAGE_PATH);
        Assertions.assertThat(fieldConfig.getDescription()).isNotNull(); // value from field merge
    }

    @Test
    public void TEST_find_ModelBean__create__notNull() {
        ModelConfigObject config = (ModelConfigObject) MODEL_CONFIG_MAP.find(ModelBean.class.getSimpleName());
        ModelBean modelBean = (ModelBean)config.create();
        Assertions.assertThat(modelBean).isNotNull(); // value from field merge
    }

    @Test
    public void TEST_create_ModelBean__set_packagePath_test__get_packagePath_test() {
        ModelConfigObject config = (ModelConfigObject) MODEL_CONFIG_MAP.find(ModelBean.class.getSimpleName());
        ModelBean modelBean = (ModelBean)config.create();
        config.set(PACKAGE_PATH, modelBean, "test");
        Assertions.assertThat(config.get(PACKAGE_PATH, modelBean)).isEqualTo("test"); // value from field merge
    }

    @Test
    public void TEST_find_ModelBean__getField_moduleScope__notNull() {
        ModelConfigObject config = (ModelConfigObject) MODEL_CONFIG_MAP.find(ModelBean.class.getSimpleName());
        Assertions.assertThat(config.hasField(MODULE_SCOPE)).isTrue(); // from super
        FieldConfigInterface fieldConfig = config.getField(MODULE_SCOPE);
        Assertions.assertThat(fieldConfig.getSuper()).isNotNull();
        Assertions.assertThat(fieldConfig.getSuper()).isTrue();
        Assertions.assertThat(fieldConfig.getDescription()).isNotNull(); // value from field merge
    }

    @Test
    public void TEST_create_ModelBean__set_moduleScope_test__get_moduleScope_test() {
        ModelConfigObject config = (ModelConfigObject) MODEL_CONFIG_MAP.find(ModelBean.class.getSimpleName());
        ModelBean modelBean = (ModelBean)config.create();
        config.set(MODULE_SCOPE, modelBean, "test");
        Assertions.assertThat(config.get(MODULE_SCOPE, modelBean)).isEqualTo("test"); // value from field merge
    }
}

