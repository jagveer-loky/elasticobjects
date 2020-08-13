package org.fluentcodes.projects.elasticobjects.assets;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderMapJson;
import org.junit.Test;


/**
 * Created by werner.diwischek on 14.1.18.
 */
public class BtToJsonTest {

    @Test
    public void mapDefault()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String serialized = new EOToJSON().toJSON(eo);
        //AssertEO.compare(serialized);
    }

    @Test
    public void withIndent0()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String stringified = new EOToJSON()
                .setStartIndent(0)
                .toJSON(eo);
        //AssertEO.compare(stringified);
    }

    @Test
    public void withIndent1()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(eo);
        //AssertEO.compare(stringified);
    }

    @Test
    public void withIndent2()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(eo);
        //AssertEO.compare(stringified);
    }

    @Test
    public void withSTANDARD()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(eo);
        //AssertEO.compare(stringified);
    }


    @Test
    public void givenUntypedJsonFloat_thenFloat()  {
        EO eo = TestProviderBtJson.FLOAT.createBtEo();
        Assertions.assertThat(eo.get(BasicTest.TEST_FLOAT)).isEqualTo(1.1f);
    }



    /*@Test
    public void withSubTestMapAndSerializationTypePARAMS()  {
        EO eo = TestEOProvider
                .create(BTProvider.createMapST());
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(eo);
        //AssertEO.compare(stringified);
    }

    @Test
    public void withIndentAndSerializationTypeSCALAR_TYPES()  {
        EO eo = BTProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
        //AssertEO.compare(stringified);
    }
     */
}
