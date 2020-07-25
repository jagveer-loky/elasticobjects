package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class ConditionTest {
    private static final Logger LOG = LogManager.getLogger(ConditionTest.class);

    @Test
    public void eq() {
        Eq eq = new Eq(TEO_STATIC.S_TEST_STRING, TEO_STATIC.S_STRING);
        Assert.assertEquals(TEO_STATIC.S_TEST_STRING, eq.getKey());
        Assert.assertEquals(TEO_STATIC.S_STRING, eq.getValue());
        Assert.assertEquals(TEO_STATIC.S_TEST_STRING + "=:" + TEO_STATIC.S_TEST_STRING + "_0 ", eq.createQuery(new HashMap<>()));
    }

}
