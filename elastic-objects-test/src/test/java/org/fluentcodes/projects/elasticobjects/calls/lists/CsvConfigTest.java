package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.junit.Test;

/**
 * Created by Werner on 11.10.2016.
 */
public class CsvConfigTest {

    @Test
    public void createByModelConfig_throwsException()  {
        ModelConfigChecks.createThrowsException(CsvConfig.class);
    }

    @Test
    public void resolveModelConfig()  {
        ModelConfigChecks.resolve(CsvConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(CsvConfig.class);
    }

    @Test
    public void resolveModel()  {
        ModelConfigChecks.resolve(CsvConfig.class);
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
