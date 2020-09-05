package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.TestBuilderEOProvider;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;

import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;
import org.junit.Assert;
import org.junit.Test;

public class TemplateCallContantsTest {

    @Test
    public void executeConstantsCreateTpl()  {
        final TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, "ConstantsCreate.tpl");
        EO adapter = TestBuilderEOProvider.STATIC;
        String result = action.execute(adapter, TestBuilderEOProvider.createFilterElasticObjects());
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        //AssertEO.compare(result);
    }

    @Test
    public void executeConstantsLoopTpl()  {
        final TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, "ConstantsLoop.tpl");
        EO adapter = TestBuilderEOProvider.STATIC;
        EO fields = adapter.getChild("fields");
        String result = action.execute(fields, TestBuilderEOProvider.createFilterElasticObjects());
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        //AssertEO.compare(result);
    }

    @Test
    public void executeEO()  {
        EO adapter = TestObjectProvider.createEOFromJson();
        final FileCall action = new FileCall(TestObjectProvider.EO_CONFIGS_CACHE, "EO_STATIC.java");
        String result = action.read(adapter);
        //final TemplateCall = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, "");
    }
}
