package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.MapProviderEO;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC._PARENT_KEY;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EOGetTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOGetTest.class);

    @Test
    public void callNotExistingPaths_hasEmptyLog() throws Exception {
        final EO rootEmpty = TestEOProvider.createEmptyMap();
        Assert.assertNull(rootEmpty.get(S_KEY_NOT_EXISTING));
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + rootEmpty.getLog(), rootEmpty.getLog().isEmpty());
    }

    @Test
    public void callNotExistingPathsAtTheEnd() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = MapProviderEO.createWithLongPathAndValueString();
        Assert.assertNull(adapter.get(toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_KEY1)));
    }

    @Test
    public void callNotExistingPathsWithin() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = MapProviderEO.createWithLongPathAndValueString();
        Assert.assertNull(adapter.get(toPath(S_LEVEL1, S_LEVEL2, S_LEVEL4, S_KEY0)));
    }

    @Test
    public void checkParentKey() throws Exception {
        TestHelper.printStartMethod();
        EO child = MapProviderEO.createWithLongPathAndValueString();
        Assert.assertEquals(S_KEY0, child.get(_PARENT_KEY));
        Assert.assertEquals(S_LEVEL2, child.get(toPath(Path.BACK, _PARENT_KEY)));
        Assert.assertEquals(S_LEVEL1, child.get(toPath(Path.BACK, Path.BACK, _PARENT_KEY)));
        Assert.assertEquals(S_LEVEL0, child.get(toPath(Path.BACK, Path.BACK, Path.BACK, _PARENT_KEY)));
    }
}

