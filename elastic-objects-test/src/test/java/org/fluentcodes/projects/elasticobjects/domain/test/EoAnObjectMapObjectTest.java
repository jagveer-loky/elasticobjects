package org.fluentcodes.projects.elasticobjects.domain.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * Created by Werner on 04.11.2016.
 */
public class EoAnObjectMapObjectTest {
    private static final Logger LOG = LogManager.getLogger(EoAnObjectMapObjectTest.class);

    /**
     * Wiki example
     */
    @Test
    public void scope_dev__path_level0_myString__$() {
        final EO eo = ProviderRootTestScope.createEo();
        final String jsonString = "{\n" +
                "\t\"(" + AnObject.class.getSimpleName() + ")level0\":{\n" +
                "\t\t\"" + AnObject.MY_STRING + "\":\"value\"\n" +
                "    }\n" +
                "}";
        eo.mapObject(jsonString);
        assertThat(eo.get(S_LEVEL0, AnObject.MY_STRING)).isEqualTo("value");
        assertThat(((AnObject)eo.get("level0")).getMyString()).isEqualTo("value");
        assertThat(eo.getEo("level0").getModelClass()).isEqualTo(AnObject.class);
    }

    /**
     * Wiki example
     */
    @Test
    public void givenDev_whenMapTestStringWithPathLevel0AndComment_thenCommentIsSet() {
        final EO eo = ProviderRootTestScope.createEo();
        final String jsonString = "{\n" +
                "\t\"(" + AnObject.class.getSimpleName() + ")level0\":{\n" +
                "\t\t\"" + AnObject.MY_STRING + "\":\"value\",\n" +
                "\"_comment\":\"_comment is not a field of the " + AnObject.class.getSimpleName() + ".class\"" +
                "    }\n" +
                "}";
        eo.mapObject(jsonString);
        assertThat(eo.get("level0/_comment")).isEqualTo("_comment is not a field of the "  + AnObject.class.getSimpleName() + ".class");
        assertThat(((AnObject)eo.get("level0")).getMyString()).isEqualTo("value");
    }

    @Test
    public void givenTestBt_whenSetAnObjectFieldWithScalar_thenHasLog()  {
        final EO eo = ProviderRootTestScope.createEo(new AnObject());
        eo.mapObject(S_STRING);
        Assertions
                .assertThat(eo.getLog()).isNotEmpty();
    }


}
