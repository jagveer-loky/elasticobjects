package org.fluentcodes.projects.elasticobjects.calls.files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.EOConfigMap;
import org.fluentcodes.projects.elasticobjects.models.EOConfigMapImmutable;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;

import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 12.10.2016.
 */
public class FileConfigTest {
    private static final Logger LOG = LogManager.getLogger(FileConfigTest.class);

    @Test
    public void testLoadModelFileConfig()  {
        ModelInterface model = ProviderRootTest.findModel(FileConfig.class);
        Assert.assertEquals(ShapeTypes.CONFIG, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertTrue(model.isObject());
        Assert.assertFalse(model.isScalar());
    }

    @Test
    public void testResolveAllConfigs()  {
        Set<String> keys = ProviderRootTest.EO_CONFIGS.getConfigKeys(FileConfig.class);
        Assertions.assertThat(keys).isNotEmpty();
        for (String key: keys) {
            Assertions.assertThat(key).isNotEmpty();
            FileConfig config = ProviderRootTest.EO_CONFIGS.findFile(key);
            Assertions.assertThat(config).isNotNull();
            config.resolve();
        }
    }

    @Test
    public void testfindSourceTxt()  {
        FileConfig config = ProviderRootTest.EO_CONFIGS.findFile(FILE_SOURCE_TXT);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void testCreateConfigMapDirect()  {
        EOConfigMap map = new EOConfigMapImmutable(ProviderRootTest.EO_CONFIGS, FileConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.find(FILE_SOURCE_TXT));
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.find(FILE_RESULT_STRING));
    }

}

