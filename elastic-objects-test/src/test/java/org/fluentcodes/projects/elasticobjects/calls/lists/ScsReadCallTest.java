package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.R_ANONYM;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.R_GUEST;

/**
 * @author Werner Diwischek
 * @since 28.10.2018.
 */
public class ScsReadCallTest {
    private static final String LIST_SIMPLE_CSV = "ListSimple.csv";

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(ScsReadCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(ScsReadCall.class);
    }

    @Test
    public void call_ListSimpleCsv__execute__set_2rows()  {
        final ScsReadCall call = new ScsReadCall(LIST_SIMPLE_CSV);
        EO eo = ProviderRootTestScope.createEo();
        call.execute(eo);
        List value =(List) eo.get();
        Assertions.assertThat(value).isNotEmpty();
        Assertions.assertThat(value.size()).isEqualTo(2);
        Map firstRow = (Map) value.get(0);
        Assertions.assertThat(firstRow.size()).isEqualTo(2);
        Assertions.assertThat(firstRow.get("key1")).isEqualTo("value11");
    }

    @Test
    public void eo_ListSimpleCsv_rowStart_2__execute__set_2rows()  {
        final ScsReadCall call = new ScsReadCall(LIST_SIMPLE_CSV);

        EO eo = ProviderRootTestScope.createEoWithClasses(List.class);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.size()).isEqualTo(2);
        Map firstRow = (Map) eo.get("0");
        Assertions.assertThat(firstRow.size()).isEqualTo(2);
        Assertions.assertThat(firstRow.get("key1")).isEqualTo("value11");
    }

    @Test
    public void eo_ListSimpleCsv_rowStart_2__execute__setOnlyLastRow()  {
        final ScsReadCall call = new ScsReadCall(LIST_SIMPLE_CSV);
        call.setRowStart(2);
        EO eo = ProviderRootTestScope.createEoWithClasses(List.class);
        eo.addCall(call);
        Assertions.assertThat(eo.getLog()).isEmpty();
        eo.execute();
        Assertions.assertThat(eo.getEo("0").get("key1")).isEqualTo("value21");
        Assertions.assertThatThrownBy(() -> { eo.getEo("1");})
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Could not move to path '1' because key '1' does not exist on '/'.");
    }

    @Test
    public void eo_ListSimpleCsv_rowEnd_2__execute__setOnlyFirstRow()  {
        final ScsReadCall call = new ScsReadCall(LIST_SIMPLE_CSV);
        call.setRowEnd(2);
        EO eo = ProviderRootTestScope.createEoWithClasses(List.class);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getEo("0").get("key1")).isEqualTo("value11");
        Assertions.assertThatThrownBy(() -> { eo.getEo("1");})
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Could not move to path '1' because key '1' does not exist on '/'.");
    }

    @Test
    public void eo_ListSourceCsv_rowHead_off__execute__setListValues()  {
        final ScsReadCall call = new ScsReadCall(LIST_SIMPLE_CSV);
        call.setRowHead(-1);
        EO eo = ProviderRootTestScope.createEoWithClasses(List.class);
        eo.addCall(call);
        Assertions.assertThat(eo.getLog()).isEmpty();
        eo.execute();
        Assertions.assertThat(eo.getEo("0").get("0")).isEqualTo("value11");
    }



    @Test
    public void eo_ListSimpleCsv_role_anonym__execute__has_log()  {
        final ScsReadCall call = new ScsReadCall(LIST_SIMPLE_CSV);
        EO eo = ProviderRootTestScope.createEo();
        eo.setRoles(R_ANONYM);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isNotEmpty();
    }

    @Test
    public void eo_ListSimpleCsv_role_guest__execute__noLogEntry()  {
        final ScsReadCall call = new ScsReadCall(LIST_SIMPLE_CSV);
        EO eo = ProviderRootTestScope.createEo();
        eo.setRoles(R_GUEST);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isEmpty();
    }

    @Test
    public void eo__configKey_AnObjectCsv__execute__listMap()  {
        final ScsReadCall call = new ScsReadCall("AnObject.csv");
        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        Assertions.assertThat(eo.getEo("0").get(AnObject.MY_STRING)).isEqualTo("value1");
        Assertions.assertThat(eo.getEo("0").getModelClass()).isEqualTo(LinkedHashMap.class);
    }

    @Test
    public void eo__configKey_AnObjectCsv_targetPath_level0_List_AnObject__execute__$()  {
        final ScsReadCall call = new ScsReadCall(AnObject.class.getSimpleName() + ".csv");
        call.setTargetPath("(List," + AnObject.class.getSimpleName() + ")level0");
        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        Assertions.assertThat(eo.getEo("level0/0").get(AnObject.MY_STRING)).isEqualTo("value1");
        Assertions.assertThat(eo.getEo("level0/0").getModelClass()).isEqualTo(AnObject.class);
    }
}
