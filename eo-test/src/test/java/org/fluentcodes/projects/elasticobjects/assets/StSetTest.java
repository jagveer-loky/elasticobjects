package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 9.7.2017.
 */
public class StSetTest {
    private static final Logger LOG = LogManager.getLogger(StSetTest.class);

    @Test
    public void givenBean_whenSetTestString_thenOk() {
        SubTest test = new SubTest();
        test.setTestString("x");
        Assert.assertEquals("x", test.getTestString());
    }
}
