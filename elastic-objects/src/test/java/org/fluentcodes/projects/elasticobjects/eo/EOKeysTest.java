package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import org.junit.Test;

import java.util.List;

/**
 * Created by Werner on 22.03.2017.
 */
public class EOKeysTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOKeysTest.class);
    @Test
    public void keyPath() throws Exception {
        final String path1 = toPath(S_LEVEL0, S_LEVEL1, S_KEY0);
        EO adapter = TestObjectProvider.createEOFromJson();
        adapter.add(S_LEVEL3)
                .set(S_STRING);
        adapter.add(path1)
                .set(S_STRING);
        adapter.add(toPath(S_LEVEL0, S_LEVEL2, S_KEY0))
                .set(S_STRING);
        adapter.add(toPath(S_LEVEL0, S_LEVEL2, S_KEY1))
                .set(S_STRING);

        List<String> keys = adapter.filterPaths(S_LEVEL3);
        Assert.assertEquals(1, keys.size());
        Assert.assertEquals(S_LEVEL3, keys.get(0));

        keys = adapter.filterPaths(path1);
        Assert.assertEquals(1, keys.size());
        Assert.assertEquals(path1, keys.get(0));

        keys = adapter.filterPaths(Path.MATCHER);
        Assert.assertEquals(2, keys.size());
        Assert.assertEquals(S_LEVEL3, keys.get(0));
        Assert.assertEquals(S_LEVEL0, keys.get(1));

        keys = adapter.filterPaths(Path.MATCHER_ALL);
        Assert.assertEquals(4, keys.size());
        Assert.assertEquals(S_LEVEL3, keys.get(0));
        Assert.assertEquals(path1, keys.get(1));

        final String pathMatcher = toPath(S_LEVEL0,Path.MATCHER, S_KEY0);
        keys = adapter.filterPaths(pathMatcher);
        Assert.assertEquals(2, keys.size());
        Assert.assertEquals(path1, keys.get(0));

        keys = adapter.filterPaths(toPath(S_LEVEL0,Path.MATCHER));
        Assert.assertEquals(2, keys.size());
        Assert.assertEquals(toPath(S_LEVEL0, S_LEVEL1), keys.get(0));

        keys = adapter.filterPaths(toPath(S_LEVEL0,Path.MATCHER, S_LEVEL4));
        Assert.assertEquals(0, keys.size());

        keys = adapter.filterPaths(".*0");
        Assert.assertEquals(1, keys.size());
        Assert.assertEquals(S_LEVEL0, keys.get(0));

        keys = adapter.filterPaths("^" + S_LEVEL3);
        Assert.assertEquals(1, keys.size());
        Assert.assertEquals(S_LEVEL3, keys.get(0));

    }

}
