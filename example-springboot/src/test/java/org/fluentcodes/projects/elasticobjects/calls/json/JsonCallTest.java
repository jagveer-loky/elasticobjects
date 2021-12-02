package org.fluentcodes.projects.elasticobjects.calls.json;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by werner.diwischek on 11.03.18.
 */
public class JsonCallTest {

    @Test
    public void givenMapJso_whenExecuteCall_thenContentIsRead()  {
        EO eo = ProviderConfigMaps.createEo();

        Assertions.assertThat(eo.getLog()).isEmpty();
        String content = (String)new FileReadCall("map.json").execute(eo);
        Assertions.assertThat(content).isNotEmpty();
    }


    @Test
    public void givenMapJsonOnXYZ_whenExecuteCall_thenXYZHasContent()  {
        EO eo = ProviderConfigMaps.createEo();
        eo.setEmpty("(AnObject)xyz");
        eo.addCall(new FileReadCall("map.json").setTargetPath("xyz"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("xyz/myString")).isEqualTo("value");
        Assertions.assertThat(eo.getEo("xyz").getModelClass()).isEqualTo(AnObject.class);
    }

    @Test
    public void givenListJsonOnX_Y_Z_whenExecuteCall_thenX_Y_ZHasContent()  {
        EO eo = ProviderConfigMaps.createEo();
        eo.setEmpty("x/y/(List)z");
        eo.addCall(new FileReadCall("list.json").setTargetPath("x/y/z"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("x/y/z/1")).isEqualTo("test");
        Assertions.assertThat(eo.getEo("x/y/z").getModelClass()).isEqualTo(List.class);
    }

    @Test
    public void givenMapJsonOnX_Y_Z_whenExecuteCall_thenX_Y_ZHasContent()  {
        EO eo = ProviderConfigMaps.createEo();
        eo.setEmpty("x/y/z");
        eo.addCall(new FileReadCall("map.json").setTargetPath("x/y/z"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("x/y/z/myString")).isEqualTo("value");
        Assertions.assertThat(eo.getEo("x/y/z").getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void givenEmpty_whenMapList_thenListIsSet()  {
        EO eo = ProviderConfigMapsDev.createEo(List.class);
        eo.mapObject("[]");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
    }
}
