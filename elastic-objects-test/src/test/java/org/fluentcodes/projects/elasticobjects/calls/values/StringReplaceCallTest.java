package org.fluentcodes.projects.elasticobjects.calls.values;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;

/**
 * Created by werner.diwischek on 11.03.18.
 */
public class StringReplaceCallTest {

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(StringReplaceCall.class);
    }

    @Test
    public void givenModelConfigPackagePath_whenExecuteCall_thenDotsAreReplaced()  {
        EO eo = ProviderRootTestScope.createEo();
        eo.mapObject(ProviderRootTestScope.EO_CONFIGS.findModel(StringReplaceCall.class));
        Assertions.assertThat(eo.getLog()).isEmpty();
        String content = new StringReplaceCall("\\.", "/").execute(eo.getEo(PACKAGE_PATH));
        Assertions.assertThat(content).isEqualTo("org/fluentcodes/projects/elasticobjects/calls/values");
    }

    @Test
    public void call_TemplateCall_eo_StringReplaceCall_packagePath__execute__dotsAreReplaced()  {
        EO eo = ProviderRootTestScope.createEo();
        eo.mapObject(ProviderRootTestScope.EO_CONFIGS.findModel(StringReplaceCall.class));
        Assertions.assertThat(eo.getLog()).isEmpty();
        String content = new TemplateCall("* \n" +
                "===>{\"(StringReplaceCall).\":{" +
                "\"targetPath\":\"" + Call.TARGET_AS_STRING + "\", " +
                "\"toReplace\"=\"\\\\.\", " +
                "\"replaceBy\"=\"/\" }" +
                "}." +
                " *")
                .execute(eo.getEo(PACKAGE_PATH));
        Assertions.assertThat(content).isEqualTo("* org/fluentcodes/projects/elasticobjects/calls/values *");
    }
}
