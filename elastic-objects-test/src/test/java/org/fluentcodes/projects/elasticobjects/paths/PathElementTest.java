package org.fluentcodes.projects.elasticobjects.paths;

import org.junit.Assert;
import org.junit.Test;
import org.fluentcodes.projects.elasticobjects.paths.PathElement;

public class PathElementTest {
    @Test
    public void testWithoutModel() {
        PathElement pathElement = new PathElement("test");
        Assert.assertEquals("test", pathElement.getPathElement());
        Assert.assertFalse(pathElement.hasModels());
    }

    @Test
    public void testWithEmptyModel() {
        PathElement pathElement = new PathElement("()test");
        Assert.assertEquals("test", pathElement.getPathElement());
        Assert.assertFalse(pathElement.hasModels());
    }

    @Test
    public void testWithOneModel() {
        PathElement pathElement = new PathElement("(nonsense)test");
        Assert.assertEquals("test", pathElement.getPathElement());
        Assert.assertTrue(pathElement.hasModels());
        Assert.assertEquals(1, pathElement.getModels().size());
    }

    @Test
    public void testWithTwoModel() {
        PathElement pathElement = new PathElement("(List,BasicTest)test");
        Assert.assertEquals("test", pathElement.getPathElement());
        Assert.assertTrue(pathElement.hasModels());
        Assert.assertEquals(2, pathElement.getModels().size());
    }
}
