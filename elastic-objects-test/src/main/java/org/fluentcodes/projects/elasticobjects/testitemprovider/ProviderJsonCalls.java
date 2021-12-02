package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.io.IOString;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.PATH_INPUT;


public enum ProviderJsonCalls {
    CALL_SINUS_ARRAY(PATH_INPUT + "calls/call-sinus/CallSinusArray.json"),
    CONFIG_KEYS_CALL_MODEL_CONFIG(PATH_INPUT + "calls/configs/ConfigKeysCallModelConfig.json");

    private String fileName;
    private final String content;
    private ProviderMapJson eoData;

    ProviderJsonCalls(final String content) {
        if (content.startsWith(PATH_INPUT)) {
            try {
                this.fileName = content;
                this.content = new IOString(content).read();
            } catch (Exception e) {
                System.out.println("Could not load file " + content);
                throw new EoException(e);
            }
        } else {
            this.content = content;
        }
        try {
            eoData = ProviderMapJson.valueOf(name());
        } catch (Exception e) {

        }
    }

    public EO createMapEo() {
        return ProviderConfigMaps.createEo(content);
    }

    public EO getEo() {
        EO eo = ProviderConfigMaps.createEo();
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
