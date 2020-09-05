package org.fluentcodes.projects.elasticobjects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.TestDbProvider;
import org.fluentcodes.projects.elasticobjects.TestHProvider;


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 13.7.2016.
 */
public class HQueryCallEOTest {
    private static final Logger LOG = LogManager.getLogger(HQueryCallEOTest.class);

    @Test
    public void readWhereWithNull()  {
        
        final EO adapter = TestDbProvider
                .executeAdapterWithActionBuilderFromJSON("data/H2MemBasicTestReadAll.json");
        AssertEO.assertLogEmpty(adapter);
        Assert.assertEquals(7, adapter.size());
        Assert.assertEquals("testString1", adapter.get("/0/testString"));
        //AssertEO.compare(adapter);
    }

    @Test
    public void readWhereWithEmpty()  {
        
        final EO adapter = TestDbProvider
                .executeAdapterWithActionBuilderFromJSON("data/H2MemBasicTestReadEmptyOr.json");
        AssertEO.assertLogEmpty(adapter);
        Assert.assertEquals(7, adapter.size());
        Assert.assertEquals("testString1", adapter.get("/0/testString"));
    }

    @Test
    public void readWhereWithId()  {
        
        final EO adapter = TestDbProvider
                .executeAdapterWithActionBuilderFromJSON("data/H2MemBasicTestReadId2.json");
        AssertEO.assertLogEmpty(adapter);
        Assert.assertEquals(1, adapter.size());
        //AssertEO.compare(adapter);

    }

    @Test
    public void readWhereWithTestString3()  {
        
        final EO adapter = TestDbProvider
                .executeAdapterWithActionBuilderFromJSON("data/H2MemBasicTestReadTestString3.json");
        AssertEO.assertLogEmpty(adapter);
        Assert.assertEquals(1, adapter.size());
        //AssertEO.compare(adapter);
    }

    @Test
    public void deleteWhereWithTestString3()  {
        
        final EO adapter = TestDbProvider
                .executeAdapterWithActionBuilderFromJSON("data/H2MemBasicTestDeleteTestString3.json");

        AssertEO.assertLogEmpty(adapter);
        final EO adapterAll = TestDbProvider
                .executeAdapterWithActionBuilderFromJSON("data/H2MemBasicTestReadAll.json");
        Assert.assertEquals(6, adapterAll.size());
        //AssertEO.compare(adapterAll);
        TestHProvider.findHibernateCache(TEO_STATIC.DB_H2_MEM_BASIC).init();
        //HQueryCall call = (HQueryCall) adapterAll.getCalls().find(0).getAction();
        //call.getHConfig().init();

    }
}
