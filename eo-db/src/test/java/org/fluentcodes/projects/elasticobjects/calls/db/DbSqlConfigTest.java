package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Werner on 28.1.2018.
 */
public class DbSqlConfigTest {
    @Test
    public void createByModelConfig_throwsException()  {
        ModelConfigChecks.createThrowsException(DbSqlConfig.class);
    }

    // TODO check within 0.5.0 for mvn test fails.
    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(DbSqlConfig.class);
    }

    @Test
    public void resolveModel()  {
        ModelConfigChecks.resolve(DbSqlConfig.class);
    }

    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(DbSqlConfig.class);
    }

    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(DbSqlConfig.class);
    }
}

