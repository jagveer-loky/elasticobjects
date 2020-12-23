package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderMapJson;
import org.junit.Test;


/**
 * Created by werner.diwischek on 14.1.18.
 */
public class AnObjectToJsonTest {

    @Test
    public void mapDefault()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String serialized = new EOToJSON().toJson(eo);
        //AssertEO.compare(serialized);
    }

    @Test
    public void withIndent0()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String stringified = new EOToJSON()
                .setIndent(0)
                .toJson(eo);
        //AssertEO.compare(stringified);
    }

    @Test
    public void withIndent1()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String stringified = new EOToJSON()
                .setIndent(2)
                .toJson(eo);
        //AssertEO.compare(stringified);
    }

    @Test
    public void withIndent2()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String stringified = new EOToJSON()
                .setIndent(2)
                .toJson(eo);
        //AssertEO.compare(stringified);
    }

    @Test
    public void withSTANDARD()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String stringified = new EOToJSON()
                .setIndent(2)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJson(eo);
        //AssertEO.compare(stringified);
    }


    @Test
    public void givenUntypedJsonFloat_thenFloat()  {
        EO eo = TestProviderAnObjectJson.FLOAT.createBtEo();
        Assertions.assertThat(eo.get(AnObject.MY_FLOAT)).isEqualTo(1.1f);
    }
}
