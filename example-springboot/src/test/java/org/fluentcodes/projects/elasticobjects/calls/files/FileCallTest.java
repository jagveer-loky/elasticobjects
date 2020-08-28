package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.calls.values.TheGreetingCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by werner.diwischek on 11.03.18.
 */
public class FileCallTest {

    @Test
    public void givenPublicTxt_whenExecuteCall_thenContentIsRead()  {
        EO eo = ProviderRootTestScope.createEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        String content = new FileReadCall("public.txt").execute(eo);
        Assertions.assertThat(content).isEqualTo("Everyone can see this content!");
    }


    @Test
    public void givenPublicTxtOnXYZ_whenExecuteCall_thenXYZHasContent()  {
        EO eo = ProviderRootTestScope.createEo();
        eo.set(new FileReadCall("public.txt"), "xyz");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("xyz")).isEqualTo("Everyone can see this content!");
    }
}
