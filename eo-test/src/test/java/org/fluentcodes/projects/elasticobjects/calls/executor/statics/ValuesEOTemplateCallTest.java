package org.fluentcodes.projects.elasticobjects.calls.executor.statics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_COMPARE_FAILS;

/**
 * @author Werner Diwischek
 * @since 15.09.2019.
 */
public class ValuesEOTemplateCallTest {
    private static final Logger LOG = LogManager.getLogger(ValuesEOTemplateCallTest.class);
    public final static String RESULT = "result";

    @Test
    public void configurationListModelConfig_listsAllModelConfigs()  {
        final TemplateCall action = new TemplateCall();

        EO root = TestProviderRootTest.createEo();
        final String result = action.execute(root);

        Assert.assertEquals(INFO_COMPARE_FAILS, "Load configurationList: ", result);
        Assert.assertEquals(M_LINKED_HASH_SET, root.get("result/0"));
    }



    /*@Test
    public void callConfiguration_WithFieldConfig_WithPathPatternAsString_hasConfig()  {
        final TemplateCall action = VALUE_CALL_CONFIGURATION(M_FIELD_CONFIG, F_PATH_PATTERN_AS_STRING);

        EO root = TestEOProvider.create();
        final String result = action.execute(root);

        Assert.assertEquals(INFO_COMPARE_FAILS, "Load configurationList: ", result);
        Assert.assertEquals(F_PATH_PATTERN_AS_STRING, root.get(RESULT + "/0"));
    }

    @Test
    public void callConfigurationListWithFieldConfig_hasAllFieldConfigs()  {
        final TemplateCall action = VALUE_CALL_CONFIGURATION_LIST(M_FIELD_CONFIG);

        EO root = TestEOProvider.create();
        final String result = action.execute(root);

        Assert.assertEquals(INFO_COMPARE_FAILS, "Load configurationList: ", result);
        Assert.assertEquals(F_PATH_PATTERN_AS_STRING, root.get(RESULT + "/0"));
    }*/

    /**
     * One can use a different attribute name - here typeKey instead of configType.
     */
    @Test
    public void configurationList_withFieldConfig_withTypeKeyAsName_hasAllFieldConfigs()  {
        final TemplateCall call = new TemplateCall();
        final String template = "Load configurationList: <call execute=\"ValuesEO.getConfigurationKeys(eo,typeKey)\" typeKey=\"FieldConfig\" mapPath=\"target\" />";
        call.setContent(template);

        EO root = TestProviderRootTest.createEo();
        final String result = call.execute(root);

        Assert.assertEquals(INFO_COMPARE_FAILS, "Load configurationList: ", result);
        Assert.assertEquals(F_PATH_PATTERN_AS_STRING, root.get("target/0" ));
    }


}
