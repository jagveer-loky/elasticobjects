package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Werner Diwischek
 * @since 4.7.2020
 */

public class EoSetValueTest {
    private static final Logger LOG = LogManager.getLogger(EoSetValueTest.class);

    @Test
    public void testSinglePathScalar_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        Assert.assertNotNull(root);
        root.set("testPath", "test");
        String value = (String)root.get("test");
        Assert.assertEquals("testPath", value);
    }

    @Test
    public void testDoublePathScalar_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        Assert.assertNotNull(root);
        root.set("testPath", "test", "test2");
        Assert.assertEquals("testPath", root.get("test","test2"));
    }

    @Test
    public void testTreePathScalar_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        Assert.assertNotNull(root);
        root.set("testPath", "test","test2","test3");
        Assert.assertEquals("testPath", root.get("test","test2","test3"));
    }

    @Test
    public void testTwoDoublePathScalarWithSameStartDirectory_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        Assert.assertNotNull(root);
        root.set("testPath", "test","test2");
        root.set("testPath2", "test","test3");
        Assert.assertEquals("testPath", root.get("test","test2"));
        Assert.assertEquals("testPath2", root.get("test","test3"));
    }

    @Test
    public void testMap_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        Map<String,String> map = new LinkedHashMap<>();
        map.put("test3","testMap");
        root.set(map,"test","test3");
        Assert.assertEquals("testMap", root.get("test","test2","test3"));
    }

    @Test
    public void testMapComplex_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        Map map = new LinkedHashMap<>();
        map.put("test3","testMap");
        Map<String,String> map2 = new LinkedHashMap<>();
        map2.put("test5","testMap2");
        map.put("test4", map2);
        root.set(map,"test","test2");
        Assert.assertEquals("testMap", root.get("test","test2","test3"));
        Assert.assertEquals("testMap2", root.get("test","test2","test4","test5"));
    }
}


