package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDev;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EOElementaryTest {
    private static final Logger LOG = LogManager.getLogger(EOElementaryTest.class);

    @Test
    public void isContainer()  {
        EO eo = ProviderRootTest.createEoWithClasses(Map.class);
        eo.set(S_STRING, S_LEVEL0);
        Assert.assertEquals(S_STRING, eo.get(S_LEVEL0));
        Assert.assertTrue(eo.isContainer());
        Assert.assertFalse(eo.getEo(S_LEVEL0).isContainer());
    }

    /**
     * Test different createChildForMap value
     *
     * @
     */
    @Test
    public void givenEoEmpty_thenModelIsSet() {
        EO eo = ProviderRootDev.createEoWithClasses(Map.class);
        Assert.assertEquals(Map.class, eo.getEo((String) null).getModelClass());
        Assert.assertEquals(Map.class, eo.getEo(S_EMPTY).getModelClass());
        Assert.assertEquals(Map.class, eo.getRoot().getModelClass());
    }

    @Test
    public void givenEoEWithKeyValueString_thenModelIsSet() {
        EO eo = ProviderRootDev.createEoWithClasses(Map.class);
        eo.set( S_STRING, S_LEVEL0);
        Assert.assertEquals(S_STRING, eo.get(S_LEVEL0));
        Assert.assertEquals(Map.class, ((EoChild) eo.getEo(S_LEVEL0)).getEoParent().getModelClass());
        Assert.assertEquals(Map.class, ((EoChild) eo.getEo(S_LEVEL0)).getRoot().getModelClass());

    }

    /**
     * Error handling getting non existent path.
     *
     * @
     */
    @Test
    public void errorNonExistingPath()  {
        
        EO eo = ProviderRootTest.createEo(Map.class);
        Object notExisting = eo.get(S_LEVEL0, SAMPLE_KEY_UNKNOW);
        Assert.assertNull(notExisting);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS, eo.getLog().isEmpty());
    }
}

