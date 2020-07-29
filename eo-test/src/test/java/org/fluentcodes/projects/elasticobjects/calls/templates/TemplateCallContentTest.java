package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;

import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_CONTAINS_FAILS;

/**
 * Created by Werner on 22.03.2017.
 */
public class TemplateCallContentTest {
    private static final Logger LOG = LogManager.getLogger(TemplateCallContentTest.class);
    private static final String H1 = "<h1>";
    private static final String H2 = "<h2>";

    @Test
    public void callContentExample()  {
        final TemplateCallResource action = new TemplateCallResource().setConfigKey(T_CONTENT_EXAMPLE);
        final String result = action.execute(TestProviderRootTest.createEo());
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(H2));
        //AssertEO.compare(result);
    }


    @Test
    public void callContentExampleWithStaticTemplate()  {
        final TemplateCallResource action = new TemplateCallResource().setConfigKey( T_CONTENT_EXAMPLE_WITH_STATIC_TEMPLATE);
        final EO adapter = TestProviderRootTest.createEo();
        final String result = action.execute(adapter);
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(H1));
        //AssertEO.compare(result);
    }

    @Test
    public void contentExampleWithKeepTpl()  {
        final TemplateCallResource action = new TemplateCallResource().setConfigKey( T_CONTENT_EXAMPLE_WITH_KEEP);
        final EO adapter = TestProviderRootTest.createEo();
        final String result = action.execute(adapter);
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(H1));
        //AssertEO.compare(result);
    }

    @Test
    public void callContentExampleWithDynamicTemplateKey()  {
        final TemplateCallResource action = new TemplateCallResource().setConfigKey(T_CONTENT_EXAMPLE_WITH_DYNAMIC_TEMPLATE);
        final EO adapter = TestProviderRootTest.createEo();
        final String result = action.execute(adapter);
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(H1));
        //AssertEO.compare(result);
    }

    @Test
    public void callContentOrExample()  {
        
        final TemplateCallResource action = new TemplateCallResource().setConfigKey( T_CONTENT_OR_EXAMPLE);
        final String result = action.execute(TestProviderRootTest.createEo());
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains("<h1>header2</h1>"));
        //AssertEO.compare(result);
    }
}
