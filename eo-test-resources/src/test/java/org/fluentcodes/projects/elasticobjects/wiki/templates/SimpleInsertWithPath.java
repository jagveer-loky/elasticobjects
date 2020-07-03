package org.fluentcodes.projects.elasticobjects.wiki.templates;

import org.fluentcodes.projects.elasticobjects.test.TestTemplateProvider;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SimpleInsertWithPath {
    @Test
    public void test()  {
        final String result = TestTemplateProvider
                .executeTemplateCall("SimpleInsertWithPathAndJson");
        Assert.assertEquals("\n" +
                "key0='test'\n" +
                "Start call: path='level0' --> key0='testOther'  :End call\n" +
                "level0/key0='testOther'", result);
    }
}
