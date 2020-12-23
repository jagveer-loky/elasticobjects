package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCallTest;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectEo;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;

/**
 * Created 6.8.2020
 */
public class TemplateCallTest {

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(TemplateCall.class);
    }

    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(TemplateCall.class);
    }

    @Test
    public void call_level0_test_StringUpperCall_StringUpperFirstCall__execute__TEST_Test()  {
        EO eo = ProviderRootTestScope.createEo();
        final String template = "START - \n" +
                "===>{\"level0\":\"test.\"," +
                "\n\"(StringUpperCall)\":{" +
                "\"sourcePath\":\"level0\", " +
                "\"targetPath\":\"" + Call.TARGET_AS_STRING + "\"}," +
                "\n\"(StringUpperFirstCharCall)\":{" +
                "\"sourcePath\":\"level0\", " +
                "\"targetPath\":\"" + Call.TARGET_AS_STRING + "\"}" +
                "}." +
                " END";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("START - TEST_Test. END");
    }

    @Test
    public void call_level0_asString_FileReadCall__execute__xpected()  {
        EO eo = ProviderRootTestScope.createEo();
        final String template = "START - \n" +
                "===>{" +
                "\n\"(FileReadCall)\":{" +
                "\"fileConfigKey\":\"" + FileReadCallTest.FILE_TEST_TXT + "\", " +
                "\"targetPath\":\"level0\"}," +
                "\n\"(FileReadCall)\":{" +
                "\"fileConfigKey\":\"" + FileReadCallTest.FILE_TEST_TXT + "\", " +
                "\"targetPath\":\"_asString\"}" +
                "}." +
                " - END";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("START - test - END");
        new XpectEo().compareAsString(eo);
    }

    @Test
    public void call_level0_asString_FileReadCall_KeepCall__execute__xpected()  {
        EO eo = ProviderRootTestScope.createEo();
        final String template = "START - /*\n" +
                "===>{" +
                "\n\"(FileReadCall)\":{" +
                "\"fileConfigKey\":\"" + FileReadCallTest.FILE_TEST_TXT + "\", " +
                "\"targetPath\":\"level0\"}," +
                "\n\"(FileReadCall)\":{" +
                "\"fileConfigKey\":\"" + FileReadCallTest.FILE_TEST_TXT + "\", " +
                "\"targetPath\":\"_asString\", " +
                "\"prepend\":\"\\n\", " +
                "\"keepCall\":\"JAVA\"}" +
                "}." +
                "*/ - END";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        new XpectString().compareAsString(result);
        new XpectEo().compareAsString(eo);
    }

    @Test
    public void call_asString_FileReadCall_KeepCall__execute__xpected()  {
        EO eo = ProviderRootTestScope.createEo();
        final String template = "START - /*\n==>{FileReadCall->" +
                FileReadCallTest.FILE_TEST_TXT + ", _asString, , JAVA}.*/ - END";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        new XpectString().compareAsString(result);
        new XpectEo().compareAsString(eo);
    }
}
