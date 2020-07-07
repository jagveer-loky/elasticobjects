package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.BTProviderEO;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.fluentcodes.projects.elasticobjects.test.EOTestHelper;
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
    public void givenString_withStringAndString_ok()  {
        final EO eoString = DevObjectProvider.createEO(String.class);
        EOTestHelper
                .setEOValue_ok(eoString, S_STRING, String.class);
    }

    @Test
    public void givenMapString_withString_ok()  {
        final EO eoString = MapProviderEODev.createString();
        EOTestHelper
                .setEOValue_ok(eoString, S_STRING, String.class);
    }

    @Test
    public void givenMapString_withStringAndHashMap_ok()  {
        final EO eoString = MapProviderEODev.createString();
        EOTestHelper
                .setEOValue_ok(eoString, S_STRING, LinkedHashMap.class);
    }

    @Test
    public void givenBTString_withStringAndMap_ok()  {
        final EO eoString = BTProviderEO.createString();
        EOTestHelper
                .setEOValue_ok(eoString, S_STRING, Map.class);
    }

    @Test
    public void givenBTString_withIntegerAndInteger_ok()  {
        final EO eoString = BTProviderEO.createString();
        EOTestHelper
                .setEOValue_ok(eoString, S_INTEGER, Integer.class);
    }

    @Test
    public void givenBTUntypedMap_WithHashMapMap_ok()  {
        final EO eoString = BTProviderEO.createMap();
        EOTestHelper
                .setEOValue_ok(eoString, new HashMap(), LinkedHashMap.class);
    }


}

