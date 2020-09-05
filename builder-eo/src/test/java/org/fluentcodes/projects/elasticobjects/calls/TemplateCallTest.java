package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.TestBuilderEOProvider;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;

import org.fluentcodes.projects.elasticobjects.testitemprovider.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;
import org.junit.Assert;
import org.junit.Test;

public class TemplateCallTest {

    @Test
    public void callTestModelsControlTpl()  {
        TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, "TestModelsControl.tpl");
        EO adapter = TestObjectProvider.createEOFromJson();

        String result = action.execute(adapter, TestBuilderEOProvider.MAP_CONTROL);
        Assert.assertFalse(adapter.isEmpty());
        //AssertEO.compare(result);
    }


    @Test
    public void callModels()  {
        String result = TestCallsProvider.executeTemplateAction(TestBuilderEOProvider.MAP_CONTROL, "JavaControl.tpl");
        //AssertEO.compare(result);
    }
}
