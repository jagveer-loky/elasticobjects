package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.ASubObject;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Test;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EoToJsonTest {
    @Test
    public void class_AnObject_values____expected() {
        final EoRoot eo = ProviderConfigMaps.createEo(AnObject.class);
        eo.map("{\"myString\": \"value\", \"myInt\", 1}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json)
                .isEqualTo("{\n" +
                        "  \"_rootmodel\": \"AnObject\",\n" +
                        "  \"myString\": \"value\",\n" +
                        "  \"myInt\": 1\n" +
                        "}");
    }

    @Test
    public void AnObject_myString_value____expected() {
        AnObject anObject = new AnObject();
        anObject.setMyString("value");
        String json = new EOToJSON()
                .toJson(ProviderConfigMaps.CONFIG_MAPS, anObject);
        Assertions.assertThat(json)
                .isEqualTo("{\n" +
                        "  \"_rootmodel\": \"AnObject\",\n" +
                        "  \"myString\": \"value\"\n" +
                        "}");
    }


    @Test
    public void ModelConfig_ASubObject____exception() {
        ModelConfig modelConfig = ProviderConfigMaps.CONFIG_MAPS.findModel(ASubObject.class);
        EOToJSON eoToJSON = new EOToJSON();
        Assertions
                .assertThatThrownBy(() -> {
                    eoToJSON
                            .toJson(ProviderConfigMaps.CONFIG_MAPS, modelConfig);
                })
                .isInstanceOf(EoException.class)
                .hasMessageContaining("ModelConfig has no create flag -> no empty instance will created for 'ModelConfigObject'");
    }

    @Test
    public void ModelConfig_ASubObject__JSONSerializationType_STANDARD__no_exception() {
        ModelConfig modelConfig = ProviderConfigMaps.CONFIG_MAPS.findModel(ASubObject.class);
        String json = new EOToJSON(JSONSerializationType.STANDARD)
                .toJson(ProviderConfigMaps.CONFIG_MAPS, modelConfig);
        Assertions.assertThat(json).isNotEmpty();
    }
}
