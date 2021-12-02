package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderMapJson;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEo;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;


/**
 * Created by werner.diwischek on 14.1.18.
 */
public class AnObjectToJsonTest {

    @Test
    public void mapDefault()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String serialized = new EOToJSON().toJson(eo);
        XpectString.assertJunit(serialized);
    }

    @SuppressWarnings({"java:S5976", "perhaps later"})
    @Test
    public void withIndent0()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String serialized = new EOToJSON()
                .setIndent(0)
                .toJson(eo);
        XpectString.assertJunit(serialized);
    }

    @SuppressWarnings({"java:S5976", "perhaps later"})
    @Test
    public void withIndent1()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String serialized = new EOToJSON()
                .setIndent(2)
                .toJson(eo);
        XpectString.assertJunit(serialized);
    }

    @SuppressWarnings({"java:S5976", "perhaps later"})
    @Test
    public void withIndent2()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String serialized = new EOToJSON()
                .setIndent(2)
                .toJson(eo);
        XpectString.assertJunit(serialized);
    }

    @Test
    public void withSTANDARD()  {
        EO eo = ProviderMapJson.EMPTY.createBtEo();
        String serialized = new EOToJSON()
                .setIndent(2)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJson(eo);
        XpectString.assertJunit(serialized);
    }


    @Test
    public void givenUntypedJsonFloat_thenFloat()  {
        EO eo = TestProviderAnObjectJson.FLOAT.createBtEo();
        Assertions.assertThat(eo.get(AnObject.MY_FLOAT)).isEqualTo(1.1f);
    }
}
