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
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EoSetScalarTest {
    private static final Logger LOG = LogManager.getLogger(EoSetScalarTest.class);

    /**
     * Basic wiki example
     */
    @Test
    public void givenDev_whenSetLongPath_thenChildAndRootCanBeAccessed() {
        final EO eo = ProviderRootDevScope.createEo();
        EO child = eo.set("value","level0/level1/level2/level3");
        Assertions.assertThat(child.get()).isEqualTo("value");
        Assertions.assertThat(eo.get("level0/level1/level2/level3")).isEqualTo("value");
    }

    @Test
    public void givenDev_whenSetStringWithLevel0_thenValueIsSet()  {
        final EO eo = ProviderRootDevScope.createEo();
        eo.set(S_STRING_OTHER, S_LEVEL0);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(String.class);
        Assertions.assertThat(eo.getEo().getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(S_LEVEL0)).isEqualTo(S_STRING_OTHER);
        Assertions.assertThat(eo.isContainer()).isTrue();
        Assertions.assertThat(eo.getEo(S_LEVEL0).isContainer()).isFalse();
    }

    @Test
    public void givenDev_whenSetStringWithLevel0Level1_thenValueIsSet()  {
        final EO eo = ProviderRootDevScope.createEo();
        final EO child = eo.set(S_STRING_OTHER, S_LEVEL0, S_LEVEL1);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(child.getModelClass()).isEqualTo(String.class);
        Assertions.assertThat(child.getPathAsString()).isEqualTo(Path.DELIMITER + S_LEVEL0 + Path.DELIMITER + S_LEVEL1);
    }

    @Test
    public void givenDevString_whenSetStringWithPath_thenExceptionThrown()  {
        final EO eo = ProviderRootDevScope.createEo(S_STRING);
        Assertions
                .assertThatThrownBy(()->{eo.set(S_STRING_OTHER, BasicTest.TEST_STRING);})
                .hasMessage("Could not create a field value with 'testString' for a scalar (String) parent on path '/'");
    }

    @Test
    public void givenDevList_whenSetBooleanWith0_thenSetValue()  {
        final EO root = new EoRoot(ProviderRootDevScope.EO_CONFIGS, List.class);
        root.set(S_BOOLEAN, S0);
        Assert.assertEquals(S_BOOLEAN, root.get(S0));
    }

    @Test
    public void givenDevList_whenSetStringWithLevel0_thenSetValueWith0()  {
        final EO root = new EoRoot(ProviderRootDevScope.EO_CONFIGS, List.class);
        root.set(S_STRING, S_LEVEL0);
        Assert.assertEquals(S_STRING, root.get(S0));
    }

    @Test
    public void givenEmpty_whenSetStringWithLevel0Level1Level2Level2_thenValueSet()  {
        final EO eo = ProviderRootDevScope.createEo();
        final EO child = eo.set("value", S_LEVEL0,S_LEVEL1,S_LEVEL2, S_LEVEL3);
        //assertEquals("value", child.get());
        assertEquals("value", eo.get(S_LEVEL0,S_LEVEL1,S_LEVEL2, S_LEVEL3));

        assertEquals(Map.class, eo.getModelClass());
        assertEquals(LinkedHashMap.class, eo.get().getClass());

        assertEquals(Map.class, eo.getEo("level0").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("level0").getClass());

        assertEquals(Map.class, eo.getEo("level0","level1").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("level0","level1").getClass());

        assertEquals(Map.class, eo.getEo("level0","level1","level2").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("level0","level1","level2").getClass());

        assertEquals(String.class, eo.getEo(S_LEVEL0,S_LEVEL1,S_LEVEL2, S_LEVEL3).getModelClass());
        assertEquals(String.class, eo.get(S_LEVEL0,S_LEVEL1,S_LEVEL2, S_LEVEL3).getClass());

        assertEquals(Map.class,child.getEo("..").getModelClass());
        assertEquals(LinkedHashMap.class, child.get("..").getClass());

        assertEquals(Map.class,child.getRoot().getModelClass());
        assertEquals(LinkedHashMap.class, child.getRoot().get().getClass());
        assertEquals("/", child.getRoot().getPathAsString());
    }
}

