package org.fluentcodes.projects.elasticobjects.assets;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.IOString;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.PATH_INPUT;

public enum TestProviderAnObjectJson {
    BOOLEAN("{\"" + AnObject.MY_BOOLEAN + "\": true}"),
    ALL(PATH_INPUT + "assets/bt/all.json"),
    ALL_TYPED(PATH_INPUT + "assets/bt/all.tjson"),
    DATE("{\"myDate\": 1465280215000}"),
    DOUBLE("{\"myDouble\": 2.2}"),
    DOUBLE_TYPED("{\"(Double)myDouble\": 2.2}"),
    EMPTY("{}"),
    FLOAT("{\"myFloat\": 1.1}"),
    FLOAT_TYPED("{\"(Float)myFloat\": 1.1}"),
    INT( "{\"myInt\": 1}"),
    SMALL("{\"myString\": \"test\", \"myInt\": 1}"),
    SMALL_TYPED("{\"(String)myString\": \"test\", \"(Integer)myInt\": 1}"),
    STRING("{\"myString\": \"test\"}"),
    SUB_TEST("{\"(ASubObject)myASubObject\": {\"name\": \"testOther\",\"myString\": \"test\"}}")
    ;

    private final String content;
    TestProviderAnObjectJson(final String content) {
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

    public Map createMapTest() {
        return (Map) createEoTest().get();
    }

    public Map createMapDev() {
        return (Map) createEoDev().get();
    }

    public EO createEoTest() {
        EO eo =  ProviderRootTestScope.createEo(content);
        Assertions.assertThat(eo.getLog()).isEmpty();
        return eo;
    }

    public EO createEoDev() {
        EO eo =  ProviderRootDevScope.createEo(content);
        Assertions.assertThat(eo.getLog()).isEmpty();
        return eo;
    }

    public EO createBtEo() {
        EO eo =  ProviderRootTestScope.createEoWithClasses(AnObject.class);
        eo.mapObject(content);
        Assertions.assertThat(eo.getLog()).isEmpty();
        return eo;
    }
    public AnObject createBt() {
        return (AnObject) createBtEo().get();
    }
}
