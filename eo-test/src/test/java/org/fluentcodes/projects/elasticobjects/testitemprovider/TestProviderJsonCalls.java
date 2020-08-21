package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.xpect.IOString;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public enum TestProviderJsonCalls {
    CALL_SINUS_ARRAY(PATH_INPUT + "calls/call-sinus/CallSinusArray.json"),
    CALL_SINUS_VALUE(PATH_INPUT + "calls/call-sinus/CallSinusValue.tjson"),
    CALL_SINUS_ARRAY_ON_TARGET_PATH(PATH_INPUT + "calls/call-sinus/CallSinusArrayOnTargetPath.tjson"),
    CONTENT_EXAMPLE_STATIC_JSON(PATH_INPUT + "calls/content-example/ContentExampleStatic.json"),
    CONTENT_EXAMPLE_DYNAMIC_JSON(PATH_INPUT + "calls/content-example/ContentExampleDynamic.json");

    private String fileName;
    private final String content;
    private ProviderMapJson eoData;
    TestProviderJsonCalls(final String content) {
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

    public EO createMapEo() {
        return ProviderRootTestScope.createEo(content);
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
