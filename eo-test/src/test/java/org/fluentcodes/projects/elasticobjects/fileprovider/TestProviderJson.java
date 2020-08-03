package org.fluentcodes.projects.elasticobjects.fileprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.tools.xpect.IOString;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.PATH_INPUT_TEMPLATE;

public enum TestProviderJson {
    CONTENT_EXAMPLE_DATA_JSON(PATH_INPUT_TEMPLATE + "content-example/ContentExampleData.json"),
    FOR_EMBEDDED_TEST(PATH_INPUT_TEMPLATE + "ForEmbeddedTest.json")
    ;

    private final String content;
    private ProviderMapJson eoData;
    TestProviderJson(final String content) {
        if (content.startsWith(PATH_INPUT_TEMPLATE)) {
            this.content = new IOString().setFileName(content).read();
        }
        else {
            this.content = content;
        }
        try {
            eoData = ProviderMapJson.valueOf(name());
        }
        catch (Exception e) {

        }
    }

    public EO getEo() {
        EO eo = ProviderRootTest.createEo();
        eo.mapObject(content);
        return eo;
    }

    public TemplateCall getCall() {
        TemplateCall call = new TemplateCall();
        call.setContent(content);
        return call;
    }

    public String content() {
        return content;
    }

}
