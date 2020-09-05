package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TestHProvider;
import org.fluentcodes.projects.elasticobjects.calls.ListParams;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.models.Left;
import org.fluentcodes.projects.elasticobjects.models.Right;

import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Werner on 16.12.2017.
 */

public class HibernateRightTest {
    protected static final HConfig HIBERNATE = TestHProvider.findHibernateCache("h2:mem:right");
    protected static final ModelInterface RIGHT = TestObjectProvider.findModel(Right.class);
    protected static final ModelInterface LEFT = TestObjectProvider.findModel(Left.class);
    private static final Logger LOG = LogManager.getLogger(HibernateRightTest.class);

    @Test
    public void simple()  {
        
        List resultRight = HIBERNATE.read(RIGHT, new ListParams());
        Assert.assertEquals(1, resultRight.size());
        Assert.assertEquals(new Long(3L), ((Right) resultRight.get(0)).getId());
        EO adapter = TestObjectProvider.createEOBuilder()
                .set(resultRight);
        adapter.setCheckObjectReplication(true);
        Assert.assertEquals("2Left", adapter.get("0/leftMap/2Left/leftName"));
        //AssertEO.compare(adapter);

        List resultLeft = HIBERNATE.read(LEFT, new ListParams());
        adapter = TestObjectProvider.createEOBuilder()
                .set(resultLeft);
        Assert.assertEquals("1Name", adapter.get("0/rightMap/1Name/rightName"));
    }

}
