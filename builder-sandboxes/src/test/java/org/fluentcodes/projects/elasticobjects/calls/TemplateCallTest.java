package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Werner on 22.03.2017.
 */
public class TemplateCallTest {
    private static final Logger LOG = LogManager.getLogger(TemplateCallContentTest.class);

    @Test
    public void callGitReadControl()  {
        Map attributes = new HashMap();
        attributes.put("set", "addList");
        
        TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, "gitread.control");
        EO adapter = TestObjectProvider.createEOFromJson();
        action.execute(adapter, attributes);
        Assert.assertEquals("gitElementH3Option", adapter.get("addList/20.0/template"));
        Assert.assertEquals("GitElementH2Code", adapter.get("rebase/0.0/template"));
    }

    @Test
    public void callHTest()  {
        TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, "HTest");
        EO adapter = TestObjectProvider.createEOFromJson();
        String result = action.execute(adapter, null);
        Assert.assertTrue("Result does not contain # header1! " + result, result.contains("# header1"));
    }

    @Test
    public void callHOptionTest()  {
        TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, "HOptionTest");
        EO adapter = TestObjectProvider.createEOFromJson();
        String result = action.execute(adapter, null);
        Assert.assertTrue("Result does not contain --header1! " + result, result.contains("--header1"));
    }

    @Test
    public void callHOptionsListTest()  {
        TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, "HOptionsListTest");
        EO adapter = TestObjectProvider.createEOFromJson();
        String result = action.execute(adapter, null);
        Assert.assertTrue("Result does not contain --header1! " + result, result.contains("--header1"));
    }
}
