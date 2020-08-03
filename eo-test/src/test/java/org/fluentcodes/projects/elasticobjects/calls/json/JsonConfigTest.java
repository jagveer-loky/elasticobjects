package org.fluentcodes.projects.elasticobjects.calls.json;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.models.EOConfigMap;
import org.fluentcodes.projects.elasticobjects.models.EOConfigMapImmutable;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.J_SIMPLE_INSERT_WITH_PATH;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on
 *
 * @author Werner Diwischek
 * @since 15.04.2018.
 */
public class JsonConfigTest {

    @Test
    public void givenFoundModel_whenCreateInstance_thenExceptionThrown()  {
        ConfigChecks.findModelAndCreateInstanceExceptionThrown(JsonConfig.class);
    }

    @Test
    public void whenResolveConfigEntries_thenNoError()  {
        ConfigChecks.resolveConfigEntries(JsonConfig.class);
    }

    @Test
    public void whenCompareConfigurations_thenEqual()  {
        ConfigChecks.compareConfigurations(JsonConfig.class);
    }

    @Test
    public void givenTestCache_whenNewEoConfigMap_thenMapIsPopulated()  {
        EOConfigMap map = new EOConfigMapImmutable(ProviderRootTest.EO_CONFIGS, JsonConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.find(J_SIMPLE_INSERT_WITH_PATH));
    }
}
