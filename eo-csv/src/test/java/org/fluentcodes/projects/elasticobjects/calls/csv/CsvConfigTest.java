package org.fluentcodes.projects.elasticobjects.calls.csv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

import org.fluentcodes.projects.elasticobjects.models.Config;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.CEO_STATIC.CONFIG_CSV_TEST;
import static org.fluentcodes.projects.elasticobjects.CEO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.H_LOCALHOST;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

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
        ConfigChecks.resolveConfigurations(CsvConfig.class);
    }

    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(CsvConfig.class);
    }
}
