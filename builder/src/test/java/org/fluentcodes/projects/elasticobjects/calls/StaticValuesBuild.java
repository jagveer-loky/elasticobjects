package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;
//import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.B_STATIC.T_STATIC_EMPTY_TPL;
import static org.fluentcodes.projects.elasticobjects.B_STATIC.T_STATIC_VALUES_LOOP_TPL;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

public class StaticValuesBuild {
/*
    @Ignore
    @Test
    public void EOStaticJavaTemplate()  {
        EO adapter = TestObjectProvider.createEOFromJson();
        TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, T_STATIC_VALUES_LOOP_TPL);
        Map attributes = new HashMap();
        attributes.put("config", TEMPLATE);
        attributes.put("prefix", "T_");
        action.mapAttributes(attributes);
        String result = action.execute(adapter, attributes);
    }

    @Ignore
    @Test
    public void EOStaticJavaByStepsMain()  {
        EO adapter = TestObjectProvider.createEOFromJson();
        FileCall fileAction = new FileCall(TestObjectProvider.EO_CONFIGS_CACHE, FILE_EO_STATIC_JAVA);
        fileAction.read(adapter);
        TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, T_STATIC_EMPTY_TPL);
        String result = action.execute(adapter);
        fileAction.write(adapter);
    }

    @Ignore
    @Test
    public void EOStaticJavaByStepsTest()  {
        EO adapter = TestObjectProvider.createEOFromJson();
        FileCall fileAction = new FileCall(TestObjectProvider.EO_CONFIGS_CACHE, FILE_EO_STATIC_TEST_JAVA);
        fileAction.read(adapter);
        TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, T_STATIC_EMPTY_TPL);
        Map attributes = new HashMap();
        attributes.put(F_FILTER_SUB_MODULE, TEST);
        String result = action.execute(adapter, attributes);
        fileAction.write(adapter);
    }

 */
}
