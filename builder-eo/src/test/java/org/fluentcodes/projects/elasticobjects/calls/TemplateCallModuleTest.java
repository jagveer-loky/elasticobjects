package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;

import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.T_MODULE_TEST_TPL;

public class TemplateCallModuleTest {

    @Test
    public void staticValuesLoopTpl()  {
        EO adapter = TestObjectProvider.createEOFromJson();
        TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE,
                T_MODULE_TEST_TPL);
        Map attributes = new HashMap();
        attributes.put("config", EO_STATIC.MODEL);
        attributes.put("prefix", "M_");
        String result = action.execute(adapter, attributes);
        //AssertEO.compare(result);
    }

}
