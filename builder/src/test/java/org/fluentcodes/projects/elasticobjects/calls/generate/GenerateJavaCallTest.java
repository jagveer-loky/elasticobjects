package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.R_ANONYM;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.R_GUEST;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL2;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;
import static org.fluentcodes.projects.elasticobjects.calls.generate.GenerateCall.BUILD_PATH;
import static org.fluentcodes.projects.elasticobjects.calls.generate.GenerateJsonConfigCall.CONFIG_TYPE;
import static org.fluentcodes.projects.elasticobjects.domain.Base.NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.Config.MODULE;
import static org.fluentcodes.projects.elasticobjects.models.Config.MODULE_SCOPE;

/**
 * @author Werner Diwischek
 * @since 9.10.2020.
 */
public class GenerateJavaCallTest {

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(GenerateJavaCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(GenerateJavaCall.class);
    }
}
