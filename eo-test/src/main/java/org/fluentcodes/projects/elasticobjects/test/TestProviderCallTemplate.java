package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.tools.xpect.IOString;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.PATH_INPUT_TEMPLATE;

public enum TestProviderCallTemplate {

    SIMPLE_INSERT_WITH_PATH(PATH_INPUT_TEMPLATE + "SimpleInsertWithPath"),
    DEEP_PATH_VALUE(PATH_INPUT_TEMPLATE + "SimpleInsertWithPath")
    ;

    private final String content;
    private TestProviderMapJson eoData;
    TestProviderCallTemplate(final String content) {
        if (content.startsWith(PATH_INPUT_TEMPLATE)) {
            this.content = new IOString().setFileName(content).read();
        }
        else {
            this.content = content;
        }
        try {
            eoData = TestProviderMapJson.valueOf(name());
        }
        catch (Exception e) {

        }
    }

    public String content() {
        return content;
    }

}
