package org.fluentcodes.projects.elasticobjects.wiki.templates;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SimpleInsertWithPath {
    @Test
    public void test() throws Exception {
        final String result = TestCallsProvider
                .executeTemplateCall("SimpleInsertWithPathAndJson");
        Assert.assertEquals("\n" +
                "key0='test'\n" +
                "Start call: path='level0' --> key0='testOther'  :End call\n" +
                "level0/key0='testOther'", result);
    }
}
