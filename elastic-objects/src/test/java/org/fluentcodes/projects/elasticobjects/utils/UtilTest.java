package org.fluentcodes.projects.elasticobjects.utils;

import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.junit.Assert;
import org.junit.Test;

public class UtilTest {
    @Test
    public void upper() {
        String in = "testUpdate-All";
        String upper = Util.upper(in);
        Assert.assertEquals("TEST_UPDATE_ALL", upper);
    }

    @Test
    public void lower() {
        String in = "TEST_UPDATE_ALL";
        String upper = Util.lower(in);
        Assert.assertEquals("testUpdateAll", upper);
    }

    @Test
    public void upperFirstChar() {
        String in = TEO_STATIC.S_STRING;
        String upper = Util.upperFirstCharacter(in);
        Assert.assertEquals("Test", upper);
    }
}
