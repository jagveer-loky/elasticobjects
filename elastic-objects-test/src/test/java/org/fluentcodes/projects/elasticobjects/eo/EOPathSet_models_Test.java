package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.BTProviderEO;
import org.fluentcodes.projects.elasticobjects.test.EOTestHelper;
import org.fluentcodes.projects.elasticobjects.test.MapProviderEODev;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.F_TEST_STRING;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.F_UNTYPED_MAP;

public class EOPathSet_models_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOPathSet_models_Test.class);


    @Test
    public void givenMapString_withInteger_fails()  {
        final EO eoString = MapProviderEODev.createString();
        EOTestHelper
                .setEO_fails(eoString, F_TEST_STRING, Integer.class);
    }

    @Test
    public void givenMapString_withMap_fails()  {
        final EO eoString = MapProviderEODev.createString();
        EOTestHelper
                .setEO_fails(eoString, F_TEST_STRING, Map.class);
    }

    @Test
    public void givenBTString_withMap_fails()  {
        final EO eoString = BTProviderEO.createString();
        EOTestHelper
                .setEO_fails(eoString, F_TEST_STRING, Map.class);
    }

    @Test
    public void givenBTString_withInteger_fails()  {
        final EO eoString = BTProviderEO.createString();
        EOTestHelper
                .setEO_fails(eoString, F_TEST_STRING, Integer.class);
    }

    //TODO
    @Test
    public void givenBTUntypedMap_withHashMap_fails()  {
        final EO eoString = BTProviderEO.createMap();
        EOTestHelper
                .setEO_fails(eoString, F_UNTYPED_MAP, LinkedHashMap.class);
    }


}

