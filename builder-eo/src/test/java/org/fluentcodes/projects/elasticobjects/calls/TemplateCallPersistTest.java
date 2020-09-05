package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.TestBuilderEOProvider;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;

import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;
import org.junit.Assert;
import org.junit.Test;

public class TemplateCallPersistTest {


    @Test
    public void callTestModelsControlWithSaveTpl()  {
        TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE
                , "TestModelsControlWithSave.tpl");
        EO adapter = TestObjectProvider.createEOFromJson();

        String result = action.execute(adapter, TestBuilderEOProvider.MAP_CONTROL);
        Assert.assertFalse(adapter.isEmpty());
        //AssertEO.compare(result);
    }
}
