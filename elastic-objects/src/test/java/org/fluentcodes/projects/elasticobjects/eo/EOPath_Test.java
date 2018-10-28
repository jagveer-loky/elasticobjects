package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;


public class EOPath_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOPath_Test.class);

    @Test
    public void withPath2() throws Exception {
        final EO adapter = TestObjectProvider.createEOFromJson();
        final EO childAdapter = adapter
                .add(toPath(S_PATH1))
                .build();
        Assert.assertEquals(S_PATH1, childAdapter.getPath().directory(false));
    }

    @Test
    public void withPath4() throws Exception {
        final EO adapter = TestObjectProvider.createEOFromJson();
        final EO child = adapter
                .add(S_PATH4)
                .build();

        Assert.assertEquals(S_PATH4, child.getPath().directory(false));
        Assert.assertEquals(Path.DELIMITER + S_PATH4, child.getPathAsString());

    }

    @Test
    public void withPath3() throws Exception {
        final String path2= toPath(S_LEVEL4, S_LEVEL5, S_LEVEL6, S_LEVEL7);
        final EO adapter = TestObjectProvider.createEOFromJson();
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
    public void withPath3AndWithCommonPart() throws Exception {
        final String path2 = toPath(S_LEVEL0, S_LEVEL4, S_LEVEL5, S_LEVEL6, S_LEVEL7);
        final EO adapter = TestObjectProvider.createEOFromJson();
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
    public void withPath3WithCommon2Parts() throws Exception {
        final String path2 = toPath(S_LEVEL0, S_LEVEL1, S_LEVEL4, S_LEVEL5, S_LEVEL6, S_LEVEL7);
        final EO adapter = TestObjectProvider.createEOFromJson();
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