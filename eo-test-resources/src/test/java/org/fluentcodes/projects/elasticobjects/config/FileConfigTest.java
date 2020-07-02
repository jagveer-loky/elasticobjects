package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.calls.FileCall;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 12.10.2016.
 */
public class FileConfigTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(FileConfigTest.class);

    @Test
    public void findConfigInCache() throws Exception {
        FileConfig config = TestEOProvider.EO_CONFIGS.findFile(FILE_SOURCE_TXT);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void readConfigClassPath() throws Exception {
        Map<String, Config> map = TestConfig.readClassPathConfig(FileConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(FILE_SOURCE_TXT));
    }

    @Test
    public void readMapTest() throws Exception {
        Map map = TestConfig.readMapFromFile(CONFIG_FILE_TEST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(FILE_TEST_TEST));
    }

    @Test
    public void readConfigTest() throws Exception {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_FILE_TEST, FileConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(FILE_RESULT_STRING));
    }


    @Test
    public void fromEoConfigsCache() throws Exception {
        TestHelper.printStartMethod();
        ModelInterface cache = TestEOProvider.EO_CONFIGS.findModel(BasicTest.class);
        Assert.assertNotNull(cache.getField(F_TEST_STRING));
        Assert.assertEquals(F_TEST_STRING, cache.getField(F_TEST_STRING).getFieldKey());
        ModelInterface subTest = cache.getFieldModel(F_SUB_TEST);
        Assert.assertEquals(M_SUB_TEST, subTest.getModelKey());
        Assert.assertEquals(F_TEST_STRING, subTest.getField(F_TEST_STRING).getFieldKey());
    }

    @Test
    public void createWithTargetTxt() throws Exception {
        TestHelper.printStartMethod();
        FileConfig cache = (FileConfig) TestEOProvider.EO_CONFIGS.find(FileConfig.class, FILE_TARGET_TXT);
        Assert.assertEquals(FILE_TARGET_TXT, cache.getFileKey());
        Assert.assertEquals(FILE_TARGET_TXT, cache.getFileName());
        Assert.assertEquals("$[TMP]/elastic-objects", cache.getFilePath());
        Assert.assertEquals(H_LOCALHOST, cache.getHostConfig().getHostName());
    }

    @Test
    public void createWithTestSourceTxt() throws Exception {
        TestHelper.printStartMethod();
        FileConfig cache = (FileConfig) TestEOProvider.EO_CONFIGS.find(FileConfig.class, FILE_TEST_SOURCE_TXT);
        Assert.assertEquals(FILE_TEST_SOURCE_TXT, cache.getFileKey());
        Assert.assertEquals(FILE_SOURCE_NAME_TXT, cache.getFileName());
        Assert.assertEquals(FileConfig.CLASSPATH + Path.DELIMITER + S_STRING, cache.getFilePath());
        Assert.assertEquals(H_LOCALHOST, cache.getHostConfig().getHostName());
    }

    @Test
    public void createWithSourceTxt() throws Exception {
        TestHelper.printStartMethod();
        FileConfig config = TestEOProvider.EO_CONFIGS.findFile(FILE_SOURCE_TXT);
        Assert.assertEquals(FILE_SOURCE_TXT, config.getFileKey());
        Assert.assertEquals(FILE_SOURCE_NAME_TXT, config.getFileName());
        Assert.assertEquals(FileConfig.CLASSPATH, config.getFilePath());
        Assert.assertEquals(H_LOCALHOST, config.getHostConfig().getHostName());
    }

    @Test
    public void createWithClasspathSourceTxt() throws Exception {
        TestHelper.printStartMethod();
        FileConfig config = (FileConfig) TestEOProvider.EO_CONFIGS.find(FileConfig.class, FILE_CLASSPATH_SOURCE_TXT);
        Assert.assertEquals(FILE_CLASSPATH_SOURCE_TXT, config.getFileKey());
        Assert.assertEquals(FILE_SOURCE_NAME_TXT, config.getFileName());
        Assert.assertEquals(FileConfig.CLASSPATH, config.getFilePath());
        Assert.assertEquals(H_LOCALHOST, config.getHostConfig().getHostName());
    }

    @Test
    public void createWithLocalhostSourceTxt() throws Exception {
        TestHelper.printStartMethod();
        FileConfig config = (FileConfig) TestEOProvider.EO_CONFIGS.find(FileConfig.class, FILE_LOCALHOST_SOURCE_TXT);
        Assert.assertEquals(FILE_LOCALHOST_SOURCE_TXT, config.getFileKey());
        Assert.assertEquals(FILE_SOURCE_NAME_TXT, config.getFileName());
        Assert.assertEquals(FileConfig.CLASSPATH, config.getFilePath());
        Assert.assertEquals(H_LOCALHOST, config.getHostConfig().getHostName());
    }


    @Test
    public void withFileKeyTest() throws Exception {
        TestHelper.printStartMethod();
        final FileConfig config = (FileConfig) TestEOProvider.EO_CONFIGS.find(FileConfig.class, FILE_TEST_TEST);
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

        AssertEO.compare(TestEOProvider.EO_CONFIGS, config);

    }

    @Test
    public void assertListFileCache() throws Exception {
        TestHelper.printStartMethod();
        ModelInterface model = TestEOProvider.EO_CONFIGS.findModel(FileConfig.class.getSimpleName());
        Assert.assertEquals(ShapeTypes.CONFIG, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertTrue(model.isObject());
        Assert.assertFalse(model.isScalar());
    }


    @Test
    public void createActionWithSourceTxt() throws Exception {
        FileCall action = new FileCall(TestEOProvider.EO_CONFIGS, FILE_SOURCE_TXT);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, action.getFileConfig().getDescription());
    }
}

