package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;


public class EOPath_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOPath_Test.class);

    @Test
    public void withPath2()  {
        final EO adapter = TestEOProvider.createEmptyMap();
        final EO childAdapter = adapter
                .add(toPath(S_PATH1))
                .build();
        Assert.assertEquals(S_PATH1, childAdapter.getPath().directory(false));
    }

    @Test
    public void withPath4()  {
        final EO adapter = TestEOProvider.createEmptyMap();
        final EO child = adapter
                .add(S_PATH4)
                .build();

        Assert.assertEquals(S_PATH4, child.getPath().directory(false));
        Assert.assertEquals(Path.DELIMITER + S_PATH4, child.getPathAsString());

    }

    @Test
    public void withPath3()  {
        final String path2 = toPath(S_LEVEL4, S_LEVEL5, S_LEVEL6, S_LEVEL7);
        final EO adapter = TestEOProvider.createEmptyMap();
        final EO childAdapter = adapter
                .add(S_PATH3)
                .build();
        final EO childAdapter2 = adapter
                .add(path2)
                .build();

        Assert.assertEquals(S_PATH3, childAdapter.getPath().directory(false));
        Assert.assertEquals(path2, childAdapter2.getPath().directory(false));
    }

    @Test
    public void withPath3AndWithCommonPart()  {
        final String path2 = toPath(S_LEVEL0, S_LEVEL4, S_LEVEL5, S_LEVEL6, S_LEVEL7);
        final EO adapter = TestEOProvider.createEmptyMap();
        final EO childAdapter = adapter
                .add(S_PATH3)
                .build();

        final EO childAdapter2 = adapter
                .add(path2)
                .build();

        Assert.assertEquals(S_PATH3, childAdapter.getPath().directory(false));
        Assert.assertEquals(path2, childAdapter2.getPath().directory(false));
    }

    @Test
    public void withPath3WithCommon2Parts()  {
        final String path2 = toPath(S_LEVEL0, S_LEVEL1, S_LEVEL4, S_LEVEL5, S_LEVEL6, S_LEVEL7);
        final EO adapter = TestEOProvider.createEmptyMap();
        final EO childAdapter = adapter
                .add(S_PATH3)
                .build();
        final EO childAdapter2 = adapter
                .add(path2)
                .build();

        Assert.assertEquals(S_PATH3, childAdapter.getPath().directory(false));
        Assert.assertEquals(path2, childAdapter2.getPath().directory(false));
    }
}