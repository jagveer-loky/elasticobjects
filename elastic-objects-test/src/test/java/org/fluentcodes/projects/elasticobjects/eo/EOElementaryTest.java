package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_NAME;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EOElementaryTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOElementaryTest.class);

    @Test
    public void isContainer()  {
        TestHelper.printStartMethod();
        EO adapter = TestEOProvider.createEOFromJson(Map.class);
        adapter.add(S_LEVEL0)
                .set(S_STRING);
        Assert.assertEquals(S_STRING, adapter.get(S_LEVEL0));
        Assert.assertTrue(adapter.isContainer());
        Assert.assertFalse(adapter.getChild(S_LEVEL0).isContainer());
    }

    /**
     * Test different createChildForMap value
     *
     * @
     */
    @Test
    public void getChild()  {
        TestHelper.printStartMethod();
        EO adapter = TestEOProvider.createEOFromJson(Map.class);
        Assert.assertEquals(Map.class, adapter.getChild(null).getModelClass());
        Assert.assertEquals(Map.class, adapter.getChild(S_EMPTY).getModelClass());
        Assert.assertEquals(Map.class, adapter.getRoot().getModelClass());
        adapter.add(S_LEVEL0)
                .set(S_STRING);

        Assert.assertEquals(S_STRING, adapter.get(S_LEVEL0));
        Assert.assertEquals(Map.class, ((EOContainer) adapter.getChild(S_LEVEL0)).getParentAdapter().getModelClass());
        Assert.assertEquals(Map.class, ((EOContainer) adapter.getChild(S_LEVEL0)).getRoot().getModelClass());

    }

    /**
     * Error handling getting non existent path.
     *
     * @
     */
    @Test
    public void errorNonExistingPath()  {
        TestHelper.printStartMethod();
        EO adapter = TestEOProvider.createEOFromJson(Map.class);
        Object notExisting = adapter.get(toPath(S_LEVEL0, SAMPLE_KEY_UNKNOW));
        Assert.assertNull(notExisting);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS, adapter.getLog().isEmpty());
    }

    /**
     * Error handling getting non existent path.
     *
     * @
     */
    @Test
    public void errorGettingNotExistingObjectPath()  {
        TestHelper.printStartMethod();
        SubTest subTest = new SubTest();
        subTest.setTestString(S_STRING);
        subTest.setName(S_STRING);
        final String path = toPath(F_SUB_TEST, F_NAME);
        EO adapter = TestEOProvider.createEOFromJson(Map.class);
        adapter
                .add(F_SUB_TEST)
                .set(subTest);
        String subTestName = (String) adapter.get(path);
        Assert.assertEquals(S_STRING, subTestName);
        adapter.get(toPath(F_SUB_TEST, SAMPLE_KEY_UNKNOW));
    }
}

