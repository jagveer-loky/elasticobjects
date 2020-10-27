package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by werner.diwischek on 07.01.18.
 */
public class ParserCurlyBracketTest {

    @Test
    public void withoutSpace__containsStartSequence__true() {
        Assertions.assertThat(ParserCurlyBracket.containsStartSequence("=>{")).isTrue();
    }

    @Test
    public void withSpace__containsStartSequence__true() {
        Assertions.assertThat(ParserCurlyBracket.containsStartSequence("\n=>{")).isTrue();
    }

    @Test
    public void noSpace__isCloseSequence__true() {
        Assertions.assertThat(new ParserCurlyBracket("").isCloseSequence("=>{}.")).isTrue();
    }

    @Test
    public void tmp__parse__error() {
        String replace = "=>{TMP}.";
        String result = new ParserCurlyBracket(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("!!Null eo wrapper defined to get 'TMP'!!");
    }

    @Test
    public void tmp_default__parse__default() {
        String replace = "=>{TMP|>TEST}.";
        String result = new ParserCurlyBracket(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("TEST");
    }

    @Test
    public void call_not_exist_default__parse__default() {
        String replace = "==>{NO_CALL->|>TEST}.";
        String result = new ParserCurlyBracket(replace).parse(ProviderRootDevScope.createEo());
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("TEST");
    }

    @Test
    public void call_not_exist_default__parse_without_eo__default() {
        String replace = "==>{NO_CALL->|>TEST}.";
        String result = new ParserCurlyBracket(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("TEST");
    }

    @Test
    public void call_json_not_exist_default__parse__default() {
        String replace = "===>{\"(NO_CALL)\":{} |>TEST}.";
        String result = new ParserCurlyBracket(replace).parse(ProviderRootDevScope.createEo());
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("TEST");
    }

    @Test
    public void call_json_not_exist_default__parse_without_eo__default() {
        String replace = "==>{NO_CALL->|>TEST}.";
        String result = new ParserCurlyBracket(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("TEST");
    }

    @Test
    public void givenTmpWithSystemPrefix_thenReplaced () {
        String replace = "=>{@TMP}.";
        String result = new ParserCurlyBracket(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).doesNotContain("!!");
    }

    @Test
    public void placeHolder_testPath_eo__parse__replace () {
        EO eo = ProviderRootTestScope.createEo();
        eo.set("testValue", "testPath");
        String replace = "-=>{testPath}.-";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(result).isEqualTo("-" + "testValue" + "-");
    }

    @Test
    public void placeHolder_testPath_noEo__parse__exception_in_template () {
        String replace = "-=>{testPath}.-";
        String result = new ParserCurlyBracket(replace).parse();
        Assertions.assertThat(result).isEqualTo("-!!Null eo wrapper defined to get 'testPath'!!-");
    }

    @Test
    public void json_TemplateCall_testPath_testValue__parse__xpected () {
        final EO eo = ProviderRootTestScope.createEo("{\"testPath\":\"testString\"}");
        final String replace = "- ===>{\"(TemplateCall).\":{" +
                "\"sourcePath\":\"testPath\"}" +
                "}|+=>{_value}.+" +
                "=>{}.-";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("-+testString+-");
    }

    @Test
    public void set_TemplateCall_testPath_testValue__parse__xpected () {
        final EO eo = ProviderRootTestScope.createEo("{\"testPath\":\"testString\"}");
        final String replace = "- ==>{TemplateCall->testPath}|" +
                "+=>{_value}.+" +
                "=>{}.-";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("-+testString+-");
    }

    @Test
    public void TemplateCall_testPath__parse__replaced () {
        final EO eo = ProviderRootTestScope.createEo();
        eo.set(AnObject.MY_STRING, "testPath");
        final String replace = "-" +
                " ===>{\"(TemplateCall).\":{" +
                "\"sourcePath\":\"testPath\"}" +
                "}|" +
                "+" +
                "=>{_value}." +
                "+" +
                "=>{}.-";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("-+" + AnObject.MY_STRING + "+-");
    }

    @Test
    public void ValueCall_level0_map__parse__valueSet () {
        final EO eo = ProviderRootTestScope.createEo();
        final String replace = " ===>{\"(ValueCall).\":{" +
                "\"targetPath\":\"level0\"}" +
                "}|" +
                "{\"1\":2}" +
                "=>{}.";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("");
        Assertions.assertThat(eo.get("level0","1")).isEqualTo(2);
    }

    @Test
    public void ValueCall_targetPath_level0_placeHolder__parse__replaced () {
        final EO eo = ProviderRootTestScope.createEo();
        final String replace = " ===>{\"(ValueCall).\":{" +
                "\"targetPath\":\"level0\"}" +
                "}|" +
                "{\"1\":2}" +
                "=>{}." +
                "Value: \n=>{level0/1}.";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("Value: 2");
    }

    @Test
    public void direct_targetPath_level0_placeHolder__parse__replaced () {
        final EO eo = ProviderRootTestScope.createEo();
        final String replace = " ===>{\"level0\":{\"1\":2}}." +
                "Value: \n=>{level0/1}.";
       String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("Value: 2");
    }

    @Test
    public void array_TemplateCall_content_trailing_space__parse__123 () {
        final EO eo = ProviderRootTestScope.createEo();
        final String replace = "" +
                "===>{\"data\":[1,2,3]}." +
                "==>{TemplateCall->/data/*}|=>{_value}. =>{}.";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("123");
    }

    @Test
    public void array_123_TemplateCall_content_trailing_space_newline__parse__1_2_3_ () {
        final EO eo = ProviderRootTestScope.createEo();
        final String replace = "" +
                "===>{\"data\":[1,2,3]}." +
                "==>{TemplateCall->/data/*}|=>{_value}. \n=>{}.";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("1 2 3 ");
    }

    @Test
    public void array_123_TemplateCall_content_trailing_space_backslash_newline__parse__1_2_3_ () {
        final EO eo = ProviderRootTestScope.createEo();
        final String replace = "" +
                "===>{\"data\":[1,2,3]}." +
                "==>{TemplateCall->/data/*}|=>{_value}. \\\n=>{}.";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("1 2 3 ");
    }

    @Test
    public void array_TemplateCall_content_start_space_new_line__parse___1_2_3() {
        final EO eo = ProviderRootTestScope.createEo();
        final String replace = "" +
                "===>{\"data\":[1,2,3]}." +
                "==>{TemplateCall->/data/*}| \n=>{_value}.=>{}.";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains(" 1 2 3");
    }

    @Test
    public void array_TemplateCall_content_start_space_backslash_newline__parse___1_2_3() {
        final EO eo = ProviderRootTestScope.createEo();
        final String replace = "" +
                "===>{\"data\":[1,2,3]}." +
                "==>{TemplateCall->/data/*}| \\\n=>{_value}. =>{}.";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains(" 1 2 3");
    }

    @Test
    public void array_TemplateCall_content_start_space__parse__123() {
        final EO eo = ProviderRootTestScope.createEo();
        final String replace = "" +
                "===>{\"data\":[1,2,3]}." +
                "==>{TemplateCall->/data/*}| =>{_value}.=>{}.";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("123");
    }

    @Test
    public void array_TemplateCall_content_start_a_space__parse__123() {
        final EO eo = ProviderRootTestScope.createEo();
        final String replace = "" +
                "===>{\"data\":[1,2,3]}." +
                "==>{TemplateCall->/data/*}| a=>{_value}.=>{}.";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains(" a1 a2 a3");
    }

    @Test
    public void TemplateCall_parent__parse__val2a() {
        final EO eo = ProviderRootTestScope.createEo();
        final String replace = "" +
                "===>{\"val1\":{\"a\":\"1\"},\"val2\":{\"a\":\"2\"}}." +
                "==>{TemplateCall->/val1/a}| val2/a = \n =>{/val2/_parent}.  =>{}.";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains(" val2/a = 2");
    }

    @Test
    public void eo_TemplateCall_val1_hash__parse__toStringUsed() {
        final EO eo = ProviderRootTestScope.createEo();
        final String replace = "" +
                "===>{\"val1\":{\"a\":\"1\"}}." +
                "=>{val1}.";
        String result = new ParserCurlyBracket(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("{\n  \"a\": \"1\"\n}");
    }


}
