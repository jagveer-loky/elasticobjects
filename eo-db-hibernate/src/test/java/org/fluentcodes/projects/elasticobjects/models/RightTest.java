package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;

import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.JSONReader;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 04.11.2016.
 */
public class RightTest {
    private static final Logger LOG = LogManager.getLogger(RightTest.class);

    @Test
    public void checkRight()  {
        ModelInterface model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(Right.class);
        model.resolve();
    }

    @Test
    public void checkRightListMany()  {
        ModelInterface model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(RightList.class);
        model.resolve();
    }

    @Test
    public void checkRightMapMany()  {
        ModelInterface model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(RightMap.class);
        model.resolve();
    }

    @Test
    public void readRightMap()  {
        List result = JSONReader.readListFromDataClassPath(TestObjectProvider.EO_CONFIGS_CACHE, RightMap.class);
        Assert.assertTrue(result.size() > 0);
    }

    @Test
    public void readRightList()  {
        List result = JSONReader.readListFromDataClassPath(TestObjectProvider.EO_CONFIGS_CACHE, RightList.class);
        Assert.assertTrue(result.size() > 0);
    }

    @Test
    public void readRight()  {
        List result = JSONReader.readListFromDataClassPath(TestObjectProvider.EO_CONFIGS_CACHE, Right.class);
        Assert.assertTrue(result.size() > 0);
        //AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, result);
    }

    @Test
    public void elementary()  {
        
        ModelInterface model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(RightMap.class);
        RightMap right = (RightMap) model.create();
        right.setId(1L);
        Assert.assertEquals(new Long(1L), right.getId());
        right.setRightName("A");
        Assert.assertEquals("A", right.getRightName());

        Map<String, LeftMap> leftMap = new LinkedHashMap<>();
        ModelInterface modelLeft = TestObjectProvider.EO_CONFIGS_CACHE.findModel(LeftMap.class);
        LeftMap left = (LeftMap) modelLeft.create();
        left.setId(2L);
        left.setLeftName("B");
        leftMap.put("B", left);
        right.setLeftMap(leftMap);
        Assert.assertEquals("B", right.getLeftMap().get("B").getLeftName());

        EO adapter = TestObjectProvider.createEOBuilder()
                .set(right);
        Assert.assertEquals("B", adapter.get("leftMap/B/leftName"));
        //AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, right);
    }
}
