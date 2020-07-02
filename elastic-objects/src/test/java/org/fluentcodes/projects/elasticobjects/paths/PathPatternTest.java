package org.fluentcodes.projects.elasticobjects.paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;


public class PathPatternTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(PathPatternTest.class);


    public PathPattern createPathPatternExample() throws Exception {
        TestHelper.printStartMethod();
        PathPattern pattern = new PathPattern();
        String path1 = toPath(S_LEVEL0, S_LEVEL1);
        pattern.addPath(path1);
        Assert.assertEquals(path1, pattern.getPath(0).directory(false));
        String path2 = toPath(S_LEVEL0, S_LEVEL2);
        pattern.addPath(path2);
        Assert.assertEquals(path2, pattern.getPath(1).directory(false));
        pattern.addPath(Path.MATCHER_ALL);
        Assert.assertEquals(Path.MATCHER_ALL, pattern.getPath(2).directory(false));
        return pattern;
    }

    @Test
    public void compareSerialized() throws Exception {
        TestHelper.printStartMethod();
        final PathPattern pattern = new PathPattern();
        pattern.addPath(toPath(S_LEVEL0, S_LEVEL1));
        pattern.addPath(toPath(S_LEVEL0, S_LEVEL1));
        AssertEO.compare(TestEOProvider.EO_CONFIGS, pattern);
    }

    @Test
    public void getPathListTest() throws Exception {
        TestHelper.printStartMethod();
        final PathPattern pathPattern = createPathPatternExample();
        final PathPattern nextPath = pathPattern.getPathList(S_LEVEL0);
        Assert.assertEquals(new Integer(3), nextPath.size());
        Assert.assertEquals(S_LEVEL1, nextPath.getPath(0).directory(false));
        Assert.assertEquals(S_LEVEL2, nextPath.getPath(1).directory(false));
        Assert.assertEquals(Path.MATCHER_ALL, nextPath.getPath(2).directory(false));
    }

    @Test
    public void getFirstPathTest() throws Exception {
        TestHelper.printStartMethod();
        final PathPattern pattern = createPathPatternExample();
        final List<String> nextPath = pattern.getFirstPath();
        Assert.assertEquals(2, nextPath.size());
        Assert.assertEquals(S_LEVEL0, nextPath.get(0));
        Assert.assertEquals(Path.MATCHER_ALL, nextPath.get(1));
    }

    @Test
    public void getFirstPathFilteredTest() throws Exception {
        TestHelper.printStartMethod();
        final PathPattern pattern = createPathPatternExample();
        List<String> nextPath = pattern.filter(Arrays.asList(S_LEVEL0, S_PATH1, S_PATH2));
        Assert.assertEquals(3, nextPath.size());
        Assert.assertEquals(S_LEVEL0, nextPath.get(0));
    }

    @Test
    public void toStringTest() {
        TestHelper.printStartMethod();
        final PathPattern pattern = new PathPattern(new String[]{S_LEVEL0});
        Assert.assertEquals(S_LEVEL0, pattern.getSerialized(false));
        pattern.addPath(S_LEVEL1);
        Assert.assertEquals(join(CON_COMMA, S_LEVEL0, S_LEVEL1), pattern.getSerialized(false));
        PathPattern patternFromString = new PathPattern(pattern.getSerialized());
        Assert.assertEquals(join(CON_COMMA, S_LEVEL0, S_LEVEL1), patternFromString.getSerialized(false));
    }


}