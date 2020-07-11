package org.fluentcodes.projects.elasticobjects.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.tools.xpect.IOString;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public enum TestProviderMapJson {
    JSON_EMPTY("{}"),
    SIMPLE_INSERT_WITH_PATH(PATH_INPUT_JSON + "SimpleInsertWithPath.json"),
    ALL(PATH_INPUT_JSON + "mapAll.json"),
    BOOLEAN(PATH_INPUT_JSON + "mapBoolean.json"),
    DATE(PATH_INPUT_JSON + "mapDate.json"),
    DOUBLE(PATH_INPUT_JSON + "mapDouble.json"),
    EMPTY(PATH_INPUT_JSON + "mapEmpty.json"),
    FLOAT(PATH_INPUT_JSON + "mapFloat.json"),
    INT(PATH_INPUT_JSON + "mapInteger.json"),
    STRING(PATH_INPUT_JSON + "mapString.json"),
    SCS_CALL_SOURCE(PATH_INPUT_JSON + "ScsCallSource.jsn"),
    SCS_CALL_SOURCE_PATH(PATH_INPUT_JSON + "ScsCallSourcePath.jsn"),
    SCS_CALL_SOURCE_JOINED(PATH_INPUT_JSON + "ScsCallSourceJoined.jsn"),
    SMALL(PATH_INPUT_JSON + "mapSmall.json"),
    ;
    private String content;
    TestProviderMapJson(final String content) {
        if (content.startsWith(PATH_INPUT_JSON)) {
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
        EO eo =  TestProviderRootDev.createEo(content);
        Assertions.assertThat(eo.getLog()).isEmpty();
        return eo;
    }

    public EO createBtEo() {
        EO eo =  TestProviderRootTest.createEoWithClasses(BasicTest.class).mapObject(content);
        Assertions.assertThat(eo.getLog()).isEmpty();
        return eo;
    }
    public BasicTest createBt() {
        return (BasicTest) createBtEo().get();
    }
}
