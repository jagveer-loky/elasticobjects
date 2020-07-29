package org.fluentcodes.projects.elasticobjects.fileprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.tools.xpect.IOString;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.PATH_INPUT_JSON;

public enum TestProviderMapJsn {
    ALL(PATH_INPUT_JSON + "mapAll.jsn"),
    BOOLEAN(PATH_INPUT_JSON + "mapBoolean.jsn"),
    CALL_SINUS_ARRAY(PATH_INPUT_JSON + "CallSinusArray.jsn"),
    CALL_SINUS_ARRAY_ON_TARGET_PATH(PATH_INPUT_JSON + "CallSinusArrayOnTargetPath.jsn"),
    DATE(PATH_INPUT_JSON + "mapDate.jsn"),
    DOUBLE(PATH_INPUT_JSON + "mapDouble.jsn"),
    FLOAT(PATH_INPUT_JSON + "mapFloat.jsn"),
    EMPTY(PATH_INPUT_JSON + "mapEmpty.jsn"),
    LIST_DOUBLE123(PATH_INPUT_JSON + "listDouble123.jsn"),
    SCS_CALL_SOURCE(PATH_INPUT_JSON + "ScsCallSource.jsn"),
    SCS_CALL_SOURCE_PATH(PATH_INPUT_JSON + "ScsCallSourcePath.jsn"),
    SCS_CALL_SOURCE_JOINED(PATH_INPUT_JSON + "ScsCallSourceJoined.jsn"),
    SMALL(PATH_INPUT_JSON + "mapSmall.jsn"),
    VALUES_CALL_NUMBER_SCALAR(PATH_INPUT_JSON + "ValuesCallNumberScalar.jsn"),
    VALUES_CALL_NUMBER_ARRAY(PATH_INPUT_JSON + "ValuesCallNumberArray.jsn"),
    ;
    private String content;
    TestProviderMapJsn(final String content) {
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
        return (Map) createMapEo().get();
    }

    public EO createMapEo() {
        return TestProviderRootTest.createEo(content);
    }

    public EO createBtEo() {
        return TestProviderRootTest.createEoWithClasses(BasicTest.class).mapObject(content);
    }
    public BasicTest createBt() {
        return (BasicTest) createBtEo().get();
    }


}
