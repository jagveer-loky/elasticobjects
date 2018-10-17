package org.fluentcodes.projects.elasticobjects.paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathPatternFilterTest extends TestHelper {
    private static final String TEST3 = "test3";
    public static final List<String> values = Arrays.asList(S_STRING, S_STRING_OTHER, TEST3);
    private static final Logger LOG = LogManager.getLogger(PathPatternFilterTest.class);

    @Test
    public void filterListTest() {
        TestHelper.printStartMethod();

        List<String> filter = Arrays.asList(S_STRING, S_STRING_OTHER);
        PathPattern pathList = new PathPattern(filter);
        List<String> result = pathList.filter(values);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(S_STRING, result.get(0));
        Assert.assertEquals(S_STRING_OTHER, result.get(1));

        result = new PathPattern("test*").filter(values);

        Assert.assertEquals(3, result.size());
        Assert.assertEquals(S_STRING, result.get(0));
        Assert.assertEquals(S_STRING_OTHER, result.get(1));
        Assert.assertEquals(TEST3, result.get(2));

        result = new PathPattern("*3").filter(values);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(TEST3, result.get(0));

        filter = Arrays.asList(S_STRING_OTHER, S_STRING);
        result = new PathPattern(filter).filter(values);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(S_STRING_OTHER, result.get(0));
        Assert.assertEquals(S_STRING, result.get(1));

        result = new PathPattern().filter(values);
        Assert.assertEquals(0, result.size());

        result = new PathPattern().filter(new ArrayList<String>());
        Assert.assertEquals(0, result.size());
        //TODO
        //result =new PathPattern().set(null);
        //Assert.assertEquals(0,result.size());


    }

    @Test
    public void filterWithEmptyStringConstructor() {
        PathPattern pathPattern = new PathPattern(S_EMPTY);
        List<String> result = pathPattern.filter(values);
        Assert.assertEquals(0, result.size());
    }
}