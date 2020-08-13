package org.fluentcodes.projects.elasticobjects.assets;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.IOString;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public enum TestProviderBtJson {
    BOOLEAN("{\"" + BasicTest.TEST_BOOLEAN + "\": true}"),
    ALL(PATH_INPUT + "assets/bt/all.json"),
    ALL_TYPED(PATH_INPUT + "assets/bt/all.tjson"),
    DATE("{\"testDate\": 1465280215000}"),
    DOUBLE("{\"testDouble\": 2.2}"),
    DOUBLE_TYPED("{\"(Double)testDouble\": 2.2}"),
    EMPTY("{}"),
    FLOAT("{\"testFloat\": 1.1}"),
    FLOAT_TYPED("{\"(Float)testFloat\": 1.1}"),
    INT( "{\"testInt\": 1}"),
    SMALL("{\"testString\": \"test\", \"testInt\": 1}"),
    SMALL_TYPED("{\"(String)testString\": \"test\", \"(Integer)testInt\": 1}"),
    STRING("{\"testString\": \"test\"}"),
    SUB_TEST("{\"(SubTest)subTest\": {\"name\": \"testOther\",\"testString\": \"test\"}}")
    ;

    private final String content;
    TestProviderBtJson(final String content) {
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
        return (Map) createTestEo().get();
    }

    public EO createTestEo() {
        EO eo =  ProviderRootTestScope.createEo(content);
        Assertions.assertThat(eo.getLog()).isEmpty();
        return eo;
    }

    public EO createDevEo() {
        EO eo =  ProviderRootDevScope.createEo(content);
        Assertions.assertThat(eo.getLog()).isEmpty();
        return eo;
    }

    public EO createBtEo() {
        EO eo =  ProviderRootTestScope.createEoWithClasses(BasicTest.class);
        eo.mapObject(content);
        Assertions.assertThat(eo.getLog()).isEmpty();
        return eo;
    }
    public BasicTest createBt() {
        return (BasicTest) createBtEo().get();
    }
}
