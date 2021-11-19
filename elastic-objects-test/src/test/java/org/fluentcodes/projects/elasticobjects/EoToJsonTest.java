package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.ASubObject;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EoToJsonTest {
    @Test
    public void Map_empty____expected()  {
        EO eo = ProviderConfigMaps.createEoDev("{}");
        String json = new EOToJSON().toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "}");
    }

    @Test
    public void Map_empty__indent_0__expected()  {
        EO eo = ProviderConfigMaps.createEoDev("{}");
        String json = new EOToJSON()
                .setIndent(0)
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{" +
                "}");
    }

    @Test
    public void Map_empty__indent_1__expected()  {
        EO eo = ProviderConfigMaps.createEoDev("{}");
        String json = new EOToJSON()
                .setIndent(1)
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "}");
    }

    @Test
    public void Map_empty__indent_2__expected()  {
        EO eo = ProviderConfigMaps.createEoDev("{}");
        String json = new EOToJSON()
                .setIndent(2)
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "}");
    }

    @Test
    public void Map_key_value__indent_2__expected()  {
        EO eo = ProviderConfigMaps.createEoDev("{\"key\": \"value\"}");
        String json = new EOToJSON()
                .setIndent(2)
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "    \"key\": \"value\"\n" +
                "}");
    }

    @Test
    public void Map_key_value__JSONSerialitationType_SCALAR__expected()  {
        EO eo = ProviderConfigMaps.createEoDev("{\"key\": \"value\"}");
        String json = new EOToJSON(JSONSerializationType.SCALAR)
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "  \"key\": \"value\"\n" +
                "}");
    }

    @Test
    public void Map_key_1__JSONSerialitationType_SCALAR__expected()  {
        EO eo = ProviderConfigMaps.createEoDev("{\"key\": 1}");
        String json = new EOToJSON(JSONSerializationType.SCALAR)
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "  \"key\": 1\n" +
                "}");
    }

    @Test
    public void Map_Long_key_1____expected()  {
        EO eo = ProviderConfigMaps.createEoDev("{\"(Long)key\": 1}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                
                "  \"(Long)key\": 1\n" +
                "}");
    }

    @Test
    public void Map_Long_key_1__JSONSerialitationType_SCALAR__expected()  {
        EO eo = ProviderConfigMaps.createEoDev("{\"(Long)key\": 1}");
        String json = new EOToJSON(JSONSerializationType.SCALAR)
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "  \"key\": 1\n" +
                "}");
    }

    @Test
    public void Map_key_1_1____expected()  {
        EO eo = ProviderConfigMaps.createEoDev("{\"key\": 1.1}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                
                "  \"(Float)key\": 1.1\n" +
                "}");
    }


    @Test
    public void Map_Double_key_1_1____expected()  {
        EO eo = ProviderConfigMaps.createEoDev("{\"(Double)key\": 1.1}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                
                "  \"(Double)key\": 1.1\n" +
                "}");
    }

    @Test
    public void Map_Date_key_1465280215000____expected()  {
        EO eo = ProviderConfigMaps.createEoDev("{\"(Date)key\": 1465280215000}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                
                "  \"(Date)key\": 1465280215000\n" +
                "}");
    }

    @Test
    public void Map_Boolean_key_true____expected()  {
        EO eo = ProviderConfigMaps.createEoDev("{\"key\": true}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "  \"key\": true\n" +
                "}");
    }

    @Test
    public void Map_myString_value_myInt_1____expected()  {
        EO eo = ProviderConfigMaps.createEoDev("{\"myString\": \"value\", \"myInt\", 1}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "  \"myString\": \"value\",\n" +
                "  \"myInt\": 1\n" +
                "}");
    }

    @Test
    public void class_AnObject_values____expected()  {
        final EO eo = ProviderConfigMaps.createEo(AnObject.class);
        eo.mapObject("{\"myString\": \"value\", \"myInt\", 1}");
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
    public void AnObject_myString_value____expected()  {
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
    public void List_empty__indent_0__expected()  {
        EO eo = ProviderConfigMaps.createEoDev("[]");
        String json = new EOToJSON()
                .setIndent(0)
                .toJson(eo);
        Assertions.assertThat(json).
                isEqualTo("{\"_rootmodel\": \"List\"" +
                "}");
    }


    @Test
    public void List_empty__indent_2__expected()  {
        EO eo = ProviderConfigMaps.createEoDev("[]");
        String json = new EOToJSON()
                .setIndent(2)
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "    \"_rootmodel\": \"List\"\n" +
                "}");
    }

    @Test
    public void List_String_test____expected()  {
        EO eo = ProviderConfigMaps.createEoDev("[\"test\"]");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "  \"_rootmodel\": \"List\",\n" +
                "  \"0\": \"test\"\n" +
                "}");

    }

    @Test
    public void List_String_test__JSONSerializeType_Standard__expected()  {
        EO eo = ProviderConfigMaps.createEoDev("[\"test\"]");
        String json = new EOToJSON()
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJson(eo.getRoot());
        Assertions.assertThat(json).isEqualTo("[\n" +
                "  \"test\"\n" +
                "]");
    }

    @Test
    public void List_Integer__JSONSerializionType_Standard__expected()  {
        EO eo = ProviderConfigMaps.createEoDev("[1]");
        String json = new EOToJSON()
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("[\n" +
                "  1\n" +
                "]");
    }

    @Test
    public void ModelConfig_ASubObject____exception() {
        ModelConfig modelConfig = ProviderConfigMaps.CONFIG_MAPS.findModel(ASubObject.class);
        EOToJSON eoToJSON = new EOToJSON();
        Assertions
                .assertThatThrownBy(()->{eoToJSON
                        .toJson(ProviderConfigMaps.CONFIG_MAPS, modelConfig);})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Field 'author' marked as final for model 'ModelConfigDbObject'.");
    }

    @Test
    public void ModelConfig_ASubObject__JSONSerializationType_STANDARD__no_exception() {
        ModelConfig modelConfig = ProviderConfigMaps.CONFIG_MAPS.findModel(ASubObject.class);
        String json = new EOToJSON(JSONSerializationType.STANDARD)
                        .toJson(ProviderConfigMaps.CONFIG_MAPS, modelConfig);
        Assertions.assertThat(json).isNotEmpty();
    }

    @Test
    public void stringifyNewLine() {
        final String input = "newline:\nnextline";
        final String value = EOToJSON.stringify(input);
        Assertions.assertThat(value).isEqualTo("newline:\\nnextline");
    }

    @Test
    public void stringifyQuote() {
        final String value = EOToJSON.stringify("quote:\"");
        Assertions.assertThat(value).isEqualTo("quote:\\\"");
    }

    @Test
    public void stringifyNumber() {
        final String value = EOToJSON.stringify(+1111111111.111);
        Assertions.assertThat(value).isEqualTo("1.111111111111E9");
    }

    @Test
    public void stringifyNothingSpecial() {
        final String value = EOToJSON.stringify("Nothing special");
        Assertions.assertThat(value).isEqualTo("Nothing special");
    }



}
