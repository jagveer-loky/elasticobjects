package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.fluentcodes.projects.elasticobjects.test.EOTest;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EOPathMap_models_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOPathMap_models_Test.class);

    @Test
    public void givenMapString_withLinkedHashMapAtStringField_fails() throws Exception {
        final EO eoMapString = DevObjectProvider.createEOMapString();
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoMapString.get(F_TEST_STRING));
        EOTest
                .mapEO_fails(eoMapString, F_TEST_STRING, Map.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoMapString.get(F_TEST_STRING));
    }
}

