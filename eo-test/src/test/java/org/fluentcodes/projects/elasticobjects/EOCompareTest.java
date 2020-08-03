package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;

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
        
        EO adapter = ProviderRootTest.createEo();
        adapter.set(TEO_STATIC.S_STRING, "first");

        EO other = ProviderRootTest.createEo();
        other.set(TEO_STATIC.S_STRING,"first");

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        Assert.assertEquals("", builder.toString());
    }

    @Test
    public void simpleScalarNotEquals()  {
        
        EO adapter = ProviderRootTest.createEo();
        adapter.set(TEO_STATIC.S_STRING, TEO_STATIC.S_TEST_STRING);

        EO other = ProviderRootTest.createEo();
        other.set(TEO_STATIC.S_STRING_OTHER, TEO_STATIC.S_TEST_STRING);

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        //AssertEO.compare(builder.toString());
    }

    @Test
    public void simpleContainerNotEquals()  {
        
        EO adapter = ProviderRootTest.createEo();
        adapter.set(TEO_STATIC.S_STRING, TEO_STATIC.S_KEY0);

        EO other = ProviderRootTest.createEo();
        other.set(TEO_STATIC.S_STRING, TEO_STATIC.S_KEY1);

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        //AssertEO.compare(builder.toString());
    }

}
