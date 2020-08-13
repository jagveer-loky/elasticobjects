package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.testitemprovider.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EoSetScalarTest {
    private static final Logger LOG = LogManager.getLogger(EoSetScalarTest.class);


    @Test
    public void givenMapEmpty_withPathAndString_ok()  {
        final EO eo = ProviderRootDevScope.createEo();
        eo.set(S_STRING_OTHER, BasicTest.TEST_STRING);
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(BasicTest.TEST_STRING)).isEqualTo(S_STRING_OTHER);
    }

    @Test
    public void givenString_withString_fails()  {
        final EO eo = ProviderRootDevScope.createEo(S_STRING);
        eo.set(S_STRING_OTHER, BasicTest.TEST_STRING);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(String.class);
        Assertions.assertThat(eo.get()).isEqualTo(S_STRING);
    }



    @Test
    public void givenListEmpty_withBoolean_ok()  {
        final EO root = ProviderRootDevScope.createEo(new ArrayList<>());
        root
                .set(S_BOOLEAN, S0);
        Assert.assertEquals(S_BOOLEAN, root.get(S0));
    }

    @Test
    public void givenEmpty_whenSetStringValueWithLongPath_ok()  {
        final EO eo = ProviderRootDevScope.createEo();
        final EO child = eo.set("value", "level0", "level1", "level2", "key");
        assertEquals("value", child.get());
        assertEquals("value", eo.get("level0","level1","level2","key"));

        assertEquals(Map.class, eo.getModelClass());
        assertEquals(LinkedHashMap.class, eo.get().getClass());

        assertEquals(Map.class, eo.getEo("level0").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("level0").getClass());

        assertEquals(Map.class, eo.getEo("level0","level1").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("level0","level1").getClass());

        assertEquals(Map.class, eo.getEo("level0","level1","level2").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("level0","level1","level2").getClass());

        assertEquals(String.class, eo.getEo("level0","level1","level2","key").getModelClass());
        assertEquals(String.class, eo.get("level0","level1","level2","key").getClass());

        assertEquals(Map.class,child.getEo("..").getModelClass());
        assertEquals(LinkedHashMap.class, child.get("..").getClass());
        //assertEquals("/level0","level1","level2", child.getEo("..").getPathAsString());

        assertEquals(Map.class,child.getRoot().getModelClass());
        assertEquals(LinkedHashMap.class, child.getRoot().get().getClass());
        assertEquals("/", child.getRoot().getPathAsString());
    }
}

