package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.junit.Assert;

import java.util.*;

/**
 * Created 2.6.2018
 */
public class TestBuilderEOProvider {
    /*
    public static final EO STATIC = initModelAdapter();
    public static final Map MAP_CONTROL = createAttributes();

    private static final EO initModelAdapter() {
        EO adapter = null;
        try {
            adapter = TestCallsProvider.executeTemplateActionForAdapter(new HashMap(), "StaticFromXlsx.tpl");
            adapter.add("").set(new LinkedHashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adapter;
    }

    public static Map createFilterElasticObjects() {
        Map attributes = new HashMap();
        attributes.put("module", "elastic-objects");
        attributes.put("subModule", "main");
        attributes.put("name", "ConstantsElasticObjectsMain");
        return attributes;
    }

    private static Map createAttributes() {
        Map attributes = new HashMap();
        attributes.put("module", "builder-eo");
        attributes.put("subModule", "test");
        attributes.put("filterModels", ".*");
        return attributes;
    }

    public static void addModelBuilderTest(EO adapter)  {
        adapter.add("module").set("builder-eo");
        adapter.add("subModule").set("test");
        adapter.add("packageGroup").set("models");
        adapter.add("eoParams/shapeType").set("BEAN");
        adapter.add("modelKey").set("BuilderTest");
        //adapter.add("fieldKeys/0").set("testValue");
        List<String> fields = Arrays.asList(new String[]{"id", "untypedList", "untypedMap", "testInt", "testString", "testLong", "testDate", "testBoolean", "testFloat", "testDouble", "testObject"});
        adapter.add("fieldKeys").set(fields);
        adapter.add("packagePath").set("org.fluentcodes.projects.elasticobjects.models");
        adapter.add("description").set("Test Description.");
        adapter.add("author").set("Albert Zweistein");
    }

    public static void testTemplate(String templateKey)  {
        addModelBuilderTest(STATIC);
        final TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, templateKey);
        String result = action.execute(STATIC);
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        //AssertEO.compare(result);
    }

 */
}
