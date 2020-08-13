package org.fluentcodes.projects.elasticobjects.paths;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.junit.Assert;
import org.junit.Test;

public class PathElementTest {
    @Test
    public void testWithoutModel() {
        PathElement pathElement = new PathElement("test");
        Assert.assertEquals("test", pathElement.getKey());
        Assert.assertFalse(pathElement.hasModels());
    }

    @Test
    public void testWithEmptyModel() {
        PathElement pathElement = new PathElement("()test");
        Assert.assertEquals("test", pathElement.getKey());
        Assert.assertFalse(pathElement.hasModels());
    }

    @Test
    public void testWithOneModel() {
        PathElement pathElement = new PathElement("(nonsense)test");
        Assert.assertEquals("test", pathElement.getKey());
        Assert.assertTrue(pathElement.hasModelArray());
        Assert.assertEquals(1, pathElement.getModelsArray().length);
    }

    @Test
    public void testWithTwoModel() {
        final String pathElementAsString = "(List,BasicTest)test";
        PathElement pathElement = new PathElement(pathElementAsString);
        Assert.assertEquals("test", pathElement.getKey());
        Assert.assertTrue(pathElement.hasModelArray());
        Assert.assertEquals(2, pathElement.getModelsArray().length);
        Assertions.assertThat(pathElement.toString()).isEqualTo(pathElementAsString);
    }
}
