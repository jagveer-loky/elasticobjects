package org.fluentcodes.projects.elasticobjects.wiki.eo;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootDev;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class SetValue {
    @Test
    public void test()  {
        final EO eo = TestProviderRootDev.createEo();
        final EO child = eo.setPathValue("level0/level1/level2/key","value");
        assertEquals("value", child.get());
        assertEquals("value", eo.get("level0/level1/level2/key"));

        assertEquals(Map.class, eo.getModelClass());
        assertEquals(LinkedHashMap.class, eo.get().getClass());

        assertEquals(Map.class, eo.getEo("level0").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("level0").getClass());

        assertEquals(Map.class, eo.getEo("level0/level1").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("level0/level1").getClass());

        assertEquals(Map.class, eo.getEo("level0/level1/level2").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("level0/level1/level2").getClass());

        assertEquals(String.class, eo.getEo("level0/level1/level2/key").getModelClass());
        assertEquals(String.class, eo.get("level0/level1/level2/key").getClass());

        assertEquals(Map.class,child.getEo("..").getModelClass());
        assertEquals(LinkedHashMap.class, child.get("..").getClass());
        assertEquals("/level0/level1/level2", child.getEo("..").getPathAsString());

        assertEquals(Map.class,child.getRoot().getModelClass());
        assertEquals(LinkedHashMap.class, child.getRoot().get().getClass());
        assertEquals("", child.getRoot().getPathAsString());
    }
}
