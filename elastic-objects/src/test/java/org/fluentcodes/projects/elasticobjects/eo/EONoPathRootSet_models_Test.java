package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.BTProviderEO;
import org.fluentcodes.projects.elasticobjects.test.EOTest;
import org.fluentcodes.projects.elasticobjects.test.MapProviderEODev;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * With no value set will be ignored when path is null...
 * Log message when scalar model.
 */
public class EONoPathRootSet_models_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EONoPathRootSet_models_Test.class);

    @Test
    public void givenString_withInteger_changeNothing() throws Exception {
        TestHelper.printStartMethod();
        final EO eoString = TestObjectProvider.createEOFromJson(String.class);
        EOTest.setEO_ok(eoString, Integer.class);
    }

    @Test
    public void givenMapString_WithInteger_changeNothing() throws Exception {
        final EO eoString = MapProviderEODev.createString();
        EOTest.setEO_ok(eoString, Integer.class);
    }

    @Test
    public void givenMapString_withHashMap_changeNothing() throws Exception {
        final EO eoString = MapProviderEODev.createString();
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoString.get(F_TEST_STRING));
        EOTest.setEO_ok(eoString, LinkedHashMap.class);
    }

    @Test
    public void givenBTString_withMap_changeNothing() throws Exception {
        final EO eoBTString = BTProviderEO.createString();
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoBTString.getRoot().get(F_TEST_STRING));
        EOTest.setEO_ok(eoBTString.getRoot(), LinkedHashMap.class);
    }

    @Test
    public void givenBTString_withInteger_changeNothing() throws Exception {
        final EO eoString = BTProviderEO.createString();
        EOTest.setEO_ok(eoString, Integer.class);
        TestObjectProvider.checkLogEmpty(eoString);
    }
}

