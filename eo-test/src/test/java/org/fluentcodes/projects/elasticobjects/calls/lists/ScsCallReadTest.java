package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 28.10.2018.
 */
public class ScsCallReadTest {
    private static final Logger LOG = LogManager.getLogger(ScsCallReadTest.class);


    @Test
    public void givenCallWithSourceCsv_whenExecute_thenListReturned()  {
        final Call call = new ScsCallRead()
                .setConfigKey(CS_SOURCE_CSV);
        EO eo = ProviderRootTest.createEo();
        List value = (List)call.execute(eo);
        Assertions.assertThat(value).isNotEmpty();
        Assertions.assertThat(value.size()).isEqualTo(2);
        Map firstRow = (Map) value.get(0);
        Assertions.assertThat(firstRow.size()).isEqualTo(2);
        Assertions.assertThat(firstRow.get("key1")).isEqualTo("value11");
    }

    @Test
    public void givenEoWithSourceCsv_whenExecute_thenParameterSet()  {
        final Call call = new ScsCallRead()
                .setConfigKey(CS_SOURCE_CSV);

        EO eo = ProviderRootTest.createEoWithClasses(List.class);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.size()).isEqualTo(2);
        Map firstRow = (Map) eo.get("0");
        Assertions.assertThat(firstRow.size()).isEqualTo(2);
        Assertions.assertThat(firstRow.get("key1")).isEqualTo("value11");
    }

    @Test
    public void givenEoWithSourceCsvAndRowStart1_whenExecuteEo_thenParameterSet()  {
        final Call call = new ScsCallRead()
                .setRowStart(2)
                .setConfigKey(CS_SOURCE_CSV);
        EO eo = ProviderRootTest.createEoWithClasses(List.class);
        eo.addCall(call);
        Assertions.assertThat(eo.getLog()).isEmpty();
        eo.execute();
        Assertions.assertThat(eo.getEo("0").get("key1")).isEqualTo("value21");
        Assertions.assertThatThrownBy(() -> { eo.getEo("1");})
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Could not find entry for 1");
    }

    @Test
    public void givenEoWithSourceCsvAndRowEnd2_whenExecuteEo_thenParameterSet()  {
        final Call call = new ScsCallRead()
                .setRowEnd(2)
                .setConfigKey(CS_SOURCE_CSV);
        EO eo = ProviderRootTest.createEoWithClasses(List.class);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getEo("0").get("key1")).isEqualTo("value11");
        Assertions.assertThatThrownBy(() -> { eo.getEo("1");})
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Could not find entry for 1");
    }

    @Test
    public void givenEoWithSourceCsvAndRowHeadEmpty_whenExecuteEo_thenParameterSet()  {
        final Call call = new ScsCallRead()
                .setRowHead(-1)
                .setConfigKey(CS_SOURCE_CSV);
        EO eo = ProviderRootTest.createEoWithClasses(List.class);
        eo.addCall(call);
        Assertions.assertThat(eo.getLog()).isEmpty();
        eo.execute();
        Assertions.assertThat(eo.getEo("0").get("0")).isEqualTo("value11");
    }



    @Test
    public void givenEoWithSourceCsvAndRoleAnonym_whenExecuteEo_thenLogEntry()  {
        final Call call = new ScsCallRead()
                .setConfigKey(CS_SOURCE_CSV);
        EO eo = ProviderRootTest.createEo();
        eo.setRoles(R_ANONYM);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isNotEmpty();
    }

    @Test
    public void givenEoWithSourceCsvAndRoleGuest_whenExecuteEo_thenNoLogEntry()  {
        final Call call = new ScsCallRead()
                .setConfigKey(CS_SOURCE_CSV);
        EO eo = ProviderRootTest.createEo();
        eo.setRoles(R_GUEST);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isEmpty();
    }

    @Test
    public void givenEoBasicTestCsv_whenExecuteEo_thenLinkedHashMapForRow()  {
        final Call call = new ScsCallRead()
                .setConfigKey("BasicTest.csv");
        EO eo = ProviderRootTest.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        Assertions.assertThat(eo.getEo("0").get("testString")).isEqualTo(S_STRING);
        Assertions.assertThat(eo.getEo("0").getModelClass()).isEqualTo(LinkedHashMap.class);
    }

    @Test
    public void givenEoBasicTestCsvWithModels_whenExecuteEo_thenBasicTestForRow()  {
        final Call call = new ScsCallRead()
                .setConfigKey("BasicTest.csv")
                .setTargetPath("(List,BasicTest)level0");
        EO eo = ProviderRootTest.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        Assertions.assertThat(eo.getEo("level0/0").get("testString")).isEqualTo(S_STRING);
        Assertions.assertThat(eo.getEo("level0/0").getModelClass()).isEqualTo(BasicTest.class);
    }
}
