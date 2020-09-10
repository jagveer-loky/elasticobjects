package org.fluentcodes.projects.elasticobjects.calls.csv;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.junit.Test;

/**
 * Created by Werner on 11.10.2016.
 */
public class CsvConfigTest {
    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(CsvConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(CsvConfig.class);
    }

    @Test
    public void resolveModel()  {
        ConfigModelChecks.resolve(CsvConfig.class);
    }

    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(FileConfig.class);
    }

    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(FileConfig.class);
    }
}
