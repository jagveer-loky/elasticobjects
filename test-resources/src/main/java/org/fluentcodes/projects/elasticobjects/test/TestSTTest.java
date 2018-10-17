package org.fluentcodes.projects.elasticobjects.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.F_SUB_TEST;

/**
 * @author Werner Diwischek
 * @since 5.10.2018
 */
public class TestSTTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(TestSTTest.class);

    @Test
    public void createSTRecursivePath0() {
        Assert.assertEquals("", STProviderEO.createSTPath(0));
    }

    @Test
    public void createSTRecursivePath1() {
        Assert.assertEquals(F_SUB_TEST, STProviderEO.createSTPath(1));
    }

    @Test
    public void createSTRecursivePath2() {
        Assert.assertEquals(F_SUB_TEST + Path.DELIMITER + F_SUB_TEST, STProviderEO.createSTPath(2));
    }
}

