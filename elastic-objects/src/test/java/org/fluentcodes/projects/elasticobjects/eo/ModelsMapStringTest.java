package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 12.09.2018.
 */
public class ModelsMapStringTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ModelsMapStringTest.class);

    private static final Class SUB_CLASS = String.class;
    private static final String NAME = F_TEST_STRING;
    private static final Models MODELS = createModels();

    private static final Models createModels() {
        try {
            return new Models(TestEOProvider.EO_CONFIGS, Map.class, SUB_CLASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Test
    public void setNull_ok() throws Exception {
        final Models child = MODELS.createChildForSet(NAME, null, null);
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
            MODELS.createChildForSet(NAME, TestEOProvider.createJSONToEOMapEmpty(), null);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapJson_fails() throws Exception {
        try {
            MODELS.createChildForMap(NAME, TestEOProvider.createJSONToEOMapEmpty());
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapString_ok() throws Exception {
        final Models child = MODELS.createChildForMap(NAME, S_STRING);
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void setString_ok() throws Exception {
        final Models child = MODELS.createChildForSet(NAME, S_STRING, null);
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }


    @Test
    public void mapDate_ok() throws Exception {
        final Models child = MODELS.createChildForMap(NAME, SAMPLE_DATE);
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void setDate_ok() throws Exception {
        final Models child = MODELS.createChildForSet(NAME, SAMPLE_DATE, null);
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void setMap_fails() throws Exception {
        try {
            MODELS.createChildForSet(NAME, MapProvider.createEmpty(), null);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapMap_fails() throws Exception {
        try {
            MODELS.createChildForMap(NAME, MapProvider.createEmpty());
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void setList_fails() throws Exception {
        try {
            MODELS.createChildForSet(NAME, ListProvider.createEmpty(), null);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapList_fails() throws Exception {
        try {
            MODELS.createChildForMap(NAME, ListProvider.createEmpty());
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void setBT_fails() throws Exception {
        try {
            MODELS.createChildForSet(NAME, BTProvider.createEmpty(), null);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapBT_fails() throws Exception {
        try {
            MODELS.createChildForMap(NAME, BTProvider.createEmpty());
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapST_fails() throws Exception {
        try {
            MODELS.createChildForMap(NAME, STProvider.createEmpty());
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

}
