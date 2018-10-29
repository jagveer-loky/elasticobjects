package org.fluentcodes.projects.elasticobjects.paths;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class PathTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(PathTest.class);

    @Test
    public void constructorStringEmpty() {
        TestHelper.printStartMethod();
        Assert.assertEquals(Path.DELIMITER, new Path(S_EMPTY).directory());
        Assert.assertEquals(Path.DELIMITER, new Path(Path.SAME).directory());
        Assert.assertEquals(Path.DELIMITER, new Path(Path.DELIMITER).directory());
        Assert.assertEquals(Path.DELIMITER, new Path("///").directory());
        Assert.assertEquals(Path.DELIMITER, new Path("/././").directory());
        Assert.assertEquals(Path.DELIMITER, new Path("/ /./\t/").directory());
    }

    @Test
    public void constructorString_3Entries() {
        TestHelper.printStartMethod();
        String toCompare = toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Assert.assertEquals(toCompare, new Path(toCompare).directory(false));
        String other = toPath(Path.SAME, S_EMPTY, EO_STATIC.CON_SPACE, S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Assert.assertEquals(toCompare, new Path(other).directory(false));
        other = toPath(S_LEVEL0, Path.SAME, S_LEVEL1, S_EMPTY, S_LEVEL2);
        Assert.assertEquals(toCompare, new Path(other).directory(false));
    }

    @Test
    public void constructorString_BackAtTheBeginning() {
        TestHelper.printStartMethod();
        String toCompare = toPath(Path.BACK, S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Assert.assertEquals(toCompare, new Path(toCompare).directory(false));
        String other = toPath(Path.SAME, Path.BACK, S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Assert.assertEquals(toCompare, new Path(other).directory(false));
    }

    @Test
    public void constructorString_SeveralBacksAtTheBeginning() {
        TestHelper.printStartMethod();
        String toCompare = toPath(Path.BACK, Path.BACK, Path.BACK, S_LEVEL0);
        Assert.assertEquals(toCompare, new Path(toCompare).directory(false));
    }

    @Test
    public void constructorString_BackAtTheMiddle() {
        TestHelper.printStartMethod();
        String toCompare = toPath(S_LEVEL0, S_LEVEL2);
        Assert.assertEquals(toCompare, new Path(toCompare).directory(false));
        String other = toPath(S_LEVEL0, S_LEVEL1, Path.BACK, S_LEVEL2);
        Assert.assertEquals(toCompare, new Path(other).directory(false));
    }

    @Test
    public void constructorString_BackAtTheEnd() {
        TestHelper.printStartMethod();
        String toCompare = toPath(S_LEVEL0, S_LEVEL1);
        Assert.assertEquals(toCompare, new Path(toCompare).directory(false));
        String other = toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2, Path.BACK);
        Assert.assertEquals(toCompare, new Path(other).directory(false));
    }


    @Test
    public void constructorPath() {
        TestHelper.printStartMethod();
        Path path = new Path(toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2));
        Path otherPath = new Path(path);
        Assert.assertEquals(toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2), otherPath.directory(false));
        path = new Path(S_LEVEL0);
        otherPath = new Path(path);
        Assert.assertEquals(toPath(S_LEVEL0), otherPath.directory(false));
        path = new Path(S_EMPTY);
        otherPath = new Path(path);
        Assert.assertEquals(S_EMPTY, otherPath.directory(false));
    }

    @Test
    public void childPath() {
        TestHelper.printStartMethod();
        Path path = new Path(toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2));
        Path childPath = path.getChildPath();
        Assert.assertEquals(toPath(S_LEVEL1, S_LEVEL2), childPath.directory(false));
        // checks path with one element
        path = new Path(S_LEVEL0);
        childPath = path.getChildPath();
        Assert.assertEquals(Path.DELIMITER, childPath.directory());
    }

    @Test
    public void firstEntry() {
        TestHelper.printStartMethod();
        Path path = new Path(toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2));
        Assert.assertEquals(S_LEVEL0, path.getFirstEntry());
        // checks path with one element
        path = new Path(S_LEVEL0);
        Assert.assertEquals(S_LEVEL0, path.getFirstEntry());
        path = new Path(S_EMPTY);
        Assert.assertNull(path.getFirstEntry());
    }

    @Test
    public void hasMatcher() {
        TestHelper.printStartMethod();
        Path path = new Path(toPath(S_LEVEL0, Path.MATCHER, S_LEVEL1));
        Assert.assertTrue(path.hasMatcher());
        path = new Path(toPath(S_LEVEL0, S_LEVEL1));
        Assert.assertFalse(path.hasMatcher());
        path = new Path(S_EMPTY);
        Assert.assertFalse(path.hasMatcher());
    }

    @Test
    public void hasPlaceHolder() {
        TestHelper.printStartMethod();
        Path path = new Path(toPath(S_LEVEL0, "$[test]"));
        Assert.assertTrue(path.hasPlaceHolder());
        path = new Path(toPath(S_LEVEL0));
        Assert.assertFalse(path.hasPlaceHolder());
        path = new Path(S_EMPTY);
        Assert.assertFalse(path.hasPlaceHolder());
    }

    @Test
    public void callToString() {
        TestHelper.printStartMethod();
        Path path = new Path(toPath(S_LEVEL0));
        Assert.assertEquals(Path.DELIMITER + toPath(S_LEVEL0), path.toString());
        path = new Path(toPath(S_LEVEL0, S_LEVEL1));
        Assert.assertEquals(Path.DELIMITER + toPath(S_LEVEL0, S_LEVEL1), path.toString());
        path = new Path(S_EMPTY);
        Assert.assertEquals(Path.DELIMITER, path.toString());
    }
}