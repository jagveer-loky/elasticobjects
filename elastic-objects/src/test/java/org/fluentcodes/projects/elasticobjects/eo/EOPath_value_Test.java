package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.fluentcodes.projects.elasticobjects.test.EOTest;
import org.fluentcodes.projects.elasticobjects.test.MapProviderEO;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */
public class EOPath_value_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOPath_value_Test.class);

    @Test
    public void givenMapStringLevel0_setMapStringLevel1_ok() throws Exception {
        final EO eoMap = DevObjectProvider.createEOMapStringLevel0();
        final String path = toPath(S_LEVEL0, S_LEVEL1, F_TEST_STRING);
        EOTest.setEO_ok(eoMap, path, S_STRING_OTHER);
    }

    @Test
    public void givenMapStringLevel1_setMapStringLevel0_ok() throws Exception {
        final EO eoMap = DevObjectProvider.createEOMapStringLevel1();
        final String path = toPath(S_LEVEL0, F_TEST_STRING);
        EOTest.setEO_ok(eoMap, path, S_STRING_OTHER);
    }

    @Test
    public void givenMapStringLevel1_setMapStringLevel0WithOverWrite_ok() throws Exception {
        final EO eoMap = DevObjectProvider.createEOMapStringLevel1();
        EOTest.setEO_ok(eoMap, S_LEVEL0, S_STRING_OTHER);
    }

    @Test
    public void givenMapStringLevel0_mapMapStringLevel1_ok() throws Exception {
        final EO eoMap = DevObjectProvider.createEOMapStringLevel0();
        final String path = toPath(S_LEVEL0, S_LEVEL1, F_TEST_STRING);
        EOTest.mapEO_ok(eoMap, path, S_STRING_OTHER);
    }

    @Test
    public void givenMapStringLevel1_mapMapStringLevel0_ok() throws Exception {
        final EO eoMap = DevObjectProvider.createEOMapStringLevel1();
        final String path = toPath(S_LEVEL0, F_TEST_STRING);
        EOTest.mapEO_ok(eoMap, path, S_STRING_OTHER);
    }

    @Test
    public void givenMapStringLevel1_mapMapStringLevel0WithOverwrite_fails() throws Exception {
        final EO eoMap = DevObjectProvider.createEOMapStringLevel1();
        EOTest.mapEO_fails(eoMap, S_LEVEL0, S_STRING_OTHER);
    }


    @Test
    /**
     * Map will overwrite scalar values on the path.
     */
    public void mapWithPathOverwrite_willWriteIntoTheLog() throws Exception {
        final EO eoMap = MapProviderEO.create();
        final String path1 = toPath(S_LEVEL0, S_LEVEL1);
        final String path2 = toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2);
        eoMap.add(path1)
                .map(S_STRING);
        eoMap.add(path2)
                .map(S_STRING_OTHER);
        Assert.assertEquals(S_STRING, eoMap.get(path1));
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, eoMap.getLog().isEmpty());
    }

    /**
     * Tests a scalar setting with some path specification including using existing childAdapters.
     *
     * @throws Exception
     */
    @Test
    public void mapWithPathsOverlapping() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = TestObjectProvider.createEOFromJson();
        adapter.add(toPath(S_LEVEL1, S_LEVEL2, S_LEVEL3, S_TEST_STRING))
                .map(S_STRING);
        Assert.assertEquals(S_STRING, adapter.get(toPath(S_LEVEL1, S_LEVEL2, S_LEVEL3, S_TEST_STRING)));
        adapter.add(toPath(S_LEVEL1, S_LEVEL2, S_TEST_STRING))
                .map(S_STRING);
        Assert.assertEquals(S_STRING, adapter.get(toPath(S_LEVEL1, S_LEVEL2, S_TEST_STRING)));
    }

    @Test
    public void mapWithPathLong() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = TestObjectProvider.createEOFromJson();
        adapter.add(toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2))
                .map(S_STRING);
        Assert.assertEquals(S_STRING, adapter.get(toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2)));
    }

    /**
     * Tests a scalar setting with some path with two entries.
     *
     * @throws Exception
     */
    @Test
    public void setWithPath2() throws Exception {
        TestHelper.printStartMethod();
        final String path = toPath(S_LEVEL0, S_LEVEL1);
        final EO childAdapter = TestObjectProvider.createEOBuilder()
                .setPath(path)
                .set(S_STRING);
        EO root = childAdapter.getChild(Path.DELIMITER);
        Assert.assertEquals(path, childAdapter.getPath().directory(false));
        Assert.assertEquals(S_STRING, root.get(path));
    }

    @Test
    public void setWithPath3() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = TestObjectProvider.createEOFromJson();
        adapter.add(toPath(S_LEVEL1, S_LEVEL2, S_LEVEL3))
                .set(S_STRING);
        Assert.assertEquals(S_STRING, adapter.get(toPath(S_LEVEL1, S_LEVEL2, S_LEVEL3)));
    }


    @Test
    public void setWithPath4() throws Exception {
        TestHelper.printStartMethod();
        final String path = toPath(S_LEVEL1, S_LEVEL2, S_LEVEL3);
        final EO childAdapter = TestObjectProvider.createEOBuilder()
                .setPath(path)
                .set(S_STRING);

        EO root = childAdapter.getRoot();
        Assert.assertEquals(path, childAdapter.getPath().directory(false));

        Assert.assertEquals(S_STRING, root.get(path));
    }

    @Test
    public void setWithTwoPathsDistinct() throws Exception {
        TestHelper.printStartMethod();
        final String path1 = toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_LEVEL3);
        final String path2 = toPath(S_LEVEL4, S_LEVEL5, S_LEVEL6, S_LEVEL7);
        final EO adapter = TestObjectProvider.createEOFromJson();
        final EO childAdapter = adapter
                .add(path1)
                .set(S_STRING);
        final EO childAdapter2 = adapter
                .add(path2)
                .set(S_STRING_OTHER);

        Assert.assertEquals(path1, childAdapter.getPath().directory(false));

        Assert.assertEquals(path2, childAdapter2.getPath().directory(false));

        Assert.assertEquals(S_STRING, adapter.get(path1));
        Assert.assertEquals(S_STRING_OTHER, adapter.get(path2));
    }

    @Test
    public void setWithPathWithCommonPart() throws Exception {
        TestHelper.printStartMethod();
        final String path1 = toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_LEVEL3);
        final String path2 = toPath(S_LEVEL0, S_LEVEL4, S_LEVEL5, S_LEVEL6, S_LEVEL7);
        EO adapter = TestObjectProvider.createEOFromJson();
        EO childAdapter = adapter
                .add(path1)
                .set(S_STRING);
        EO childAdapter2 = adapter.add(path2)
                .set(S_STRING_OTHER);

        Assert.assertEquals(path1, childAdapter.getPath().directory(false));

        Assert.assertEquals(path2, childAdapter2.getPath().directory(false));

        Assert.assertEquals(S_STRING, adapter.get(path1));
        Assert.assertEquals(S_STRING_OTHER, adapter.get(path2));
    }

    @Test
    public void setWithPathWithCommon2Parts() throws Exception {
        TestHelper.printStartMethod();
        final String path1 = toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_LEVEL3);
        final String path2 = toPath(S_LEVEL0, S_LEVEL1, S_LEVEL4, S_LEVEL5, S_LEVEL6, S_LEVEL7);
        EO childAdapter = TestObjectProvider.createEOBuilder()
                .setPath(path1)
                .set(S_STRING);
        EO adapter = childAdapter.getRoot();
        EO childAdapter2 = adapter.add(path2)
                .set(S_STRING_OTHER);

        Assert.assertEquals(path1, childAdapter.getPath().directory(false));

        Assert.assertEquals(path2, childAdapter2.getPath().directory(false));

        Assert.assertEquals(S_STRING, adapter.get(path1));
        Assert.assertEquals(S_STRING_OTHER, adapter.get(path2));
    }


}

