package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall.KEEP_CALL;

/**
 * Created 21.9.2020
 */
public class TemplateDirResourceCallTest {

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(TemplateDirResourceCall.class);
    }

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(TemplateDirResourceCall.class);
    }
}
