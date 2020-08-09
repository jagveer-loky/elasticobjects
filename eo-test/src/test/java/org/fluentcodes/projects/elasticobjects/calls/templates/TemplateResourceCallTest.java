package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.junit.Test;

/**
 * Created 6.8.2020
 */
public class TemplateResourceCallTest {

    @Test
    public void whenCompareConfiguration_thenXpected()  {
        ConfigChecks.findModelAndCompare(TemplateResourceCall.class);
    }

    @Test
    public void givenFoundModel_whenCreateInstance_thenOk()  {
        ConfigChecks.findModelAndCreateInstance(TemplateResourceCall.class);
    }
}
