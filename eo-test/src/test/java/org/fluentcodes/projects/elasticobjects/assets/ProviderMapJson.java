package org.fluentcodes.projects.elasticobjects.assets;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootDev;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;
import org.fluentcodes.tools.xpect.IOString;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public enum ProviderMapJson {
    BOOLEAN(PATH_INPUT_MODELS + "mapBoolean.json");
    private String content;
    ProviderMapJson(final String content) {
        if (content.startsWith(PATH_INPUT)) {
            this.content = new IOString().setFileName(content).read();
        }
        else {
            this.content = content;
        }
    }

    public String content() {
        return content;
    }

    public Map createMap() {
        return (Map)createMapEo().get();
    }

    public EO createMapEo() {
        EO eo =  ProviderRootDev.createEo(content);
        Assertions.assertThat(eo.getLog()).isEmpty();
        return eo;
    }

    public EO createBtEo() {
        EO eo =  ProviderRootTest.createEoWithClasses(BasicTest.class).mapObject(content);
        Assertions.assertThat(eo.getLog()).isEmpty();
        return eo;
    }
    public BasicTest createBt() {
        return (BasicTest) createBtEo().get();
    }
}
