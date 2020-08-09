package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.xpect.IOString;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.PATH_INPUT;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.PATH_INPUT_JSON;

public enum ProviderMapJsn {

    ALL(PATH_INPUT_JSON + "mapAll.jsn"),
    BOOLEAN(PATH_INPUT_JSON + "mapBoolean.jsn"),
    DATE(PATH_INPUT_JSON + "mapDate.jsn"),
    DOUBLE(PATH_INPUT_JSON + "mapDouble.jsn"),
    FLOAT(PATH_INPUT_JSON + "mapFloat.jsn"),
    EMPTY(PATH_INPUT_JSON + "mapEmpty.jsn"),
    LIST_DOUBLE123(PATH_INPUT_JSON + "listDouble123.jsn"),
    SMALL("{\"testString\": \"test\", \"testInt\": 1}"),
    VALUES_CALL_NUMBER_SCALAR(PATH_INPUT + "calls/ValuesCallNumberScalar.jsn"),
    VALUES_CALL_NUMBER_ARRAY(PATH_INPUT + "calls/ValuesCallNumberArray.jsn"),
    ;
    private static final Logger LOG = LoggerFactory.getLogger(ProviderMapJsn.class);
    private String fileName;
    private String content;
    ProviderMapJsn(final String content) {
        if (content.startsWith(PATH_INPUT_JSON)) {
            try {
                this.fileName = content;
                this.content = new IOString().setFileName(content).read();
            }
            catch (Exception e) {
                System.out.println("Could not load file " + content);
                throw new EoException(e);
            }
        }
        else if (content.startsWith(PATH_INPUT)) {
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
    }

    public String content() {
        return content;
    }

    public Map createMap() {
        return (Map) createMapEo().get();
    }

    public EO createMapEo() {
        return ProviderRootTest.createEo(content);
    }

    public EO createBtEo() {
        return ProviderRootTest.createEoWithClasses(BasicTest.class).mapObject(content);
    }

    public BasicTest createBt() {
        return (BasicTest) createBtEo().get();
    }


}
