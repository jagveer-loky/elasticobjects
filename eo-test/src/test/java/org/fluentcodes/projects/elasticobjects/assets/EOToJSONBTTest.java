package org.fluentcodes.projects.elasticobjects.assets;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;

import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderMapJson;
import org.junit.Test;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOToJSONBTTest {

    @Test
    public void mapDefault()  {
        EO adapter = ProviderMapJson.EMPTY.createBtEo();
        String serialized = new EOToJSON().toJSON(adapter);
        //AssertEO.compare(serialized);
    }

    @Test
    public void withIndent0()  {
        EO adapter = ProviderMapJson.EMPTY.createBtEo();
        String stringified = new EOToJSON()
                .setStartIndent(0)
                .toJSON(adapter);
        //AssertEO.compare(stringified);
    }

    @Test
    public void withIndent1()  {
        EO adapter = ProviderMapJson.EMPTY.createBtEo();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        //AssertEO.compare(stringified);
    }

    @Test
    public void withIndent2()  {
        EO adapter = ProviderMapJson.EMPTY.createBtEo();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        //AssertEO.compare(stringified);
    }

    @Test
    public void withSTANDARD()  {
        EO adapter = ProviderMapJson.EMPTY.createBtEo();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(adapter);
        //AssertEO.compare(stringified);
    }


    @Test
    public void withFloatStandard()  {
        EO adapter = ProviderMapJson.FLOAT.createBtEo();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(adapter);
    }



    /*@Test
    public void withSubTestMapAndSerializationTypePARAMS()  {
        EO adapter = TestEOProvider
                .create(BTProvider.createMapST());
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(adapter);
        //AssertEO.compare(stringified);
    }

    @Test
    public void withIndentAndSerializationTypeSCALAR_TYPES()  {
        EO adapter = BTProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        //AssertEO.compare(stringified);
    }
     */
}
