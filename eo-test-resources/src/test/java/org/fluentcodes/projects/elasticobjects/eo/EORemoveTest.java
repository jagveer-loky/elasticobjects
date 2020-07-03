package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 23.05.2016.
 */
public class EORemoveTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EORemoveTest.class);

    private EOBuilder createBuilder()  {
        return TestEOProvider.createEOBuilder()
                .setLogLevel(LogLevel.WARN);
    }

    /**
     * Test for the remove method, which is depending on the object
     *
     * @
     */
    @Test
    public void object()  {
        EOContainer child = (EOContainer) TestEOProvider.createEOBuilder()
                .setModels(BasicTest.class)
                .setPath(F_TEST_STRING)
                .set("Test");
        EO root = child.getRoot();
        Assert.assertEquals(1, ((EOContainer) root).valuesize());
        Assert.assertEquals(1, ((EOContainer) root).adaptersize());

        Assert.assertTrue(root.remove(F_TEST_STRING));
        Assert.assertEquals(0, ((EOContainer) root).valuesize());
        Assert.assertEquals(0, ((EOContainer) root).adaptersize());

        Assert.assertFalse(root.remove(F_TEST_STRING));

    }

    /**
     * Test for the remove method, which is depending on the object
     *
     * @
     */
    @Test
    public void map()  {
        TestHelper.printStartMethod();
        EO child = createBuilder()
                .setModels(Map.class)
                .setPath(S_TEST_STRING)
                .set(S_STRING);
        EO root = child.getRoot();
        Assert.assertEquals(1, ((EOContainer) root).valuesize());
        Assert.assertEquals(1, ((EOContainer) root).adaptersize());
        Assert.assertEquals(S_STRING, root.get(S_TEST_STRING));
        Map map = (Map) root.get();
        Assert.assertTrue(root.remove(S_TEST_STRING));
        Assert.assertEquals(0, ((EOContainer) root).valuesize());
        Assert.assertEquals(0, ((EOContainer) root).adaptersize());
        //TODO Assert.assertEquals(null, map.find(0));

        Assert.assertFalse(root.remove(S_TEST_STRING));
    }


    /**
     * Test for the remove method, which is depending on the object
     *
     * @
     */
    @Test
    public void list()  {
        TestHelper.printStartMethod();
        EO child = createBuilder()
                .setModels(List.class)
                .setPath(S0)
                .set(S_STRING);
        // remove value entry first
        EO root = child.getRoot();
        Assert.assertEquals(1, ((EOContainer) root).adaptersize());
        Assert.assertEquals(1, ((EOContainer) root).valuesize());

        Assert.assertTrue(root.remove(S0));
        Assert.assertEquals(0, ((EOContainer) root).adaptersize());
        Assert.assertEquals(0, ((EOContainer) root).valuesize());

        Assert.assertFalse(root.remove(S0));

    }
}
