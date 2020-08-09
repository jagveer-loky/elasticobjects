package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_MAP;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_STRING;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 04.11.2016.
 */
public class ConfigDivTest {

    private static final Logger LOG = LogManager.getLogger(ConfigDivTest.class);

    @Test
    public void testMap()  {
        
        ModelInterface mapModel = ProviderRootTest.EO_CONFIGS.findModel(M_MAP);
        Assert.assertEquals(M_MAP, mapModel.getModelKey());
        Map map = (Map) mapModel.create();
        Assert.assertEquals(LinkedHashMap.class, map.getClass());

        mapModel.set(S_TEST_STRING, map, S_STRING);
        Assert.assertEquals(S_STRING, map.get(S_TEST_STRING));
        Assert.assertEquals(S_STRING, mapModel.get(S_TEST_STRING, map));
        Assert.assertTrue(mapModel.isMap());
    }

    @Test
    public void testString()  {
        
        ModelInterface scalarModel = ProviderRootTest.EO_CONFIGS.findModel(M_STRING);
        Assert.assertEquals(M_STRING, scalarModel.getModelKey());
        Assert.assertTrue(scalarModel.isScalar());

        String scalar = (String) scalarModel.create();
        Assert.assertNull(scalar);
        try {
            scalarModel.set(S_TEST_STRING, scalar, S_STRING);
            throw new Exception(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
        try {
            Assert.assertEquals(S_STRING, scalarModel.get(S_TEST_STRING, scalar));
            throw new Exception(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

}
