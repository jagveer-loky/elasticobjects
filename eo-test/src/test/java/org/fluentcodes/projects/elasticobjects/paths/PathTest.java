package org.fluentcodes.projects.elasticobjects.paths;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class PathTest {
    private static final Logger LOG = LogManager.getLogger(PathTest.class);

    @Test
    public void constructorStringEmpty() {
        
        Assert.assertEquals(Path.DELIMITER, new Path(S_EMPTY).directory());
        Assert.assertEquals(Path.DELIMITER, new Path(PathElement.SAME).directory());
        Assert.assertEquals(Path.DELIMITER, new Path(Path.DELIMITER).directory());
        Assert.assertEquals(Path.DELIMITER, new Path("///").directory());
        Assert.assertEquals(Path.DELIMITER, new Path("/././").directory());
        Assert.assertEquals(Path.DELIMITER, new Path("/ /./\t/").directory());
    }

    @Test
    public void constructorString_3Entries() {
        
        String toCompare = Path.ofs(S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Assert.assertEquals(toCompare, new Path(toCompare).directory(false));
        String other = Path.ofs(PathElement.SAME, S_EMPTY, EO_STATIC.CON_SPACE, S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Assert.assertEquals(toCompare, new Path(other).directory(false));
        other = Path.ofs(S_LEVEL0, PathElement.SAME, S_LEVEL1, S_EMPTY, S_LEVEL2);
        Assert.assertEquals(toCompare, new Path(other).directory(false));
    }

    @Test
    public void constructorString_BackAtTheBeginning() {
        
        String toCompare = Path.ofs(PathElement.BACK, S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Assert.assertEquals(toCompare, new Path(toCompare).directory(false));
        String other = Path.ofs(PathElement.SAME, PathElement.BACK, S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Assert.assertEquals(toCompare, new Path(other).directory(false));
    }

    @Test
    public void constructorString_SeveralBacksAtTheBeginning() {
        
        String toCompare = Path.ofs(PathElement.BACK, PathElement.BACK, PathElement.BACK, S_LEVEL0);
        Assert.assertEquals(toCompare, new Path(toCompare).directory(false));
    }

    @Test
    public void constructorString_BackAtTheMiddle() {
        
        String toCompare = Path.ofs(S_LEVEL0, S_LEVEL2);
        Assert.assertEquals(toCompare, new Path(toCompare).directory(false));
        String other = Path.ofs(S_LEVEL0, S_LEVEL1, PathElement.BACK, S_LEVEL2);
        Assert.assertEquals(toCompare, new Path(other).directory(false));
    }

    @Test
    public void constructorString_BackAtTheEnd() {
        
        String toCompare = Path.ofs(S_LEVEL0, S_LEVEL1);
        Assert.assertEquals(toCompare, new Path(toCompare).directory(false));
        String other = Path.ofs(S_LEVEL0, S_LEVEL1, S_LEVEL2, PathElement.BACK);
        Assert.assertEquals(toCompare, new Path(other).directory(false));
    }


    @Test
    public void constructorPath() {
        
        Path path = new Path(Path.ofs(S_LEVEL0, S_LEVEL1, S_LEVEL2));
        Path otherPath = new Path(path);
        Assert.assertEquals(Path.ofs(S_LEVEL0, S_LEVEL1, S_LEVEL2), otherPath.directory(false));
        path = new Path(S_LEVEL0);
        otherPath = new Path(path);
        Assert.assertEquals(Path.ofs(S_LEVEL0), otherPath.directory(false));
        path = new Path(S_EMPTY);
        otherPath = new Path(path);
        Assert.assertEquals(S_EMPTY, otherPath.directory(false));
    }

    @Test
    public void childPath() {
        
        Path path = new Path(Path.ofs(S_LEVEL0, S_LEVEL1, S_LEVEL2));
        Path childPath = path.getChildPath();
        Assert.assertEquals(Path.ofs(S_LEVEL1, S_LEVEL2), childPath.directory(false));
        // checks path with one element
        path = new Path(S_LEVEL0);
        childPath = path.getChildPath();
        Assert.assertEquals(Path.DELIMITER, childPath.directory());
    }

    @Test
    public void firstEntry() {
        
        Path path = new Path(Path.ofs(S_LEVEL0, S_LEVEL1, S_LEVEL2));
        Assert.assertEquals(S_LEVEL0, path.getFirstEntry());
        // checks path with one element
        path = new Path(S_LEVEL0);
        Assert.assertEquals(S_LEVEL0, path.getFirstEntry());
        path = new Path(S_EMPTY);
        Assert.assertNull(path.getFirstEntry());
    }

    @Test
    public void hasMatcher() {
        
        Path path = new Path(Path.ofs(S_LEVEL0, PathElement.MATCHER, S_LEVEL1));
        Assert.assertTrue(path.hasMatcher());
        path = new Path(Path.ofs(S_LEVEL0, S_LEVEL1));
        Assert.assertFalse(path.hasMatcher());
        path = new Path(S_EMPTY);
        Assert.assertFalse(path.hasMatcher());
    }

    @Test
    public void hasPlaceHolder() {
        
        Path path = new Path(Path.ofs(S_LEVEL0, "$[test]"));
        Assert.assertTrue(path.hasPlaceHolder());
        path = new Path(Path.ofs(S_LEVEL0));
        Assert.assertFalse(path.hasPlaceHolder());
        path = new Path(S_EMPTY);
        Assert.assertFalse(path.hasPlaceHolder());
    }

    @Test
    public void callToString() {
        
        Path path = new Path(Path.ofs(S_LEVEL0));
        Assert.assertEquals(Path.DELIMITER + Path.ofs(S_LEVEL0), path.toString());
        path = new Path(Path.ofs(S_LEVEL0, S_LEVEL1));
        Assert.assertEquals(Path.DELIMITER + Path.ofs(S_LEVEL0, S_LEVEL1), path.toString());
        path = new Path(S_EMPTY);
        Assert.assertEquals(Path.DELIMITER, path.toString());
    }
}