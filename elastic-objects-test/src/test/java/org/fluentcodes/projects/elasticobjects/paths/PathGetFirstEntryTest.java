package org.fluentcodes.projects.elasticobjects.paths;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.Path;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class PathGetFirstEntryTest {
    private static final Logger LOG = LogManager.getLogger(PathGetFirstEntryTest.class);

    @Test
    public void givenPathWith3PathElements_whenFirstEntry_thenFirstPathElement() {
        Path path = new Path(S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Assert.assertEquals(S_LEVEL0, path.getFirstEntry());
    }

    @Test
    public void givenPathWith1PathElements_whenFirstEntry_thenFirstPathElement() {
        Path path = new Path(S_LEVEL0);
        Assert.assertEquals(S_LEVEL0, path.getFirstEntry());
    }

    @Test
    public void givenPathWith1EmptyPathElements_whenFirstEntry_thenFirstPathElement() {
        Path path = new Path(S_EMPTY);
        Assert.assertNull(path.getFirstEntry());
    }
}