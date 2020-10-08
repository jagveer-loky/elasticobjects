package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.xpect.IOString;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.PATH_INPUT;

public enum TestProviderCallTemplate {
    CALL_SINUS_ARRAY(PATH_INPUT + "calls/call-sinus/CallSinusArray.tpl"),
    ;

    private final String content;
    private String fileName;
    private ProviderMapJson eoData;
    TestProviderCallTemplate(final String content) {
        if (content.startsWith(PATH_INPUT)) {
            try {
                this.fileName = content;
                this.content = new IOString().setFileName(content).read();
            }
            catch (Exception e) {
                System.out.println("Could not load file " + content);
                throw new EoException(e);
            }
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
        EO eo = ProviderRootTestScope.createEo();
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
