package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.paths.PathElement;
import org.fluentcodes.tools.xpect.IOString;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public enum ProviderListJson {
    JSON_EMPTY("[]"),
    JSON_SMALL("[\"" + S_STRING + "\",\n" + S_INTEGER + "\n]"),
    SMALL("{" +
            "\n\"" + PathElement.ROOT_MODEL + "\":\"" + List.class.getSimpleName() + "\"," +
            "\n\"0\":\"" + S_STRING + "\"," +
            "\n\"1\":" + S_INTEGER + "\n}"),

    JSON_STRING("[\"" + S_STRING + "\"]"),
    JSON_FILTER("[\"" + S_STRING + "\",\n\"" +  S_STRING_OTHER + "\",\n" + null + ",\n\"" +   S_KEY0 + "\",\n" +  S_INTEGER + "]"),
    JSON_INT("[" + S_INTEGER + "]"),
    BOOLEAN("{\"_rootmodel\": \"List\",\"(Boolean)0\": true}"),
    DATE("{\"_rootmodel\": \"List\",\"(Date)0\": 1465280215000}"),
    DOUBLE("{\"_rootmodel\": \"List\",\"(Double)0\": 2.2}"),
    EMPTY("{\"_rootmodel\": \"List\"}"),
    FLOAT("{\"_rootmodel\": \"List\",\"(Float)0\": 1.1}"),
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

    public List createListDev() {
        return (List) ProviderRootDevScope.createEo(content).get();
    }

    public EO createEoDev() {
        return ProviderRootDevScope.createEo(content);
    }
}
