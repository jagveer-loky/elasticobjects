package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Ignore;
import org.junit.Test;


/**
 * @author Werner Diwischek
 * @since 2.11.20.
 */
public class TemplateDirResourceCallTest {

    @Test
    public void eo_examples_fileName_DbModelReadCallHtml__execute__noException() {
        EO eo = ProviderRootTestScope.createEo("{\"(TemplateDirResourceCall).\":{\n" +
                "                 \"templateFileConfigKey\":\"examples\",\n" +
                "                 \"fileName\":\"DbModelReadCall.html\",\n" +
                "                 \"logLevel\":\"NONE\"\n" +
                "                }\n" +
                "            }");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get(PathElement.TEMPLATE)).isNotEmpty();
    }

    @Test
    public void eo_examples_fileName_CallImplHtml_logLevel_none__execute__noLog() {
        EO eo = ProviderRootTestScope.createEo("{\"(TemplateDirResourceCall).\":{\n" +
                "                 \"templateFileConfigKey\":\"examples\",\n" +
                "                 \"fileName\":\"CallImpl.html\",\n" +
                "                 \"logLevel\":\"NONE\"\n" +
                "                }\n" +
                "            }");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void eo_examples_fileName_CallImplHtml__execute__hasLog() {
        EO eo = ProviderRootTestScope.createEo("{\"(TemplateDirResourceCall).\":{\n" +
                "                 \"templateFileConfigKey\":\"examples\",\n" +
                "                 \"fileName\":\"CallImpl.html\"\n" +
                "                }\n" +
                "            }");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

}
