package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created by Werner on 30.10.2020.
 */
public class DbModelWriteCallTest {
    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(DbModelWriteCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DbModelWriteCall.class);
    }

    @Test
    public void resolveModelConfig()  {
        ConfigModelChecks.resolve(DbModelWriteCall.class);
    }
}

