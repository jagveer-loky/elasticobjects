package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_NAME;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EOElementaryTest {
    private static final Logger LOG = LogManager.getLogger(EOElementaryTest.class);

    @Test
    public void isContainer()  {
        
        EO adapter = TestProviderRootTest.createEo(Map.class);
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
        
        EO adapter = TestProviderRootTest.createEo(Map.class);
        Assert.assertEquals(Map.class, adapter.getEo(null).getModelClass());
        Assert.assertEquals(Map.class, adapter.getEo(S_EMPTY).getModelClass());
        Assert.assertEquals(Map.class, adapter.getRoot().getModelClass());
        adapter.set( S_STRING, S_LEVEL0);

        Assert.assertEquals(S_STRING, adapter.get(S_LEVEL0));
        Assert.assertEquals(Map.class, ((EoChild) adapter.getEo(S_LEVEL0)).getParentAdapter().getModelClass());
        Assert.assertEquals(Map.class, ((EoChild) adapter.getEo(S_LEVEL0)).getRoot().getModelClass());

    }

    /**
     * Error handling getting non existent path.
     *
     * @
     */
    @Test
    public void errorNonExistingPath()  {
        
        EO eo = TestProviderRootTest.createEo(Map.class);
        Object notExisting = eo.get(S_LEVEL0, SAMPLE_KEY_UNKNOW);
        Assert.assertNull(notExisting);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS, eo.getLog().isEmpty());
    }
}

