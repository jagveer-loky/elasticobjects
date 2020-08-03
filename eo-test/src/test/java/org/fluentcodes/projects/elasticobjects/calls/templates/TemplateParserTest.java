package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by werner.diwischek on 07.01.18.
 */
public class TemplateParserTest {

    @Test
    public void givenTmpWithoutSystemPrefix_thenReplaced() {
        String replace = "$[TMP]";
        String result = new TemplateParser(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("!!Could not find path 'TMP'!!");
    }

    @Test
    public void givenTmpWithSystemPrefix_thenReplaced () {
        String replace = "$[@TMP]";
        String result = new TemplateParser(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).doesNotContain("!!");
    }

    @Test
    public void givenSimpleTemplateAndEoTestPath_whenReplace_thenReplaced () {
        EO eo = ProviderRootTest.createEo();
        eo.set("testString", "testPath");
        String replace = "-$[testPath]-";
        String result = new TemplateParser(replace).parse(eo);
        Assertions.assertThat(result).isEqualTo("-testString-");
    }

    @Test
    public void givenSimpleTemplateAndNullEo_whenReplace_thenReplaced () {
        String replace = "-$[testPath]-";
        String result = new TemplateParser(replace).parse();
        Assertions.assertThat(result).isEqualTo("-!!Could not find path 'testPath'!!-");
    }

    @Test
    public void givenSimpleCallTemplateAndEoTestPath_whenReplace_thenReplaced () {
        final EO eo = ProviderRootTest.createEo();
        eo.set("testString", "testPath");
        final String replace = "-$[(TemplateCall)testPath]+$[_value]+$[/]-";
        String result = new TemplateParser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("-+testString+-");
    }


    @Test
    public void givenValueCall_whenReplace_thenReplaced () {
        final EO eo = ProviderRootTest.createEo();
        final String replace = "$[(ValueCall)level0]{\"1\":2}$[/]";
        String result = new TemplateParser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("");
        Assertions.assertThat(eo.get("level0","1")).isEqualTo(2);
    }
}
