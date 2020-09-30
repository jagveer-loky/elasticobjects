package org.fluentcodes.projects.elasticobjects.external;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.calls.db.DbQueryCall;
import org.junit.Test;


public class DbTest {
    @Test
    public void create() {
        DbQueryCall dbQueryCall = new DbQueryCall();
        Assertions.assertThat(dbQueryCall).isNotNull();
    }
    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DbQueryCall.class);
    }
}
