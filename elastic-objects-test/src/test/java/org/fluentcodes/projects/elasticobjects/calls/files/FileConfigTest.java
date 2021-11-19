package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IConfigurationTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

/**
 * Created by Werner on 12.10.2016.
 */
public class FileConfigTest implements IConfigurationTests {
    public static final String FILE_TEST_TXT = "FileTest.txt";

    @Override
    public Class<?> getModelConfigClass() {
        return FieldConfig.class;
    }

    @Override
    @Test
    public void create_throwsEoException()  {
        assertCreateThrowingException();
    }

    @Override
    @Test
    public void compareModelConfig()  {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig()  {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareConfigurations()  {
        assertLoadedConfigurationsEqualsPersisted();
    }

    @Test
    public void testScope__findFileConfig_FileTestTxt__found()  {
        FileConfig config = ProviderConfigMaps.CONFIG_MAPS.findFile(FILE_TEST_TXT);
        Assertions.assertThat(config).isNotNull();
        Assertions.assertThat(config.getDescription()).isNotNull();
    }

}

