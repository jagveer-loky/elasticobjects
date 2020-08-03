package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EOElementaryTest {
    private static final Logger LOG = LogManager.getLogger(EOElementaryTest.class);

    @Test
    public void isContainer()  {
        
        EO adapter = ProviderRootTest.createEo(Map.class);
        adapter.set(S_STRING, S_LEVEL0);
        Assert.assertEquals(S_STRING, adapter.get(S_LEVEL0));
        Assert.assertTrue(adapter.isContainer());
        Assert.assertFalse(adapter.getEo(S_LEVEL0).isContainer());
    }

    /**
     * Test different createChildForMap value
     *
     * @
     */
    @Test
    public void getChild()  {
        
        EO adapter = ProviderRootTest.createEo(Map.class);
        Assert.assertEquals(Map.class, adapter.getEo((String) null).getModelClass());
        Assert.assertEquals(Map.class, adapter.getEo(S_EMPTY).getModelClass());
        Assert.assertEquals(Map.class, adapter.getRoot().getModelClass());
        adapter.set( S_STRING, S_LEVEL0);

        Assert.assertEquals(S_STRING, adapter.get(S_LEVEL0));
        Assert.assertEquals(Map.class, ((EoChild) adapter.getEo(S_LEVEL0)).getEoParent().getModelClass());
        Assert.assertEquals(Map.class, ((EoChild) adapter.getEo(S_LEVEL0)).getRoot().getModelClass());

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

