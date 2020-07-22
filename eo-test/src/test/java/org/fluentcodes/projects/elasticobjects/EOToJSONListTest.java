package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.test.*;

import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOToJSONListTest {
    @Test
    public void withDefault()  {
        
        EO adapter = TestProviderListJson.JSON_EMPTY.createEo();
        String json = new EOToJSON().toJSON(adapter);
        //AssertEO.compare(json);
    }

    @Test
    public void withIndent0()  {
        
        EO adapter = TestProviderListJson.JSON_EMPTY.createEo();
        String json = new EOToJSON()
                .setStartIndent(0)
                .toJSON(adapter);
        //AssertEO.compare(json);
    }

    @Test
    public void withIndent1()  {
        
        EO adapter = TestProviderListJson.JSON_EMPTY.createEo();
        String json = new EOToJSON()
                .setStartIndent(1)
                .toJSON(adapter);
        //AssertEO.compare(json);
    }

    @Test
    public void withIndent2()  {
        
        EO adapter = TestProviderListJson.JSON_EMPTY.createEo();
        String json = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        //AssertEO.compare(json);
    }

    @Test
    public void withString()  {
        TestProviderListJson.JSON_STRING.createEo();
    }

    @Test
    public void withStringScalarTypes()  {
        
        final EO adapter = TestProviderListJson.JSON_STRING.createEo();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        new XpectString().compareAsString(stringified);
    }

    @Test
    public void withIntegerScalarTypes()  {
        
        final EO adapter = TestProviderListJson.JSON_INT.createEo();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
    }

}
