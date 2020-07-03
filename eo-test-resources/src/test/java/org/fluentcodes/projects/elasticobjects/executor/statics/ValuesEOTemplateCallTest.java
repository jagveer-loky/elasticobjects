package org.fluentcodes.projects.elasticobjects.executor.statics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.TemplateCall;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.Executor;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_COMPARE_FAILS;

/**
 * @author Werner Diwischek
 * @since 15.09.2019.
 */
public class ValuesEOTemplateCallTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ValuesEOTemplateCallTest.class);
    public final static String RESULT = "result";
    private final static String PATH_RESULT = F_MAP_PATH + "=\"" + RESULT + "\"";


    @Test
    public void configurationListModelConfig_listsAllModelConfigs()  {
        final TemplateCall action = DIRECT_CALL_CONFIGURATION_LIST(null);

        EO root = TestEOProvider.createEmptyMap();
        final String result = action.execute(root);

        Assert.assertEquals(INFO_COMPARE_FAILS, "Load configurationList: ", result);
        Assert.assertEquals(M_LINKED_HASH_SET, root.get("result/0"));
    }

    private static final TemplateCall DIRECT_CALL_CONFIGURAION(final String configType, final String selectConfig)  {
            final TemplateCall call = new TemplateCall(TestEOProvider.EO_CONFIGS);
            final String template = "Load configurationList: " + TemplateCall.createCallNoContent (
                    Executor.EXECUTE, ValuesEO.EXECUTE_GET_CONFIGURATION_KEYS,
                    ValuesEO.CONFIG_TYPE, configType,
                    ValuesEO.CONFIG_KEY, selectConfig,
                    F_MAP_PATH, RESULT
            );
            call.setContent(template);
            return call;
    }

    private static final TemplateCall DIRECT_CALL_CONFIGURATION_LIST(final String configType)  {
        final TemplateCall call = new TemplateCall(TestEOProvider.EO_CONFIGS);
        final String template = "Load configurationList: " + TemplateCall.createCallNoContent (
                Executor.EXECUTE, ValuesEO.EXECUTE_GET_CONFIGURATION,
                ValuesEO.CONFIG_TYPE, configType,
                F_MAP_PATH, RESULT
        );
        call.setContent(template);
        return call;
    }

    private static final TemplateCall VALUE_CALL_CONFIGURATION_LIST(final String configType)  {
        final TemplateCall call = new TemplateCall(TestEOProvider.EO_CONFIGS);
        final String template = "Load configurationList: " + TemplateCall.createCallNoContent (
                Executor.EXECUTE, ValuesEO.EXECUTE_VALUE_CALL_GET_CONFIGURATION_LIST,
                ValuesEO.CONFIG_TYPE, configType,
                F_MAP_PATH, RESULT
        );
        call.setContent(template);
        return call;
    }

    private static final TemplateCall VALUE_CALL_CONFIGURATION(final String configType, final String selectConfig)  {
        final TemplateCall call = new TemplateCall(TestEOProvider.EO_CONFIGS);
        final String template = "Load configurationList: " + TemplateCall.createCallNoContent (
                Executor.EXECUTE, ValuesEO.EXECUTE_VALUE_CALL_GET_CONFIGURATION,
                ValuesEO.CONFIG_TYPE, configType,
                ValuesEO.CONFIG_KEY, selectConfig,
                F_MAP_PATH, RESULT
        );
        call.setContent(template);
        return call;
    }

    @Test
    public void configurationList_withFieldConfig_listsAllFieldConfigs()  {
        TemplateCall call = DIRECT_CALL_CONFIGURATION_LIST(M_FIELD_CONFIG);

        EO root = TestEOProvider.createEmptyMap();
        final String result = call.execute(root);

        Assert.assertEquals(INFO_COMPARE_FAILS, "Load configurationList: ", result);
        Assert.assertEquals(F_PATH_PATTERN_AS_STRING, root.get("result/0"));
    }

    @Test
    public void configurationFieldConfig_hasAllFieldConfigs()  {
        final TemplateCall action = DIRECT_CALL_CONFIGURAION(M_FIELD_CONFIG, F_PATH_PATTERN_AS_STRING);

        EO root = TestEOProvider.createEmptyMap();
        final String result = action.execute(root);

        Assert.assertEquals(INFO_COMPARE_FAILS, "Load configurationList: ", result);
        Assert.assertEquals(F_PATH_PATTERN_AS_STRING, root.get(RESULT + "/0"));
    }

    @Test
    public void callConfiguration_WithFieldConfig_WithPathPatternAsString_hasConfig()  {
        final TemplateCall action = VALUE_CALL_CONFIGURATION(M_FIELD_CONFIG, F_PATH_PATTERN_AS_STRING);

        EO root = TestEOProvider.createEmptyMap();
        final String result = action.execute(root);

        Assert.assertEquals(INFO_COMPARE_FAILS, "Load configurationList: ", result);
        Assert.assertEquals(F_PATH_PATTERN_AS_STRING, root.get(RESULT + "/0"));
    }

    @Test
    public void callConfigurationListWithFieldConfig_hasAllFieldConfigs()  {
        final TemplateCall action = VALUE_CALL_CONFIGURATION_LIST(M_FIELD_CONFIG);

        EO root = TestEOProvider.createEmptyMap();
        final String result = action.execute(root);

        Assert.assertEquals(INFO_COMPARE_FAILS, "Load configurationList: ", result);
        Assert.assertEquals(F_PATH_PATTERN_AS_STRING, root.get(RESULT + "/0"));
    }

    /**
     * One can use a different attribute name - here typeKey instead of configType.
     */
    @Test
    public void configurationList_withFieldConfig_withTypeKeyAsName_hasAllFieldConfigs()  {
        final TemplateCall call = new TemplateCall(TestEOProvider.EO_CONFIGS);
        final String template = "Load configurationList: <call execute=\"ValuesEO.getConfigurationKeys(eo,typeKey)\" typeKey=\"FieldConfig\" mapPath=\"target\" />";
        call.setContent(template);

        EO root = TestEOProvider.createEmptyMap();
        final String result = call.execute(root);

        Assert.assertEquals(INFO_COMPARE_FAILS, "Load configurationList: ", result);
        Assert.assertEquals(F_PATH_PATTERN_AS_STRING, root.get("target/0" ));
    }


}
