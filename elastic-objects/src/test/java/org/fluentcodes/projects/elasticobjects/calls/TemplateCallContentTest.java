package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 22.03.2017.
 */
public class TemplateCallContentTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(TemplateCallContentTest.class);
    private static final String H1 = "<h1>";
    private static final String H2 = "<h2>";

    @Test
    public void callContentExample() throws Exception {
        final TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, T_CONTENT_EXAMPLE);
        final String result = action.execute(TestObjectProvider.createEO());
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(H2));
        AssertEO.compare(result);
    }


    @Test
    public void callContentExampleWithStaticTemplate() throws Exception {
        final TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, T_CONTENT_EXAMPLE_WITH_STATIC_TEMPLATE);
        final EO adapter = TestObjectProvider.createEO();
        final String result = action.execute(adapter);
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(H1));
        AssertEO.compare(result);
    }

    @Test
    public void contentExampleWithKeepTpl() throws Exception {
        final TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, T_CONTENT_EXAMPLE_WITH_KEEP);
        final EO adapter = TestObjectProvider.createEO();
        final String result = action.execute(adapter);
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(H1));
        AssertEO.compare(result);
    }

    @Test
    public void callContentExampleWithDynamicTemplateKey() throws Exception {
        final TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, T_CONTENT_EXAMPLE_WITH_DYNAMIC_TEMPLATE);
        final EO adapter = TestObjectProvider.createEO();
        final String result = action.execute(adapter);
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(H1));
        AssertEO.compare(result);
    }

    @Test
    public void callContentOrExample() throws Exception {
        TestHelper.printStartMethod();
        final TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, T_CONTENT_OR_EXAMPLE);
        final String result = action.execute(TestObjectProvider.createEO());
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains("<h1>header2</h1>"));
        AssertEO.compare(result);
    }
}
