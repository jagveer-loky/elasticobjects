package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created by Werner on 30.10.2020.
 */
public class DbModelDeleteCallTest {
    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(DbModelDeleteCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DbModelDeleteCall.class);
    }

    @Test
    public void resolveModelConfig()  {
        ConfigModelChecks.resolve(DbModelDeleteCall.class);
    }
}

