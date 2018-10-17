package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Werner on 04.11.2016.
 */
public class ConfigDivTest extends TestHelper {

    private static final Logger LOG = LogManager.getLogger(ConfigDivTest.class);

    @Test
    public void testMap() throws Exception {
        TestHelper.printStartMethod();
        ModelInterface mapModel = TestObjectProvider.EO_CONFIGS_CACHE.findModel(M_MAP);
        Assert.assertEquals(M_MAP, mapModel.getModelKey());
        Map map = (Map) mapModel.create();
        Assert.assertEquals(LinkedHashMap.class, map.getClass());

        mapModel.set(S_TEST_STRING, map, S_STRING);
        Assert.assertEquals(S_STRING, map.get(S_TEST_STRING));
        Assert.assertEquals(S_STRING, mapModel.get(S_TEST_STRING, map));
        Assert.assertTrue(mapModel.isMap());
    }

    @Test
    public void testString() throws Exception {
        TestHelper.printStartMethod();
        ModelInterface scalarModel = TestObjectProvider.EO_CONFIGS_CACHE.findModel(M_STRING);
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
