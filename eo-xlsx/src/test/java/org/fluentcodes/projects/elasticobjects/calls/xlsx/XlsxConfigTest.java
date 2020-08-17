package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created by Werner on 04.11.2016.
 */
public class XlsxConfigTest {
    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(XlsxConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(XlsxConfig.class);
    }

    @Test
    public void resolveModel()  {
        ConfigModelChecks.resolve(XlsxConfig.class);
    }

    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(XlsxConfig.class);
    }

    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(XlsxConfig.class);
    }

}
