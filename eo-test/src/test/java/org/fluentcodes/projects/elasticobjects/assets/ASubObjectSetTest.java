package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 9.7.2017.
 */
public class ASubObjectSetTest {
    private static final Logger LOG = LogManager.getLogger(ASubObjectSetTest.class);

    @Test
    public void givenBean_whenSetTestString_thenOk() {
        ASubObject test = new ASubObject();
        test.setMyString("x");
        Assert.assertEquals("x", test.getMyString());
    }
}
