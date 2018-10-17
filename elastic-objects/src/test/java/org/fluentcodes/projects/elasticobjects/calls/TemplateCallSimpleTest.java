package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.test.MapProviderEO;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 22.03.2017.
 */
public class TemplateCallSimpleTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(TemplateCallSimpleTest.class);

    @Test
    public void executeWithPath() throws Exception {
        final TemplateCall action = TestCallsProvider.createTemplateCall(T_SIMPLE_INSERT_WITH_PATH);
        final String result = action.execute(MapProviderEO.createSimpleInsertWithPath());
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(S_STRING));
        AssertEO.compare(result);
    }

    @Test
    public void executeWithPathAndEmbeddedJson() throws Exception {
        final String value  = TestCallsProvider.executeTemplateCall( T_SIMPLE_INSERT_WITH_PATH_AND_EMBEDDED_JSON);
        Assert.assertEquals("\nTest testValue Insert: testValue2 testValue2", value);
    }

    @Test
    public void executeWithPathAndJson() throws Exception {
        final String result = TestCallsProvider.executeTemplateCall( T_SIMPLE_INSERT_WITH_PATH_AND_JSON);
        AssertEO.compare(result);
    }

    @Test
    public void executeWithPathAndJsonAndStore() throws Exception {
        final String result = TestCallsProvider.executeTemplateCall( T_SIMPLE_INSERT_WITH_PATH_AND_JSON_STORE);
        Assert.assertTrue(result.contains(S_STRING_OTHER));
        AssertEO.compare(result);
    }

}
