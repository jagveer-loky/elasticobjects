package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.EOTestHelper;
import org.fluentcodes.projects.elasticobjects.test.MapProviderEO;
import org.fluentcodes.projects.elasticobjects.test.STProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EOPathMap_value_models_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOPathMap_value_models_Test.class);

    @Test
    public void givenMapSTEmpty_withST_ok()  {
        final EO eoMapEmpty = MapProviderEO.createEmpty();
        final EO child = EOTestHelper
                .mapEO_ok(eoMapEmpty, F_SUB_TEST, STProvider.createString(), Map.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, child.getModelClass());
    }

}

