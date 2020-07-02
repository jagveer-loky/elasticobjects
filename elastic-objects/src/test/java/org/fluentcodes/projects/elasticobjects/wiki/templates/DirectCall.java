package org.fluentcodes.projects.elasticobjects.wiki.templates;

import org.fluentcodes.projects.elasticobjects.calls.TemplateCall;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Assert;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import org.junit.Test;

public class DirectCall {
    @Test
    public void test() throws Exception {
        final TemplateCall action = new TemplateCall(TestEOProvider.EO_CONFIGS);
        action.setContent("key='$[key]'<call path=\"level0/level1\">level0/level1/key='$[key]'</call>");

        final EO root = TestEOProvider.create();
        root.add("key").set("value");
        root.add("level0/level1/key").set("value with path");

        final String result = action.execute(root);
        Assert.assertEquals(INFO_COMPARE_FAILS, "key='value'level0/level1/key='value with path'",result);
    }
}
