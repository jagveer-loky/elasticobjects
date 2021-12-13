package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEo;
import org.fluentcodes.tools.io.IORuntimeException;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Ignore;
import org.junit.Test;


/**
 * @author Werner Diwischek
 * @since 2.11.20.
 */
public class TemplateDirResourceCallTest {

    @Test
    public void eo_examples_fileName_DbModelReadCallHtml__execute__noException() {
        EO eo = ProviderConfigMaps.createEo("{\"(TemplateDirResourceCall)_asString\":{\n" +
                "                 \"fileConfigKey\":\"examples\",\n" +
                "                 \"fileName\":\"DbModelReadCall.html\",\n" +
                "                 \"logLevel\":\"NONE\"\n" +
                "                }\n" +
                "            }");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get(PathElement.TEMPLATE)).isNotEmpty();
        XpectString.assertJunit((String)eo.get(PathElement.TEMPLATE));
    }

    @Test
    public void eo_examples_fileName_CallImplHtml_logLevel_none__execute__noLog() {
        EO eo = ProviderConfigMaps.createEo("{\"(TemplateDirResourceCall)_asString\":{\n" +
                "                 \"fileConfigKey\":\"examples\",\n" +
                "                 \"fileName\":\"CallImpl.html\",\n" +
                "                 \"logLevel\":\"NONE\"\n" +
                "                }\n" +
                "            }");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void eo_examples_fileName_CallImplHtml__execute__hasLog() {
        EO eo = ProviderConfigMaps.createEo("{\"(TemplateDirResourceCall)_asString\":{\n" +
                "                 \"fileConfigKey\":\"examples\",\n" +
                "                 \"fileName\":\"CallImpl.html\"\n" +
                "                }\n" +
                "            }");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

}
