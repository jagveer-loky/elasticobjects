package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_CONTAINS_FAILS;

/**
 * Created by Werner on 22.05.2018.
 */
public class TemplateCallExecutorTest extends TestHelper {

    @Test
    public void callExecutorValuesMathSinExample() throws Exception {
        final String result = TestCallsProvider.executeTemplateCall(T_VALUES_MATH_SIN_EXAMPLE);
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains("0.14"));
        ;
    }

    @Test
    public void callExecutorValuesMiscSetExample() throws Exception {
        final String result = TestCallsProvider.executeTemplateCall(T_VALUES_MISC_SET_EXAMPLE);
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains("'value'"));
        AssertEO.compare(result);
    }

    @Test
    public void callExecutorValuesMiscReplaceExample() throws Exception {
        final String result = TestCallsProvider.executeTemplateCall(T_VALUES_MISC_REPLACE_EXAMPLE);
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains("3 + 3"));
    }

    @Test
    public void callExecutorValuesMiscRepeatExample() throws Exception {
        final String result = TestCallsProvider.executeTemplateCall(T_VALUES_MISC_REPEAT_EXAMPLE);
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains("####"));
        AssertEO.compare(result);
    }
}
