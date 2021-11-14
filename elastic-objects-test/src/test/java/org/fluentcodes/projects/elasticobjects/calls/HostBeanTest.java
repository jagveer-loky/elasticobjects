package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.junit.Test;

/**
 * Created by Werner on 11.10.2016.
 */
public class HostBeanTest {

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(HostBean.class);
    }
    @Test
    public void createByModelConfig() {
        ModelConfigChecks.create(HostBean.class);
    }

}
