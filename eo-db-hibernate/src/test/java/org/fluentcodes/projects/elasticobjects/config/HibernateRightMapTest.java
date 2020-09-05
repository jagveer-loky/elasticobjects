package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TestHProvider;
import org.fluentcodes.projects.elasticobjects.calls.ListParams;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.models.LeftMap;
import org.fluentcodes.projects.elasticobjects.models.RightMap;

import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Werner on 16.12.2017.
 */

public class HibernateRightMapTest {
    protected static final HConfig HIBERNATE_MAP = TestHProvider.findHibernateCache("h2:mem:rightMap");
    protected static final ModelInterface RIGHT_MAP = TestObjectProvider.findModel(RightMap.class);
    protected static final ModelInterface LEFT_MAP = TestObjectProvider.findModel(LeftMap.class);
    private static final Logger LOG = LogManager.getLogger(HibernateRightMapTest.class);

    @Test
    public void mapMany()  {
        
        Assert.assertNotNull(HIBERNATE_MAP);
        Assert.assertNotNull(RIGHT_MAP);
        List resultRight = HIBERNATE_MAP.read(RIGHT_MAP, new ListParams());
        Assert.assertEquals(1, resultRight.size());
        Assert.assertEquals(new Long(2L), ((RightMap) resultRight.get(0)).getId());
        EO adapter = TestObjectProvider.createEOBuilder()
                .set(resultRight);
        adapter.setCheckObjectReplication(true);
        Assert.assertEquals("2Left", adapter.get("0/leftMap/2Left/leftName"));
        //AssertEO.compare(adapter);

        List resultLeft = HIBERNATE_MAP.read(LEFT_MAP, new ListParams());
        EO adapterLeft = TestObjectProvider.createEOBuilder()
                .set(resultLeft);
        Assert.assertEquals("1Name", adapterLeft.get("0/rightMap/1Name/rightName"));
    }

}
