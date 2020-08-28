package org.fluentcodes.projects.elasticobjects.calls.json;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.calls.values.TheGreetingCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by werner.diwischek on 11.03.18.
 */
public class JsonCallTest {

    @Test
    public void givenMapJso_whenExecuteCall_thenContentIsRead()  {
        EO eo = ProviderRootTestScope.createEo();

        Assertions.assertThat(eo.getLog()).isEmpty();
        String content = new JsonReadCall("map.json").execute(eo);
        Assertions.assertThat(content).isNotEmpty();
    }


    @Test
    public void givenMapJsonOnXYZ_whenExecuteCall_thenXYZHasContent()  {
        EO eo = ProviderRootTestScope.createEo();
        eo.setEmpty("(BasicTest)xyz");
        eo.addCall(new JsonReadCall("map.json").setTargetPath("xyz"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("xyz/testString")).isEqualTo("value");
        Assertions.assertThat(eo.getEo("xyz").getModelClass()).isEqualTo(BasicTest.class);
    }

    @Test
    public void givenListJsonOnX_Y_Z_whenExecuteCall_thenX_Y_ZHasContent()  {
        EO eo = ProviderRootTestScope.createEo();
        eo.setEmpty("x/y/(List)z");
        eo.addCall(new JsonReadCall("list.json").setTargetPath("x/y/z"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("x/y/z/1")).isEqualTo("test");
        Assertions.assertThat(eo.getEo("x/y/z").getModelClass()).isEqualTo(List.class);
    }

    @Test
    public void givenMapJsonOnX_Y_Z_whenExecuteCall_thenX_Y_ZHasContent()  {
        EO eo = ProviderRootTestScope.createEo();
        eo.setEmpty("x/y/z");
        eo.addCall(new JsonReadCall("map.json").setTargetPath("x/y/z"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("x/y/z/testString")).isEqualTo("value");
        Assertions.assertThat(eo.getEo("x/y/z").getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void givenEmpty_whenMapList_thenListIsSet()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.mapObject("[]");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
    }
}
