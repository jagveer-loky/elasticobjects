package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;


import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EONoPathChildMap_models_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EONoPathChildMap_models_Test.class);

    @Test
    public void givenString_withInteger_changeNothing() throws Exception {
        final EO eoString = DevObjectProvider
                .createEOBuilder()
                .setPath(F_TEST_STRING)
                .set(S_STRING);
        EOTest
                .setEO_ok(eoString, Integer.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, String.class, eoString.getModelClass());
    }

}

