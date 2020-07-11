package org.fluentcodes.projects.elasticobjects.wiki.eo;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class Convert {
    @Test
    public void test()  {
        final EO eo = TestProviderRootTest.createEo();

        final Map map = new HashMap();
        map.put("testString", "value");
        map.put("testFloat", 1.1D);

        final EO child = eo.setPathValue("(BasicTest)level0", map);
        Assert.assertEquals(BasicTest.class, child.getModelClass());
        assertEquals("value", child.get("testString"));
        assertEquals(1.1F, child.get("testFloat"));
        assertEquals("value", eo.get("level0/testString"));

        assertEquals(Map.class, eo.getModelClass());
        assertEquals(LinkedHashMap.class, eo.get().getClass());

        assertEquals(BasicTest.class, eo.getEo("level0").getModelClass());
        assertEquals(BasicTest.class, eo.get("level0").getClass());
        assertEquals(BasicTest.class, child.get().getClass());
        assertEquals(Float.class, eo.getEo("level0/testFloat").getModelClass());
    }
}
