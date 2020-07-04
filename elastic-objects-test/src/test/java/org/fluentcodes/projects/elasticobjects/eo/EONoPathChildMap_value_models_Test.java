package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.fluentcodes.projects.elasticobjects.test.EOTestHelper;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EONoPathChildMap_value_models_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EONoPathChildMap_value_models_Test.class);

    @Test
    public void givenMapString_withInteger_ok()  {
        final EO eoMapString = DevObjectProvider.createEOMapString();
        final EO child = eoMapString.getChild(F_TEST_STRING);
        EOTestHelper
                .mapEOValue_ok(child, S_INTEGER, Integer.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER.toString(), child.get());
    }


}

