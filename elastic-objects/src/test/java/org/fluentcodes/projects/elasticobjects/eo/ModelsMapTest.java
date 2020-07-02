package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 12.09.2018.
 */
public class ModelsMapTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ModelsMapTest.class);
    private static final Models MODELS = createModels();

    private static final Models createModels() {
        try {
            return new Models(TestEOProvider.EO_CONFIGS, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Test
    public void setNull_ok() throws Exception {
        final Models child = MODELS.createChildForSet(F_TEST_OBJECT, null, null);
        Assert.assertEquals(Map.class, child.getModelClass());
    }

    @Test
    public void mapNull_ok() throws Exception {
        final Models child = MODELS.createChildForMap(F_TEST_OBJECT, null);
        Assert.assertEquals(Map.class, child.getModelClass());
    }

    @Test
    public void setJson_fails() throws Exception {
        try {
            MODELS.createChildForSet(F_UNTYPED_MAP, TestEOProvider.createJSONToEOMapEmpty(), null);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapJson_ok() throws Exception {
        final Models child = MODELS.createChildForMap(F_UNTYPED_MAP, TestEOProvider.createJSONToEOMapEmpty());
        Assert.assertEquals(Map.class, child.getModelClass());
    }

    @Test
    public void mapString_ok() throws Exception {
        final Models child = MODELS.createChildForMap(F_TEST_STRING, S_STRING);
        Assert.assertEquals(String.class, child.getModelClass());
    }

    @Test
    public void setString_ok() throws Exception {
        final Models child = MODELS.createChildForSet(F_TEST_STRING, S_STRING, null);
        Assert.assertEquals(String.class, child.getModelClass());
    }

    @Test
    public void mapStringWithDateClass_ok() throws Exception {
        final Models child = MODELS.createChildForMap(F_TEST_STRING, S_STRING, Date.class);
        Assert.assertEquals(Date.class, child.getModelClass());
    }

    @Test
    public void setMap_ok() throws Exception {
        final Models child = MODELS.createChildForSet(F_UNTYPED_MAP, MapProvider.createEmpty(), null);
        Assert.assertEquals(LinkedHashMap.class, child.getModelClass());
    }

    @Test
    public void mapMap_ok() throws Exception {
        final Models child = MODELS.createChildForMap(F_UNTYPED_MAP, MapProvider.create());
        Assert.assertEquals(LinkedHashMap.class, child.getModelClass());
    }

    @Test
    public void setList_ok() throws Exception {
        final Models child = MODELS.createChildForSet(F_UNTYPED_LIST, ListProvider.create(), null);
        Assert.assertEquals(ArrayList.class, child.getModelClass());
    }

    @Test
    public void mapList_ok() throws Exception {
        final Models child = MODELS.createChildForMap(F_UNTYPED_LIST, ListProvider.create());
        Assert.assertEquals(ArrayList.class, child.getModelClass());
    }

    @Test
    public void setBT_ok() throws Exception {
        final Models child = MODELS.createChildForSet(F_BASIC_TEST, BTProvider.create(), null);
        Assert.assertEquals(BasicTest.class, child.getModelClass());
    }

    @Test
    public void mapBT_ok() throws Exception {
        final Models child = MODELS.createChildForMap(F_BASIC_TEST, BTProvider.create());
        Assert.assertEquals(BasicTest.class, child.getModelClass());
    }

    @Test
    public void mapST_ok() throws Exception {
        final Models child = MODELS.createChildForMap(F_SUB_TEST, STProvider.create());
        Assert.assertEquals(SubTest.class, child.getModelClass());

    }

}
