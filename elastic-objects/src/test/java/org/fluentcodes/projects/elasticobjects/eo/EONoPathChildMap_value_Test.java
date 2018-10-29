package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.BTProviderEO;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.fluentcodes.projects.elasticobjects.test.EOTest;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EONoPathChildMap_value_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EONoPathChildMap_value_Test.class);

    @Test
    public void givenMapString_withInteger_ok() throws Exception {
        final EO eoMapString = DevObjectProvider.createEOMapString();
        final EO child = eoMapString.getChild(F_TEST_STRING);
        EOTest
                .mapEOValue_ok(child, S_INTEGER);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER.toString(), child.get());
    }

    @Test
    public void givenMapString_withMap_fails() throws Exception {
        final EO eoMapString = DevObjectProvider.createEOMapString();
        final EO child = eoMapString.getChild(F_TEST_STRING);
        EOTest
                .mapEOValue_fails(child, new LinkedHashMap());
    }

    @Test
    public void givenBTString_withMap_fails() throws Exception {
        final EO eoBTString = BTProviderEO.createString();
        final EO child = eoBTString.getChild(F_TEST_STRING);
        EOTest
                .mapEOValue_fails(child, new LinkedHashMap());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get());
    }

    @Test
    public void givenBTString_withInteger_ok() throws Exception {
        final EO eoBTString = BTProviderEO.createString();
        final EO child = eoBTString.getChild(F_TEST_STRING);
        EOTest
                .mapEOValue_ok(child, S_INTEGER);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER.toString(), child.get());
    }
}

