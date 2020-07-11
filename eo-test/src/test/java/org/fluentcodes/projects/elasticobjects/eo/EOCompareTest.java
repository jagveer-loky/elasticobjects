package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;

import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

/**
 * New compare method tests
 * Created by Werner on 6.6.2018.
 */
public class EOCompareTest {
    private static final Logger LOG = LogManager.getLogger(EOCompareTest.class);

    @Test
    public void simpleEquals()  {
        
        EO adapter = TestProviderRootTest.createEo();
        adapter.setPathValue("first", TEO_STATIC.S_STRING);

        EO other = TestProviderRootTest.createEo();
        other.setPathValue("first" ,TEO_STATIC.S_STRING);

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        Assert.assertEquals("", builder.toString());
    }

    @Test
    public void simpleScalarNotEquals()  {
        
        EO adapter = TestProviderRootTest.createEo();
        adapter.setPathValue(TEO_STATIC.S_TEST_STRING
                ,TEO_STATIC.S_STRING);

        EO other = TestProviderRootTest.createEo();
        other.setPathValue(TEO_STATIC.S_TEST_STRING
                ,TEO_STATIC.S_STRING_OTHER);

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        //AssertEO.compare(builder.toString());
    }

    @Test
    public void simpleContainerNotEquals()  {
        
        EO adapter = TestProviderRootTest.createEo();
        adapter.setPathValue(TEO_STATIC.S_KEY0,TEO_STATIC.S_STRING);

        EO other = TestProviderRootTest.createEo();
        other.setPathValue(TEO_STATIC.S_KEY1,TEO_STATIC.S_STRING);

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        //AssertEO.compare(builder.toString());
    }

}
