package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created by Werner on 28.1.2018.
 */
public class DbSqlReadCallTest {
    public static final String H2_MEM_BASIC_BASIC_TEST = "h2:mem:basic:AnObject";

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(DbSqlReadCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DbSqlReadCall.class);
    }

    @Test
    public void resolveModelConfig()  {
        ConfigModelChecks.resolve(DbSqlReadCall.class);
    }
}

