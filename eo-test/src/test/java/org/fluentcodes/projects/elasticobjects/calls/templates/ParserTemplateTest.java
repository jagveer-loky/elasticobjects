package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by werner.diwischek on 07.01.18.
 */
public class ParserTemplateTest {

    @Test
    public void givenTmpWithoutSystemPrefix_thenReplaced() {
        String replace = "$[TMP]";
        String result = new ParserTemplate(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("!!Could not find path 'TMP'!!");
    }

    @Test
    public void givenTmpWithSystemPrefix_thenReplaced () {
        String replace = "$[@TMP]";
        String result = new ParserTemplate(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).doesNotContain("!!");
    }

    @Test
    public void givenSimpleTemplateAndEoTestPath_whenReplace_thenReplaced () {
        EO eo = ProviderRootTestScope.createEo();
        eo.set(AnObject.MY_STRING, "testPath");
        String replace = "-$[testPath]-";
        String result = new ParserTemplate(replace).parse(eo);
        Assertions.assertThat(result).isEqualTo("-" + AnObject.MY_STRING + "-");
    }

    @Test
    public void givenSimpleTemplateAndNullEo_whenReplace_thenReplaced () {
        String replace = "-$[testPath]-";
        String result = new ParserTemplate(replace).parse();
        Assertions.assertThat(result).isEqualTo("-!!Could not find path 'testPath'!!-");
    }

    @Test
    public void givenSimpleCallTemplateAndEoTestPath_whenReplace_thenReplaced () {
        final EO eo = ProviderRootTestScope.createEo();
        eo.set(AnObject.MY_STRING, "testPath");
        final String replace = "-$[(TemplateCall)testPath]+$[_value]+$[/]-";
        String result = new ParserTemplate(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("-+" + AnObject.MY_STRING + "+-");
    }

    @Test
    public void givenSimpleTemplateCallWithPlaceHolderAndEoTestPath_whenReplace_thenReplaced () {
        final EO eo = ProviderRootTestScope.createEo();
        eo.set(AnObject.MY_STRING, "testPath");
        final String replace = "-$[&testPath]+$[_value]+$[/]-";
        String result = new ParserTemplate(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("-+" + AnObject.MY_STRING + "+-");
    }

    @Test
    public void givenValueCall_whenReplace_thenReplaced () {
        final EO eo = ProviderRootTestScope.createEo();
        final String replace = "$[(ValueCall)level0]{\"1\":2}$[/]";
        String result = new ParserTemplate(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("");
        Assertions.assertThat(eo.get("level0","1")).isEqualTo(2);
    }

    @Test
    public void givenValueCallAndAddValueToResult_whenReplace_thenReplaced () {
        final EO eo = ProviderRootTestScope.createEo();
        final String replace = "$[(ValueCall)level0]{\"1\":2}$[/]Value: $[level0/1]";
        String result = new ParserTemplate(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("Value: 2");
    }
}
