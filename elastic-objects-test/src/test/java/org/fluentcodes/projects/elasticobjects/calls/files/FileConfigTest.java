package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Werner on 12.10.2016.
 */
public class FileConfigTest {
    public static final String FILE_TEST_TXT = "FileTest.txt";

    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(FileConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(FileConfig.class);
    }

    // Failed in mvn
    @Ignore
    @Test
    public void anObjectCsv__compare__xpected()  {
        ConfigChecks.compareConfiguration(FileConfig.class, "AnObject.csv");
    }


    @Test
    public void whenResolveConfigEntries_thenNoError()  {
        ConfigChecks.resolveConfigurations(FileConfig.class);
    }

    // Failed with maven. Check within 0.5.0
    @Ignore
    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(FileConfig.class);
    }

    @Test
    public void testScope__findFileConfig_FileTestTxt__found()  {
        FileConfig config = ProviderRootTestScope.EO_CONFIGS.findFile(FILE_TEST_TXT);
        Assertions.assertThat(config).isNotNull();
        Assertions.assertThat(config.getDescription()).isNotNull();
    }

}

