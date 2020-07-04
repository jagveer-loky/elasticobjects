package org.fluentcodes.projects.elasticobjects.wiki.eo;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class SetValue {
    @Test
    public void test()  {
        final EO eo = DevObjectProvider.createEO();
        final EO child = eo.add("level0/level1/level2/key")
                .set("value");
        assertEquals("value", child.get());
        assertEquals("value", eo.get("level0/level1/level2/key"));

        assertEquals(Map.class, eo.getModelClass());
        assertEquals(LinkedHashMap.class, eo.get().getClass());

        assertEquals(Map.class, eo.getChild("level0").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("level0").getClass());

        assertEquals(Map.class, eo.getChild("level0/level1").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("level0/level1").getClass());

        assertEquals(Map.class, eo.getChild("level0/level1/level2").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("level0/level1/level2").getClass());

        assertEquals(String.class, eo.getChild("level0/level1/level2/key").getModelClass());
        assertEquals(String.class, eo.get("level0/level1/level2/key").getClass());

        assertEquals(Map.class,child.getChild("..").getModelClass());
        assertEquals(LinkedHashMap.class, child.get("..").getClass());
        assertEquals("/level0/level1/level2", child.getChild("..").getPathAsString());

        assertEquals(Map.class,child.getRoot().getModelClass());
        assertEquals(LinkedHashMap.class, child.getRoot().get().getClass());
        assertEquals("", child.getRoot().getPathAsString());
    }
}
