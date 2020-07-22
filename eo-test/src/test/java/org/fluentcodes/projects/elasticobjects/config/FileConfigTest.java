package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.calls.FileCallRead;
import org.fluentcodes.projects.elasticobjects.paths.Path;

import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_FILE_NAME;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.H_LOCALHOST;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 12.10.2016.
 */
public class FileConfigTest {
    private static final Logger LOG = LogManager.getLogger(FileConfigTest.class);

    @Test
    public void findConfigInCache()  {
        FileConfig config = TestProviderRootTest.EO_CONFIGS.findFile(FILE_SOURCE_TXT);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void testReadConfig()  {
        EOConfigMap map = new EOConfigMapImmutable(TestProviderRootTest.EO_CONFIGS, FileConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(FILE_SOURCE_TXT));
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(FILE_RESULT_STRING));
    }


    @Test
    public void fromEoConfigsCache()  {
        
        ModelInterface cache = TestProviderRootTest.EO_CONFIGS.findModel(BasicTest.class);
        Assert.assertNotNull(cache.getFieldConfig(F_TEST_STRING));
        Assert.assertEquals(F_TEST_STRING, cache.getFieldConfig(F_TEST_STRING).getFieldKey());
        ModelInterface subTest = cache.getFieldModel(F_SUB_TEST);
        Assert.assertEquals(M_SUB_TEST, subTest.getModelKey());
        Assert.assertEquals(F_TEST_STRING, subTest.getFieldConfig(F_TEST_STRING).getFieldKey());
    }

    @Test
    public void createWithTargetTxt()  {
        
        FileConfig cache = (FileConfig) TestProviderRootTest.EO_CONFIGS.find(FileConfig.class, FILE_TARGET_TXT);
        Assert.assertEquals(FILE_TARGET_TXT, cache.getFileKey());
        Assert.assertEquals(FILE_TARGET_TXT, cache.getFileName());
        Assert.assertEquals("$[TMP]/elastic-objects", cache.getFilePath());
        Assert.assertEquals(H_LOCALHOST, cache.getHostConfig().getHostName());
    }

    @Test
    public void createWithTestSourceTxt()  {
        
        FileConfig cache = (FileConfig) TestProviderRootTest.EO_CONFIGS.find(FileConfig.class, FILE_TEST_SOURCE_TXT);
        Assert.assertEquals(FILE_TEST_SOURCE_TXT, cache.getFileKey());
        Assert.assertEquals(FILE_SOURCE_NAME_TXT, cache.getFileName());
        Assert.assertEquals(FileConfig.CLASSPATH + Path.DELIMITER + S_STRING, cache.getFilePath());
        Assert.assertEquals(H_LOCALHOST, cache.getHostConfig().getHostName());
    }

    @Test
    public void createWithSourceTxt()  {
        
        FileConfig config = TestProviderRootTest.EO_CONFIGS.findFile(FILE_SOURCE_TXT);
        Assert.assertEquals(FILE_SOURCE_TXT, config.getFileKey());
        Assert.assertEquals(FILE_SOURCE_NAME_TXT, config.getFileName());
        Assert.assertEquals(FileConfig.CLASSPATH, config.getFilePath());
        Assert.assertEquals(H_LOCALHOST, config.getHostConfig().getHostName());
    }

    @Test
    public void createWithClasspathSourceTxt()  {
        
        FileConfig config = (FileConfig) TestProviderRootTest.EO_CONFIGS.find(FileConfig.class, FILE_CLASSPATH_SOURCE_TXT);
        Assert.assertEquals(FILE_CLASSPATH_SOURCE_TXT, config.getFileKey());
        Assert.assertEquals(FILE_SOURCE_NAME_TXT, config.getFileName());
        Assert.assertEquals(FileConfig.CLASSPATH, config.getFilePath());
        Assert.assertEquals(H_LOCALHOST, config.getHostConfig().getHostName());
    }

    @Test
    public void createWithLocalhostSourceTxt()  {
        
        FileConfig config = (FileConfig) TestProviderRootTest.EO_CONFIGS.find(FileConfig.class, FILE_LOCALHOST_SOURCE_TXT);
        Assert.assertEquals(FILE_LOCALHOST_SOURCE_TXT, config.getFileKey());
        Assert.assertEquals(FILE_SOURCE_NAME_TXT, config.getFileName());
        Assert.assertEquals(FileConfig.CLASSPATH, config.getFilePath());
        Assert.assertEquals(H_LOCALHOST, config.getHostConfig().getHostName());
    }


    @Test
    public void withFileKeyTest()  {
        
        final FileConfig config = (FileConfig) TestProviderRootTest.EO_CONFIGS.find(FileConfig.class, FILE_TEST_TEST);
        Assert.assertEquals(FILE_TEST_TEST, config.getFileKey());
        Assert.assertEquals(F_FILE_NAME, config.getFileName());
        Assert.assertEquals(H_LOCALHOST, config.getHostConfig().getHostName());
        Assert.assertEquals(H_LOCALHOST, config.getHostConfig().getHostKey());

        Assert.assertEquals(R_ANONYM, config.getRolePermissions().getRead());
        Assert.assertEquals(R_GUEST, config.getRolePermissions().getWrite());
        Assert.assertEquals(join(CON_COMMA, R_TEST_ROLE_CREATE, R_ADMIN), config.getRolePermissions().getCreate());
        Assert.assertEquals(R_ADMIN, config.getRolePermissions().getDelete());
        Assert.assertEquals(R_SUPER_ADMIN, config.getRolePermissions().getExecute());

        Assert.assertEquals(Permissions.NOTHING, config.getPermissions(R_NOTHING));
        Assert.assertEquals(Permissions.CREATE, config.getPermissions(R_TEST_ROLE_CREATE));
        Assert.assertEquals(Permissions.DELETE, config.getPermissions(R_ADMIN));
        Assert.assertEquals(Permissions.EXECUTE, config.getPermissions(R_SUPER_ADMIN));
        Assert.assertEquals(Permissions.WRITE, config.getPermissions(R_GUEST));
        Assert.assertEquals(Permissions.READ, config.getPermissions(R_ANONYM));

        //AssertEO.compare(TRootTestProvider.EO_CONFIGS, config);

    }

    @Test
    public void assertListFileCache()  {
        
        ModelInterface model = TestProviderRootTest.EO_CONFIGS.findModel(FileConfig.class.getSimpleName());
        Assert.assertEquals(ShapeTypes.CONFIG, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertTrue(model.isObject());
        Assert.assertFalse(model.isScalar());
    }


    @Test
    public void createActionWithSourceTxt()  {
        FileCallRead action = new FileCallRead(TestProviderRootTest.EO_CONFIGS, FILE_SOURCE_TXT);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, action.getFileConfig().getDescription());
    }
}

