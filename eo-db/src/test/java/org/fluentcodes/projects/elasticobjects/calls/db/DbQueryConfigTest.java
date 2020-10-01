package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created by Werner on 28.1.2018.
 */
public class DbQueryConfigTest {
    protected static final String DB_QUERY_SUB_TEST = "h2:mem:basic:ASubObject";
    protected static final String DB_QUERY_BASIC_TEST = "h2:mem:basic:AnObject";
    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(DbQueryConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DbQueryConfig.class);
    }

    @Test
    public void resolveModel()  {
        ConfigModelChecks.resolve(DbQueryConfig.class);
    }

    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(DbSqlConfig.class);
    }

}

