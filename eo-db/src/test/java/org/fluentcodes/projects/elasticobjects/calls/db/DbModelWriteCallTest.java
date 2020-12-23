package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.junit.Test;

/**
 * Created by Werner on 30.10.2020.
 */
public class DbModelWriteCallTest {
    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(DbModelWriteCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(DbModelWriteCall.class);
    }

    @Test
    public void resolveModelConfig()  {
        ModelConfigChecks.resolve(DbModelWriteCall.class);
    }
}

