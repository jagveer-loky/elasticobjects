package org.fluentcodes.projects.elasticobjects.calls.values;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

/**
 * Created by werner.diwischek on 11.03.18.
 */
public class GithubLinkCallTest {

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(GithubLinkCall.class);
    }

    @Test
    public void configKey_CallImpl__execute__$()  {
        GithubLinkCall call = new GithubLinkCall("CallImpl");
        EO eo = ProviderRootTestScope.createEo();
        String result = call.execute(eo);
    }

    @Test
    public void FileConfig_configKey_AnObjectCsv__execute__$()  {
        GithubLinkCall call = new GithubLinkCall("FileConfig", "AnObject.csv");
        EO eo = ProviderRootTestScope.createEo();
        String result = call.execute(eo);
        Assertions.assertThat(result).isEqualTo("\n" +
                "<nobreak><a target=\"github\" href=\"https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects-test/src/main/resources/input/assets/bt/AnObject.csv\"> <img src=\"/pics/github.png\" height=\"12\" width=\"12\" \" style=\"margin:0px 4px 0px 6px;\"/>AnObject.csv</a></nobreak>");
    }

    @Test
    public void call_NONE_SPIwebHeaderHtml___execute__expected()  {
        GithubLinkCall call = new GithubLinkCall("NONE", "SPI|web|Header.html");
        EO eo = ProviderRootTestScope.createEo();
        String result = call.execute(eo);
        Assertions.assertThat(result).isEqualTo("\n<nobreak><a target=\"github\" href=\"https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/web/Header.html\"> <img src=\"/pics/github.png\" height=\"12\" width=\"12\" \" style=\"margin:0px 4px 0px 6px;\"/>Header.html</a></nobreak>");
    }

    @Test
    public void eo_NONE_SPIwebHeaderHtml___execute__expected()  {
        GithubLinkCall call = new GithubLinkCall("NONE", "SPI|web|Header.html");
        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(call);
        eo.execute();
        String result = (String)eo.get(PathElement.TEMPLATE);
        Assertions.assertThat(result).isEqualTo("\n<nobreak><a target=\"github\" href=\"https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/web/Header.html\"> <img src=\"/pics/github.png\" height=\"12\" width=\"12\" \" style=\"margin:0px 4px 0px 6px;\"/>Header.html</a></nobreak>");
    }

    @Test
    public void template_NONE_SPIwebHeaderHtml___execute__expected()  {
        TemplateCall call = new TemplateCall("START ==>{GithubLinkCall->NONE, SPI|web|Header.html}. END");
        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(call);
        eo.execute();
        String result = (String)eo.get(PathElement.TEMPLATE);
        Assertions.assertThat(result).isEqualTo("START\n<nobreak><a target=\"github\" href=\"https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/web/Header.html\"> <img src=\"/pics/github.png\" height=\"12\" width=\"12\" \" style=\"margin:0px 4px 0px 6px;\"/>Header.html</a></nobreak> END");
    }



}
