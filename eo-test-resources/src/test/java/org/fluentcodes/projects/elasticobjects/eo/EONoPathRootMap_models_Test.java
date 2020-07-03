package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.BTProviderEO;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.fluentcodes.projects.elasticobjects.test.MapProviderEO;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Models will be used to create an empty value. But with not mapable values not changes occures!
 * When a scalar is the model, an exception occured and subsequently a log entry is written, since there is no default value for scalar models.
 * When a non scalar is the model, a new object is created but without changes!
 * So the only relevant test is {@link EONoPathRootMap_value_Test}
 */
public class EONoPathRootMap_models_Test {
    private static final Logger LOG = LogManager.getLogger(EONoPathRootMap_models_Test.class);

    public static EO mapEO_ok(final EO root, final Class... classes)  {
        final Class modelClass = root.getModelClass();
        final EO child = root.add()
                .setModels(classes)
                .map(null);
        Assert.assertEquals(INFO_COMPARE_FAILS, modelClass, root.getModelClass());
        Assert.assertTrue(INFO_EMPTY_FAILS + root.getLog(), root.getLog().isEmpty());
        return child;
    }

    public static EO mapEO_changeNothing(final EO root, final Class... classes)  {
        final EO child = root.add()
                .setModels(classes)
                .map(null);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, root.getLog().isEmpty());
        return child;
    }

    @Test
    public void givenString_withInteger_changeNothing()  {
        final EO eoString = DevObjectProvider
                .createEOBuilder()
                .set(S_STRING);
        mapEO_ok(eoString, Integer.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoString.get());
    }

    @Test
    public void givenString_WithList_changeNothing()  {
        final EO eoString = DevObjectProvider
                .createEOBuilder()
                .set(S_STRING);
        mapEO_ok(eoString, List.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoString.get());
    }

    @Test
    public void givenMapString_withMap_changeNothing()  {
        final EO eoMap = MapProviderEO.createString();
        mapEO_ok(eoMap, Map.class);
    }

    @Test
    public void givenMapWithString_withString_changeNothing()  {
        final EO eoMap = MapProviderEO.createString();
        Assert.assertEquals(S_STRING, eoMap.get(F_TEST_STRING));
        mapEO_ok(eoMap, String.class);
        Assert.assertEquals(S_STRING, eoMap.get(F_TEST_STRING));
    }

    @Test
    public void givenBTWithString_withMap_changeNothing()  {
        final EO eoBTString = BTProviderEO.createString();
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));

        mapEO_ok(eoBTString, Map.class);
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
    }

    @Test
    public void givenBTString_withMap_changeNothing()  {
        final EO eoBTString = BTProviderEO.createString();
        mapEO_ok(eoBTString, Map.class);
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
    }
}


