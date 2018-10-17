package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 11.10.2016.
 */
public class UserConfigTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(UserConfigTest.class);


    @Test
    public void withGuest() throws Exception {
        TestHelper.printStartMethod();
        UserConfig user = (UserConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(UserConfig.class, RoleConfig.GUEST);
        Assert.assertEquals(RoleConfig.GUEST, user.getUserKey());
    }


}
