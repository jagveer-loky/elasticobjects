package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceStoreCall;
import org.fluentcodes.projects.elasticobjects.calls.xlsx.XlsxReadCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

public class GenerateHtml {
    static final String XLSX_FILE = "content.xlsx";
    static final String DATA = "data";

    @Test
    public void createIntro() {
        EO eo = READ_XLSX(XLSX_FILE);
        TemplateResourceStoreCall call = new TemplateResourceStoreCall("Presentation.html");
        //call.setLocalCondition("head like Get an");
        call.setSourcePath("/data/*");
        call.setTargetFile("src/main/resources/static/presentation/eo->(StringReplaceWhiteSpaceCall)head targetPath=\"asString\"/..html");
        eo.addCall(call);
        eo.execute();
    }

    @Test
    public void createFaq() {
        EO eo = READ_XLSX(XLSX_FILE + ":faq");
        TemplateResourceStoreCall call = new TemplateResourceStoreCall("Faq.html");
        //call.setLocalCondition("head like Why EO called");
        call.setSourcePath("/data/*");
        call.setTargetFile("src/main/resources/static/faq/eo->(StringReplaceWhiteSpaceCall)head targetPath=\"asString\"/..html");
        eo.addCall(call);
        eo.execute();
    }

    public static EO READ_XLSX(final String xlsxFile) {
        XlsxReadCall call = new XlsxReadCall(xlsxFile);
        //call.setFilter("head like Get");
        call.setTargetPath(String.join(Path.DELIMITER,new String[]{ DATA, "eo->head."}));
        EO eo = ProviderRootTestScope.createEo();
        call.execute(eo);

        return eo;
    }
}
