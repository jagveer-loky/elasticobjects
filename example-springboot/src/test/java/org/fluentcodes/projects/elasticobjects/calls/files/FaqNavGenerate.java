package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceStoreCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class FaqNavGenerate {
    @Test
    public void directoryListReadCall_faq__execute__listRelativePath() {
        EO eo = ProviderRootTestScope.createEo();

        DirectoryListReadCall call = new DirectoryListReadCall("faq");
        List<String> fileList = (List<String>)call.execute(eo);
        Assertions.assertThat(fileList).isNotEmpty();
        Assertions.assertThat(fileList.get(0)).startsWith("faq/");
    }

    @Test
    public void directoryMapReadCall_faq__execute__listRelativePath() {
        EO eo = ProviderRootTestScope.createEo();
        DirectoryMapReadCall call = new DirectoryMapReadCall("faq");
        Map<String, String> fileMap = (Map<String, String>) call.execute(eo);
        Assertions.assertThat(fileMap).isNotEmpty();
        //Assertions.assertThat(fileList.get()).startsWith("faq/");
    }

    @Test
    public void generate() {
        EO eo = ProviderRootTestScope.createEo();
        eo.set("examples","directory");
        TemplateResourceCall call = new TemplateResourceCall("Nav.tpl");
        String result = call.execute(eo);
    }
}
