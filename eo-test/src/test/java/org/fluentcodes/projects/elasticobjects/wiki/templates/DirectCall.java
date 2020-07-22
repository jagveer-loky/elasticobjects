package org.fluentcodes.projects.elasticobjects.wiki.templates;

import org.fluentcodes.projects.elasticobjects.calls.TemplateCall;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Assert;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import org.junit.Test;

public class DirectCall {
    @Test
    public void test()  {
        final TemplateCall action = new TemplateCall();
        /*action.setContent("key='$[key]'<call path=\"level0/level1\">level0/level1/key='$[key]'</call>");

        final EO root = TestProviderRootTest.createEo();
        root.setPathValue("key","value");
        root.setPathValue("level0/level1/key","value with path");

        final String result = action.execute(root);
        Assert.assertEquals(INFO_COMPARE_FAILS, "key='value'level0/level1/key='value with path'",result);*/
    }
}
