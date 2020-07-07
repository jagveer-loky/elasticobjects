package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.*;
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
    public void givenString_withInteger_changeNothing()  {
        TestHelper.printStartMethod();
        final EO eoString = TestEOProvider.create(String.class);
        EOTestHelper.setEO_ok(eoString, Integer.class);
    }

    @Test
    public void givenMapString_WithInteger_changeNothing()  {
        final EO eoString = MapProviderEODev.createString();
        EOTestHelper.setEO_ok(eoString, Integer.class);
    }

    @Test
    public void givenMapString_withHashMap_changeNothing()  {
        final EO eoString = MapProviderEODev.createString();
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoString.get(F_TEST_STRING));
        EOTestHelper.setEO_ok(eoString, LinkedHashMap.class);
    }

    @Test
    public void givenBTString_withMap_changeNothing()  {
        final EO eoBTString = BTProviderEO.createString();
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoBTString.getRoot().get(F_TEST_STRING));
        EOTestHelper.setEO_ok(eoBTString.getRoot(), LinkedHashMap.class);
    }

    @Test
    public void givenBTString_withInteger_changeNothing()  {
        final EO eoString = BTProviderEO.createString();
        EOTestHelper.setEO_ok(eoString, Integer.class);
        TestObjectProvider.checkLogEmpty(eoString);
    }
}

