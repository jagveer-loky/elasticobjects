package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.ValueCallTest;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_VALUE_TEST;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 13.4.2017.
 */
public class ValueConfigTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ValueConfigTest.class);

    @Test
    public void testItemTestFromProvider() throws Exception {
        ValueConfig cache = (ValueConfig) TestEOProvider.EO_CONFIGS.find(ValueConfig.class, ValueCallTest.VC_TEST_ITEM);
    }


    @Test
    public void findIntValue1() throws Exception {
        final ValueConfig config = TestEOProvider.EO_CONFIGS.findValue(ValueCallTest.VC_INT_VALUE1);
        Assert.assertNotNull(config);
        Assert.assertEquals(new Long(S_INTEGER), config.getValue());
    }

    @Test
    public void findContent() throws Exception {
        TestHelper.printStartMethod();
        final ValueConfig config = TestEOProvider.EO_CONFIGS.findValue(ValueCallTest.VC_CONTENT);
        Assert.assertNotNull(config);
        Assert.assertEquals(S_STRING, config.getValue());
        //TODO Assert.assertEquals(SAMPLE_CONTENT, config.getMapPath());
    }

    @Test
    public void readValueConfigTest() throws Exception {
        TestHelper.printStartMethod();
        final Map<String, Config> configMap = TestConfig.readConfigMapFromFile(CONFIG_VALUE_TEST, ValueConfig.class);
        ValueConfig config = (ValueConfig) configMap.get(ValueCallTest.VC_INT_VALUE1);
        Assert.assertNotNull(config);
        config = (ValueConfig) configMap.get(SAMPLE_CONTENT);
    }


}
