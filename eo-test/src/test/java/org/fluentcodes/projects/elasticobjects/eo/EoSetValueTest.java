package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.LogLevel;
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
        root.setPathValue("test", "testPath");
        String value = (String)root.get("test");
        Assert.assertEquals("testPath", value);
    }

    @Test
    public void testDoublePathScalar_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        Assert.assertNotNull(root);
        root.setPathValue("test/test2", "testPath");
        Assert.assertEquals("testPath", root.get("test/test2"));
    }

    @Test
    public void testTreePathScalar_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        Assert.assertNotNull(root);
        root.setPathValue("test/test2/test3", "testPath");
        Assert.assertEquals("testPath", root.get("test/test2/test3"));
    }

    @Test
    public void testTwoDoublePathScalarWithSameStartDirectory_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        Assert.assertNotNull(root);
        root.setPathValue("test/test2", "testPath");
        root.setPathValue("test/test3", "testPath2");
        Assert.assertEquals("testPath", root.get("test/test2"));
        Assert.assertEquals("testPath2", root.get("test/test3"));
    }

    @Test
    public void testMap_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        Map<String,String> map = new LinkedHashMap<>();
        map.put("test3","testMap");
        root.setPathValue("test/test2", map);
        Assert.assertEquals("testMap", root.get("test/test2/test3"));
    }

    @Test
    public void testMapComplex_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        Map map = new LinkedHashMap<>();
        map.put("test3","testMap");
        Map<String,String> map2 = new LinkedHashMap<>();
        map2.put("test5","testMap2");
        map.put("test4", map2);
        root.setPathValue("test/test2", map);
        Assert.assertEquals("testMap", root.get("test/test2/test3"));
        Assert.assertEquals("testMap2", root.get("test/test2/test4/test5"));
    }

    @Test
    public void testBT_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        BasicTest basicTest = new BasicTest();
        basicTest.setTestString("testObject");
        root.setPathValue("test/test2", basicTest);
        Assert.assertEquals("testObject", root.get("test/test2/testString"));
        Assert.assertEquals(BasicTest.class, root.getEo("test/test2").getModelClass());
    }

    @Test
    public void testBTByPath_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        root.setPathValue("(BasicTest)test/testString", "testObject");
        Assert.assertEquals("testObject", root.get("test/testString"));
        Assert.assertEquals(BasicTest.class, root.getEo("test").getModelClass());
    }

    @Test
    public void testBTByPathAddInteger_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        root.setPathValue("(BasicTest)test/testString", "testObject");
        root.setPathValue("test/testInt", 1);
        Assert.assertEquals(1, root.get("test/testInt"));
        Assert.assertTrue(root.getLog().isEmpty());
        Assert.assertEquals(BasicTest.class, root.getEo("test").getModelClass());
    }

    @Test
    public void testBTByPathAddNonsense_nok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        root.setPathValue("(BasicTest)test/testString", "testObject");
        root.setPathValue("test/nonsense", 1);
        Assert.assertNull(root.get("test/nonsense"));
        Assert.assertFalse(root.getLog().isEmpty());
        Assert.assertEquals(BasicTest.class, root.getEo("test").getModelClass());
    }

    @Test
    public void testBTByPathWithEmpty_ok()  {
        final EoRoot root = TestProviderRootDev.createEo();
        root.setPathValue("(BasicTest)test");
        Assert.assertEquals(BasicTest.class, root.getEo("test").getModelClass());
    }

    @Test
    public void testBTRoot_ok()  {
        final EoRoot root = new EoRoot(TestProviderRootDev.EO_CONFIGS, LogLevel.DEBUG, BasicTest.class);
        root.setPathValue("testString", "testObject");
        Assert.assertEquals(BasicTest.class, root.getModelClass());
        Assert.assertEquals("testObject", root.get("testString"));
    }
}


