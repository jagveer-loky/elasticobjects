package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.junit.Test;

/**
 * Created 6.8.2020
 */
public class TemplateCallTest {

    @Test
    public void whenCompareConfigurations_thenXpected()  {
        ConfigChecks.findModelAndCompare(TemplateCall.class);
    }

    @Test
    public void givenFoundModel_whenCreateInstance_thenOk()  {
        ConfigChecks.findModelAndCreateInstance(TemplateCall.class);
    }
}
