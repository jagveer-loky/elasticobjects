package org.fluentcodes.projects.elasticobjects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.TestHProvider;
import org.fluentcodes.projects.elasticobjects.calls.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 29.10.2017.
 */
public class HQueryCallBuilderTest {
    private static final Logger LOG = LogManager.getLogger(HQueryCallBuilderTest.class);

    protected static CallExecutor createActionBuilder()  {
        return TestHProvider.createHibernateModelActionRead(TEO_STATIC.DB_H2_MEM_BASIC);
    }

    @Test
    public void readWhereWithNull()  {
        
        final CallExecutor callExecutor = createActionBuilder();
        EO adapter = TestObjectProvider.createEOFromJson();
        callExecutor.execute(adapter);

        Assert.assertEquals(7, adapter.size());
        Assert.assertEquals("testString1", adapter.get("/0/testString"));
        Assert.assertEquals("testString2", adapter.get("/1/testString"));
    }
}
