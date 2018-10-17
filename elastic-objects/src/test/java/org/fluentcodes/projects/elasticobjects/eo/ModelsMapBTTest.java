package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_KEY;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 12.09.2018.
 */
public class ModelsMapBTTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ModelsMapBTTest.class);

    private static final Class SUB_CLASS = BasicTest.class;
    private static final String NAME =F_BASIC_TEST;
    private static final Models MODELS = createModels();
    private static final Models createModels () {
        try {
            return new Models(TestObjectProvider.EO_CONFIGS_CACHE, Map.class, SUB_CLASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Test
    public void setNull_ok() throws Exception {
        final Models child = MODELS.createChildForSet(NAME, null);
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void mapNull_ok() throws Exception {
        final Models child = MODELS.createChildForMap(NAME, null);
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void setJson_fails() throws Exception {
        try {
            MODELS.createChildForSet(NAME, TestObjectProvider.createJSONToEOMapEmpty());
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        }
        catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapJson_ok() throws Exception {
        final Models child = MODELS.createChildForMap(NAME, TestObjectProvider.createJSONToEOMapEmpty());
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void mapString_fails() throws Exception {
        try {
            MODELS.createChildForMap(NAME, S_STRING);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        }
        catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void setString_fails() throws Exception {
        try {
            MODELS.createChildForSet(NAME, S_STRING);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        }
        catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void setMap_fails() throws Exception {
        try {
            MODELS.createChildForSet(NAME, MapProvider.createEmpty());
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        }
        catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapMap_ok() throws Exception {
        final Models child = MODELS.createChildForMap(NAME, MapProvider.create());
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void setList_fails() throws Exception {
        try {
            MODELS.createChildForSet(NAME, ListProvider.createEmpty());
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        }
        catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapList_fails() throws Exception {
        try {
            MODELS.createChildForMap(NAME, ListProvider.createEmpty());
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        }
        catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void setST_fails() throws Exception {
        try {
            MODELS.createChildForSet(NAME, STProvider.createEmpty());
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        }
        catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }


    @Test
    public void mapST_ok() throws Exception {
        final Models child = MODELS.createChildForMap(F_KEY, new SubTest());
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void mapBT_ok() throws Exception {
        final Models child = MODELS.createChildForMap(F_KEY, new BasicTest());
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }


    @Test
    public void setBT_ok() throws Exception {
        final Models child = MODELS.createChildForSet(F_KEY, new BasicTest());
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

}
