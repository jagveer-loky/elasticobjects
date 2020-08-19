package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.assets.BasicTest.TEST_OBJECT;

/**
 * Created by Werner on 04.11.2016.
 */
public class EoBtMapObjectTest {
    private static final Logger LOG = LogManager.getLogger(EoBtMapObjectTest.class);

    /**
     * Wiki example
     */
    @Test
    public void givenDev_whenMapTestStringWithPathLevel0_thenValueIsSet() {
        final EO eo = ProviderRootTestScope.createEo();
        final String jsonString = "{\n" +
                "\t\"(BasicTest)level0\":{\n" +
                "\t\t\"testString\":\"value\"\n" +
                "    }\n" +
                "}";
        eo.mapObject(jsonString);
        assertThat(eo.get("level0/testString")).isEqualTo("value");
        assertThat(((BasicTest)eo.get("level0")).getTestString()).isEqualTo("value");
        assertThat(eo.getEo("level0").getModelClass()).isEqualTo(BasicTest.class);
    }

    /**
     * Wiki example
     */
    @Test
    public void givenDev_whenMapTestStringWithPathLevel0AndComment_thenCommentIsSet() {
        final EO eo = ProviderRootTestScope.createEo();
        final String jsonString = "{\n" +
                "\t\"(BasicTest)level0\":{\n" +
                "\t\t\"testString\":\"value\",\n" +
                "\"_comment\":\"_comment is not a field of the BasicTest.class\"" +
                "    }\n" +
                "}";
        eo.mapObject(jsonString);
        assertThat(eo.get("level0/_comment")).isEqualTo("_comment is not a field of the BasicTest.class");
        assertThat(((BasicTest)eo.get("level0")).getTestString()).isEqualTo("value");
    }

    @Test
    public void givenTestBt_whenSetBTFieldWithScalar_thenHasLog()  {
        final EO eo = ProviderRootTestScope.createEo(new BasicTest());
        eo.mapObject(S_STRING);
        Assertions
                .assertThat(eo.getLog()).isNotEmpty();
    }


}
