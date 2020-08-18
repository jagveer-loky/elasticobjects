package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.TestProviderBtJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * @author Werner Diwischek
 * @since 4.7.2020
 */

public class EoSetMapTest {
    private static final Logger LOG = LogManager.getLogger(EoSetMapTest.class);

    @Test
    public void givenDev_whenSetNullWithLevel0_thenEmptyMapIsSet()  {
        final EO eo = ProviderRootDevScope.createEo();
        final EO child = eo.set(null, S_LEVEL0);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(child.getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(child.getPathAsString()).isEqualTo(Path.DELIMITER + S_LEVEL0);
    }

    @Test
    public void testMap_ok()  {
        final EoRoot root = ProviderRootDevScope.createEo();
        Map<String,String> map = new LinkedHashMap<>();
        map.put("test3","testMap");
        root.set(map,"test","test2");
        Assert.assertEquals("testMap", root.get("test","test2","test3"));
    }

    @Test
    public void givenDev_whenSetMapComplex_thenValuesAreMapped()  {
        final EoRoot root = ProviderRootDevScope.createEo();


        Map<String,String> subMap = new LinkedHashMap<>();
        subMap.put("test5","testMap2");

        Map rootMap = new LinkedHashMap<>();
        rootMap.put("test3","testMap");
        rootMap.put("test4", subMap);

        root.set(rootMap,"test1","test2");
        Assert.assertEquals("testMap", root.get("test1","test2","test3"));
        Assert.assertEquals("testMap2", root.get("test1","test2","test4","test5"));
    }

    /**
     * Given eo with no Model
     * when adding json map small to a path
     * values will be added with the correct type to the path.
     */
    @Test
    public void givenMapEmpty_whenSetJsonSmall_ok()  {
        final EO eoEmpty = ProviderRootDevScope.createEo();
        eoEmpty.set(TestProviderBtJson.SMALL.content(), S_LEVEL0);
        Assertions.assertThat(eoEmpty.getEo(S_LEVEL0).getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eoEmpty.get(S_LEVEL0, BasicTest.TEST_INTEGER)).isEqualTo(S_INTEGER);
        Assertions.assertThat(eoEmpty.get(S_LEVEL0, BasicTest.TEST_STRING)).isEqualTo(S_STRING);
    }
}


