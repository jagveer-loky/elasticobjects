package org.fluentcodes.projects.elasticobjects.calls.values;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by werner.diwischek on 11.03.18.
 */
public class TheGreetingCallTest {

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(TheGreetingCall.class);
    }

    @Test
    public void givenNameAndGreeting_whenExecuteCall_thenNameIsSet()  {
        EO eo = ProviderRootTestScope.createEo();
        eo.set("Albert", "abc");
        Assertions.assertThat(eo.getLog()).isEmpty();
        String content = new TheGreetingCall("Hello").execute(eo.getEo("abc"));
        Assertions.assertThat(content).isEqualTo("Hello Albert!");
    }

    @Test
    public void givenNameAndGreetingAndTargetPath_whenExecuteCall_thenNamesAreSet()  {
        EO eo = ProviderRootTestScope.createEo();
        eo.set("Albert", "abc");
        eo.addCall(new TheGreetingCall("Hello").setSourcePath("abc").setTargetPath("/x/y/z"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("x/y/z")).isEqualTo("Hello Albert!");
    }

    @Test
    public void givenNameListAndGreeting_whenExecuteCall_thenNamesAreSet()  {
        EO eo = ProviderRootTestScope.createEo();
        List<String> nameList = Arrays.asList(new String[]{"Albert", "Victor", "Chessy"});
        eo.set(nameList, "abc");
        eo.addCall(new TheGreetingCall("Hello").setSourcePath("abc/*"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("abc/0")).isEqualTo("Hello Albert!");
    }

    @Test
    public void givenNameListAndGreetingAndTargetPath_whenExecuteCall_thenNamesAreSet()  {
        EO eo = ProviderRootTestScope.createEo();
        List<String> nameList = Arrays.asList(new String[]{"Albert", "Victor", "Chessy"});
        eo.set(nameList, "abc");
        eo.addCall(new TheGreetingCall("Hello").setSourcePath("abc/*").setTargetPath("/x/y/z"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("x/y/z/0")).isEqualTo("Hello Albert!");
    }

    @Test
    public void callTemplateCall_GreetingCall__execute__replaced()  {
        EO eo = ProviderRootTestScope.createEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        String content = new TemplateCall("* \n==>{TheGreetingCall->., }. *").execute(eo);
        Assertions.assertThat(content).isEqualTo("* Hi Stranger! *");
    }

}
