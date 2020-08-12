package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.testitemprovider.*;

import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOListJson2Test {
    @Test
    public void withDefault()  {
        
        EO eo = ProviderListJson.JSON_EMPTY.createEoDev();
        String json = new EOToJSON().toJSON(eo);
        //AssertEO.compare(json);
    }

    @Test
    public void withIndent0()  {
        
        EO eo = ProviderListJson.JSON_EMPTY.createEoDev();
        String json = new EOToJSON()
                .setStartIndent(0)
                .toJSON(eo);
        //AssertEO.compare(json);
    }

    @Test
    public void withIndent1()  {
        
        EO eo = ProviderListJson.JSON_EMPTY.createEoDev();
        String json = new EOToJSON()
                .setStartIndent(1)
                .toJSON(eo);
        //AssertEO.compare(json);
    }

    @Test
    public void withIndent2()  {
        
        EO eo = ProviderListJson.JSON_EMPTY.createEoDev();
        String json = new EOToJSON()
                .setStartIndent(2)
                .toJSON(eo);
        //AssertEO.compare(json);
    }

    @Test
    public void withString()  {
        ProviderListJson.JSON_STRING.createEoDev();
    }

    @Test
    public void givenListWithStringValue_whenSerializeStandard_thenXpected()  {
        
        final EO eo = ProviderListJson.JSON_STRING.createEoDev();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(eo);
        new XpectString().compareAsString(stringified);
    }

    @Test
    public void givenListWithIntegerValue_whenSerializeStandard_thenXpected()  {
        final EO eo = ProviderListJson.JSON_INT.createEoDev();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(eo);
        new XpectString().compareAsString(stringified);
    }

}
