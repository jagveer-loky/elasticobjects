package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.xpect.IOString;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public enum TestProviderJson {
    CONTENT_EXAMPLE_DATA(PATH_INPUT + "calls/content-example/ContentExampleData.json"),
    FOR_EMBEDDED_TEST(PATH_INPUT + "calls/embedded/ForEmbeddedTest.json"),
    MAP_SMALL_WITH_KEY("{\"key0\": \"test\", \"key1\": 1}"),
    MAP_SMALL_WITH_KEY_AND_LIST("{\"key0\": \"test\", \"list\": [\"test\", 1]}"),
    VALUES_CALL_NUMBER_ARRAY("{\"(List,Double)source\":[1,2,3]}"),
    VALUES_CALL_NUMBER_SCALAR("{\"(Double)source\":2.1}"),
    ;
    private String fileName;
    private final String content;
    private ProviderMapJson eoData;
    TestProviderJson(final String content) {
    if (content.startsWith(PATH_INPUT)) {
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
        try {
            eoData = ProviderMapJson.valueOf(name());
        }
        catch (Exception e) {

        }
    }

    public EO getEoTest() {
        EO eo = ProviderRootTestScope.createEo();
        eo.mapObject(content);
        return eo;
    }


    public EO getEoDev() {
        EO eo = ProviderRootDevScope.createEo();
        eo.mapObject(content);
        return eo;
    }

    public TemplateCall getCall() {
        TemplateCall call = new TemplateCall();
        call.setContent(content);
        return call;
    }

    public String content() {
        return content;
    }

}
