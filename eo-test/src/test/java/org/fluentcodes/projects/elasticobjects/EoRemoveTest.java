package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

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
    public void testObjectbject()  {
        EoChild child = (EoChild) TestProviderRootTest.createEo(new BasicTest())
                .set("Test", F_TEST_STRING);
        EO root = child.getRoot();
        Assert.assertEquals(1, ((EoChild) root).valuesize());
        Assert.assertEquals(1, ((EoChild) root).adaptersize());

        root.remove(F_TEST_STRING);
        Assert.assertEquals(0, ((EoChild) root).valuesize());
        Assert.assertEquals(0, ((EoChild) root).adaptersize());
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
    public void testMap()  {
        
        EO child = TestProviderRootTest.createEo().set(S_STRING, S_TEST_STRING);
        EO root = child.getRoot();
        Assert.assertEquals(1, ((EoChild) root).valuesize());
        Assert.assertEquals(1, ((EoChild) root).adaptersize());
        Assert.assertEquals(S_STRING, root.get(S_TEST_STRING));
        Map map = (Map) root.get();
        root.remove(S_TEST_STRING);
        Assert.assertEquals(0, ((EoChild) root).valuesize());
        Assert.assertEquals(0, ((EoChild) root).adaptersize());
        try {
            root.remove(S_TEST_STRING);
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
    public void testList()  {
        
        EO child = TestProviderRootTest.createEo(new ArrayList<>())
                .set(S_STRING, S0);
        // remove value entry first
        EO root = child.getRoot();
        Assert.assertEquals(1, ((EoChild) root).adaptersize());
        Assert.assertEquals(1, ((EoChild) root).valuesize());

        root.remove(S0);
        Assert.assertEquals(0, ((EoChild) root).adaptersize());
        Assert.assertEquals(0, ((EoChild) root).valuesize());
        try {
            root.remove(S0);
            Assert.fail("Exception expected removing non existing child '" + S0 + "'");
        }
        catch (EoException e) {
            LOG.info("Expected Exception: " + e.getMessage());
        }
    }
}
