package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.BTProviderEO;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.fluentcodes.projects.elasticobjects.test.EOTest;
import org.fluentcodes.projects.elasticobjects.test.MapProviderEODev;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_INTEGER;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * With set with value and model only value will be used and model ignored.
 */
public class EOPathSet_value_models_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOPathSet_value_models_Test.class);

    @Test
    public void givenString_withStringAndString_ok() throws Exception {
        final EO eoString = DevObjectProvider.createEOString();
        EOTest
                .setEOValue_ok(eoString, S_STRING, String.class);
    }

    @Test
    public void givenMapString_withString_ok() throws Exception {
        final EO eoString = MapProviderEODev.createString();
        EOTest
                .setEOValue_ok(eoString, S_STRING, String.class);
    }

    @Test
    public void givenMapString_withStringAndHashMap_ok() throws Exception {
        final EO eoString = MapProviderEODev.createString();
        EOTest
                .setEOValue_ok(eoString, S_STRING, LinkedHashMap.class);
    }

    @Test
    public void givenBTString_withStringAndMap_ok() throws Exception {
        final EO eoString = BTProviderEO.createString();
        EOTest
                .setEOValue_ok(eoString, S_STRING, Map.class);
    }

    @Test
    public void givenBTString_withIntegerAndInteger_ok() throws Exception {
        final EO eoString = BTProviderEO.createString();
        EOTest
                .setEOValue_ok(eoString, S_INTEGER, Integer.class);
    }

    @Test
    public void givenBTUntypedMap_WithHashMapMap_ok() throws Exception {
        final EO eoString = BTProviderEO.createMap();
        EOTest
                .setEOValue_ok(eoString, new HashMap(), LinkedHashMap.class);
    }


}

