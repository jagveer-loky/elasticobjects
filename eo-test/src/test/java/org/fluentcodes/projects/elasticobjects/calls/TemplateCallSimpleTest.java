package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.test.*;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 22.03.2017.
 */
public class TemplateCallSimpleTest {
    private static final Logger LOG = LogManager.getLogger(TemplateCallSimpleTest.class);

    @Test
    public void executeDirectContent()  {

        /*final TemplateCall action = new TemplateCall();

        final String template = "key='$[key]'<call path=\"level0/level1\">level0/level1/key='$[key]'</call>";
        action.setContent(template);

        EO root = TestProviderRootTest.createEo();
        root.setPathValue("key","value");
        root.setPathValue("level0/level1/key","value with path");
        final String result = action.execute(root);

        Assert.assertEquals(INFO_COMPARE_FAILS, "key='value'level0/level1/key='value with path'", result);
        //AssertEO.compare(result);*/
    }
/*
    @Test
    public void executeWithPath()  {
        final TemplateCall action = new TemplateCall(T_SIMPLE_INSERT_WITH_PATH);
        final String result = action.execute(MapProviderEO.createSimpleInsertWithPath());
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(S_STRING));
        //AssertEO.compare(result);
    }

    @Test
    public void executeWithPathAndEmbeddedJson()  {
        final String value = TestTemplateProvider.executeTemplateCall(T_SIMPLE_INSERT_WITH_PATH_AND_EMBEDDED_JSON);
        Assert.assertEquals("\nTest testValue Insert: testValue2 testValue2", value);
    }

    @Test
    public void executeWithPathAndJson()  {
        final String result = TestTemplateProvider.executeTemplateCall(T_SIMPLE_INSERT_WITH_PATH_AND_JSON);
        //AssertEO.compare(result);
    }

    @Test
    public void executeWithPathAndJsonAndStore()  {
        final String result = TestTemplateProvider.executeTemplateCall(T_SIMPLE_INSERT_WITH_PATH_AND_JSON_STORE);
        Assert.assertTrue(result.contains(S_STRING_OTHER));
        //AssertEO.compare(result);
    }

 */

}
