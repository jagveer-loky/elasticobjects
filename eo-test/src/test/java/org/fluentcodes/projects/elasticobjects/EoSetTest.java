package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.test.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EoSetTest {
    private static final Logger LOG = LogManager.getLogger(EoSetTest.class);


    @Test
    public void givenMapEmpty_withPathAndString_ok()  {
        final EO eo = TestProviderRootDev.createEo();
        eo.set(S_STRING_OTHER, F_TEST_STRING);
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(F_TEST_STRING)).isEqualTo(S_STRING_OTHER);
    }

    @Test
    public void givenString_withString_fails()  {
        final EO eo = TestProviderRootDev.createEo(S_STRING);
        eo.set(S_STRING_OTHER, F_TEST_STRING);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(String.class);
        Assertions.assertThat(eo.get()).isEqualTo(S_STRING);
    }

    @Test
    public void givenBtEmpty_withPathAndString_ok()  {
        final EO eo = TestProviderRootTest.createEo(new BasicTest());
        eo.set(S_STRING_OTHER, F_TEST_STRING);
        Assertions.assertThat(eo.getModelClass()).isEqualTo(BasicTest.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void givenBtEmpty_withPathNotExisting_hasErrors()  {
        final EO eo = TestProviderRootTest.createEo(new BasicTest());
        eo.set(S_STRING_OTHER, S_KEY1);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

    @Test
    public void givenBtEmpty_whenSetScalarWithObject_hasErrors()  {
        final EO eo = TestProviderRootTest.createEo(new BasicTest());
        eo.set(new BasicTest(), F_TEST_STRING);
        Assertions.assertThat(eo.getLog()).contains("Problem setting non scalar value ");
    }

    @Test
    public void givenBtEmpty_setObjectFieldWithScalar_hasErrors()  {
        final EO eo = TestProviderRootTest.createEo(new BasicTest());
        eo.set(S_STRING, F_BASIC_TEST);
        Assertions.assertThat(eo.getLog()).contains("Problem setting scalar value ");
    }

    @Test
    public void givenMapEmpty_setScalarTypePathWithMap_hasError()  {
        final EO eoMap = TestProviderRootTest.createEo();
        eoMap.set(TestProviderMapJson.SMALL.createBt(), S_LEVEL0);
        eoMap.set(TestProviderMapJson.SMALL.createBt(), S_LEVEL0, F_TEST_INTEGER);
        Assertions.assertThat(eoMap.getLog()).contains("Tried to map scalar child");
    }

    /**
     * Given eo with no Model
     * when adding json map small to a path
     * values will be added with the correct type to the path.
     */
    @Test
    public void givenMapEmpty_withJsonSmall_ok()  {
        final EO eoEmpty = TestProviderRootDev.createEo();
        eoEmpty.set(TestProviderMapJson.SMALL.content(), S_LEVEL0);
        Assertions.assertThat(eoEmpty.getEo(S_LEVEL0).getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eoEmpty.get(S_LEVEL0, F_TEST_INTEGER)).isEqualTo(S_INTEGER);
        Assertions.assertThat(eoEmpty.get(S_LEVEL0, F_TEST_STRING)).isEqualTo(S_STRING);
    }

    @Test
    public void givenListEmpty_withJsonListSmall_ok()  {
        final EO eoEmpty = TestProviderRootDev.createEo();
        eoEmpty.set(TestProviderListJson.JSON_SMALL.content(), S_LEVEL0);
        Assertions.assertThat(eoEmpty.getEo(S_LEVEL0).getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eoEmpty.get(S_LEVEL0, S1)).isEqualTo(S_INTEGER);
        Assertions.assertThat(eoEmpty.get(S_LEVEL0, S0)).isEqualTo(S_STRING);
    }

    @Test
    public void givenListEmpty_withBoolean_ok()  {
        final EO root = TestProviderRootDev.createEo(new ArrayList<>());
        root
                .set(S_BOOLEAN, S0);
        Assert.assertEquals(S_BOOLEAN, root.get(S0));
    }
}

