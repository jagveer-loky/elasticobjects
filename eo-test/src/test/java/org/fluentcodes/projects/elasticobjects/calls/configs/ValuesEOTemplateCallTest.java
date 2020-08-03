package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;

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


}
