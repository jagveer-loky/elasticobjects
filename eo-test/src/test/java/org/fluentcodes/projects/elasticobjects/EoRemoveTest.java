package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 23.05.2016.
 */
public class EoRemoveTest {
    private static final Logger LOG = LogManager.getLogger(EoRemoveTest.class);

    /**
     * Test for the remove method, which is depending on the object
     *
     * @
     */
    @Test
    public void givenBT_thenRemoved()  {
        EO child = ProviderRootTest.createEo(new BasicTest())
                .set(S_STRING, F_TEST_STRING);
        EO root = child.getRoot();
        Assert.assertEquals(1, (root).size());
        Assert.assertEquals(3, (root).sizeEo());

        root.remove(F_TEST_STRING);
        Assert.assertEquals(0, (root).size());
        Assert.assertEquals(2, (root).sizeEo());
        try {
            root.remove(F_TEST_STRING);
            Assert.fail("Exception expected removing non existing child '" + S_TEST_STRING + "'");
        }
        catch (EoException e) {
            LOG.info("Expected Exception: " + e.getMessage());
        }
    }

    /**
     * Test for the remove method, which is depending on the object
     *
     * @
     */
    @Test
    public void givenMap_thenRemoved()  {
        EO child = ProviderRootTest.createEo().set(S_STRING, S_TEST_STRING);
        EO root = child.getRoot();
        Assert.assertEquals(1, root.size());
        Assert.assertEquals(1, root.sizeEo());
        Assert.assertEquals(S_STRING, root.get(S_TEST_STRING));
        root.remove(S_TEST_STRING);
        Assert.assertEquals(0, root.size());
        Assert.assertEquals(0, root.sizeEo());
        try {
            root.remove(S_TEST_STRING);
            Assert.fail("Exception expected removing non existing child '" + S_TEST_STRING + "'");
        }
        catch (EoException e) {
            LOG.info("Expected Exception: " + e.getMessage());
        }
    }

    @Test
    public void givenList_thenRemoved()  {
        
        EO child = ProviderRootTest.createEo(new ArrayList<>())
                .set( S_STRING,S0);
        // remove value entry first
        EO root = child.getRoot();
        Assert.assertEquals(3, root.sizeEo());
        Assert.assertEquals(1, root.size());

        root.remove(S0);
        Assert.assertEquals(2, root.sizeEo());
        Assert.assertEquals(0, root.size());
        try {
            root.remove(S0);
            Assert.fail("Exception expected removing non existing child '" + S0 + "'");
        }
        catch (EoException e) {
            LOG.info("Expected Exception: " + e.getMessage());
        }
    }
}
