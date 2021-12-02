package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.tools.io.IOString;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.PATH_INPUT;

public enum ProviderMapJson {
    SIMPLE_INSERT_WITH_PATH("{\"key0\": \"test\", \"level0\": {\"key0\": \"testOther\"}}"),
    EMPTY("{}"),
    LIST_123_TYPED("{\"(List,Double)source\": {\"0\": 1,\"1\": 2,\"2\": 3}}"),
    LIST_SMALL("[\"test\", 1]"),
    VALUES_CALL_NUMBER_SCALAR("{\"(Double)source\":2.1}"),
    VALUES_CALL_NUMBER_ARRAY("{\"(List,Double)source\":[1,2,3]}"),
    ;
    private String content;

    ProviderMapJson(final String content) {
        if (content.startsWith(PATH_INPUT)) {
            this.content = new IOString(content).read();
        } else {
            this.content = content;
        }
    }

    public String content() {
        return content;
    }


    public EO createMapTestEo() {
        EO eo = ProviderConfigMaps.createEo(content);
        Assertions.assertThat(eo.getLog()).isEmpty();
        return eo;
    }


    public EO createBtEo() {
        EO eo = ProviderConfigMaps.createEoWithClasses(AnObject.class).mapObject(content);
        Assertions.assertThat(eo.getLog()).isEmpty();
        return eo;
    }

    public AnObject createBt() {
        return (AnObject) createBtEo().get();
    }
}
