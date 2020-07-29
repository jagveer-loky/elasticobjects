package org.fluentcodes.projects.elasticobjects.fileprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.tools.xpect.IOString;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.PATH_INPUT_TEMPLATE;

public enum TestProviderCallTemplate {

    //SIMPLE_INSERT_WITH_PATH(PATH_INPUT_TEMPLATE + "SimpleInsertWithPath"),
    //DEEP_PATH_VALUE(PATH_INPUT_TEMPLATE + "SimpleInsertWithPath"),
    CALL_SINUS_ARRAY(PATH_INPUT_TEMPLATE + "CallSinusArray.tpl"),
    CALL_SINUS_ARRAY_JSON(PATH_INPUT_TEMPLATE + "CallSinusArray.json")
    ;

    private final String content;
    private TestProviderMapJson eoData;
    TestProviderCallTemplate(final String content) {
        if (content.startsWith(PATH_INPUT_TEMPLATE)) {
            this.content = new IOString().setFileName(content).read();
        }
        else {
            this.content = content;
        }
        try {
            eoData = TestProviderMapJson.valueOf(name());
        }
        catch (Exception e) {

        }
    }

    public EO getEo() {
        EO eo = TestProviderRootTest.createEo();
        eo.addCall(getCall());
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
