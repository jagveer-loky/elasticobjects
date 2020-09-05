package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TestHProvider;
import org.fluentcodes.projects.elasticobjects.calls.ListParams;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.models.LeftList;
import org.fluentcodes.projects.elasticobjects.models.RightList;

import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Werner on 16.12.2017.
 */

public class HibernateRightListTest {
    protected static final HConfig HIBERNATE_LIST = TestHProvider.findHibernateCache("h2:mem:rightList");
    protected static final ModelInterface RIGHT_LIST = TestObjectProvider.findModel(RightList.class);
    protected static final ModelInterface LEFT_LIST = TestObjectProvider.findModel(LeftList.class);
    private static final Logger LOG = LogManager.getLogger(HibernateRightListTest.class);

    @Test
    public void listMany()  {
        

        List resultRight = HIBERNATE_LIST.read(RIGHT_LIST, new ListParams());
        Assert.assertEquals(1, resultRight.size());
        Assert.assertEquals(new Long(2L), ((RightList) resultRight.get(0)).getId());
        EO adapter = TestObjectProvider.createEOBuilder()
                .set(resultRight);
        adapter.setCheckObjectReplication(true);
        Assert.assertEquals("3Left", adapter.get("0/leftList/0/leftName"));
        //AssertEO.compare(adapter);

        List resultLeft = HIBERNATE_LIST.read(LEFT_LIST, new ListParams());
        adapter = TestObjectProvider.createEOBuilder()
                .set(resultLeft);
        Assert.assertEquals("1Name", adapter.get("0/rightList/0/rightName"));
    }
}
