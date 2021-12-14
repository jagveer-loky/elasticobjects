package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IConfigurationTests;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps.CONFIG_MAPS;

/**
 * Created by Werner on 17.11.2021.
 */
public class FieldConfigTest implements IConfigurationTests {

    @Override
    public Class<?> getModelConfigClass() {
        return FieldConfig.class;
    }

    @Override
    @Test
    public void createThrowsEoException() {
        assertCreateThrowingException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Test
    public void getDescription() {
        FieldConfig fieldConfig = (FieldConfig) CONFIG_MAPS.find(FieldConfig.class, "description");
        assertThat(fieldConfig).isNotNull();
    }
}
