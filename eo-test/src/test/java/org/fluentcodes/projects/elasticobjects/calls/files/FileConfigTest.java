package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;

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
    public void ConfigKey_BasicTestCsv__compare__xpected()  {
        ConfigChecks.compareConfiguration(FileConfig.class, "BasicTest.csv");
    }


    @Test
    public void whenResolveConfigEntries_thenNoError()  {
        ConfigChecks.resolveConfigurations(FileConfig.class);
    }

    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(FileConfig.class);
    }

    @Test
    public void givenTestScope_whenFindFileContent_thenFound()  {
        FileConfig config = ProviderRootTestScope.EO_CONFIGS.findFile(FILE_TEST_TXT);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

}

