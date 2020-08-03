package org.fluentcodes.projects.elasticobjects.fileprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.tools.xpect.IOString;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public enum ProviderListJson {
    JSON_EMPTY("[]"),
    JSON_SMALL("[\"" + S_STRING + "\",\n" + S_INTEGER + "\n]"),
    JSON_STRING("[\"" + S_STRING + "\"]"),
    JSON_INT("[" + S_INTEGER + "]"),
    ALL(PATH_INPUT_JSON + "listAll.json"),
    BOOLEAN(PATH_INPUT_JSON + "listBoolean.json"),
    DATE(PATH_INPUT_JSON + "listDate.json"),
    DOUBLE(PATH_INPUT_JSON + "listDouble.json"),
    EMPTY(PATH_INPUT_JSON + "listEmpty.json"),
    FLOAT(PATH_INPUT_JSON + "listFloat.json"),
    SMALL(PATH_INPUT_JSON + "listSmall.json"),

            ;
    private final String content;
    ProviderListJson(final String content) {
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

    public List createList() {
        return (List) ProviderRootDev.createEo(content).get();
    }

    public EO createEo() {
        return ProviderRootDev.createEo(content);
    }
}
