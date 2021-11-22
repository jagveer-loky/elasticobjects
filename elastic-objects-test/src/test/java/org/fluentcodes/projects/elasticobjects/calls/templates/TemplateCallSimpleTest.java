package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 22.03.2017.
 */
public class TemplateCallSimpleTest {

    @Test
    public void call_TemplateCall_placeholder__execute__replaced()  {
        TemplateCall call = new TemplateCall();
        call.setContent("Just a content with placeHolder testKey='.{testKey}.'");
        EO eo = ProviderConfigMaps.createEoDev();
        eo.set("testValue", "testKey");
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("testValue");
    }

    @Test
    public void eo_TemplateCall_placeholder__execute__replaced()  {
        TemplateCall call = new TemplateCall();
        call.setContent("Just a content with placeHolder testKey='.{testKey}.'");
        EO eo = ProviderConfigMaps.createEo();
        eo.addCall(call);
        eo.set("testValue", "testKey");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).contains("testValue");
    }

}
