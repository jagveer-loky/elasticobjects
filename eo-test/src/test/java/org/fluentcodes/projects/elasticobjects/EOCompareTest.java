package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
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
        
        EO adapter = ProviderRootTestScope.createEo();
        adapter.set(TEO_STATIC.S_STRING, "first");

        EO other = ProviderRootTestScope.createEo();
        other.set(TEO_STATIC.S_STRING,"first");

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        Assert.assertEquals("", builder.toString());
    }

    @Test
    public void simpleScalarNotEquals()  {
        
        EO adapter = ProviderRootTestScope.createEo();
        adapter.set(TEO_STATIC.S_STRING, TEO_STATIC.S_TEST_STRING);

        EO other = ProviderRootTestScope.createEo();
        other.set(TEO_STATIC.S_STRING_OTHER, TEO_STATIC.S_TEST_STRING);

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        //AssertEO.compare(builder.toString());
    }

    @Test
    public void simpleContainerNotEquals()  {
        
        EO adapter = ProviderRootTestScope.createEo();
        adapter.set(TEO_STATIC.S_STRING, TEO_STATIC.S_KEY0);

        EO other = ProviderRootTestScope.createEo();
        other.set(TEO_STATIC.S_STRING, TEO_STATIC.S_KEY1);

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        //AssertEO.compare(builder.toString());
    }

}
