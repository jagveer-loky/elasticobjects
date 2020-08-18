package org.fluentcodes.projects.elasticobjects.calls.csv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderFileContent;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * These Tests will checks all CsvActions.
 * Created by Werner on 11.10.2016.
 */
public class CsvReadCallTest {
    private static final Logger LOG = LogManager.getLogger(CsvReadCallTest.class);
    private static final String LIST_SIMPLE_CSV = ProviderFileContent.LIST_SIMPLE_CSV.getConfigKey();

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(CsvReadCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(CsvReadCall.class);
    }

    @Test
    public void resolveModelConfig()  {
        ConfigModelChecks.resolve(CsvConfig.class);
    }

    @Test
    public void givenCallWithListSimpleCsv_whenExecute_thenListReturned()  {
        final Call call = new CsvReadCall(LIST_SIMPLE_CSV);
        EO eo = ProviderRootTestScope.createEo();
        List value = (List)call.execute(eo);
        Assertions.assertThat(value).isNotEmpty();
        Assertions.assertThat(value.size()).isEqualTo(2);
        Map firstRow = (Map) value.get(0);
        Assertions.assertThat(firstRow.size()).isEqualTo(2);
        Assertions.assertThat(firstRow.get("key1")).isEqualTo("value11");
    }

    @Test
    public void givenEoWithListSimpleCsv_whenExecute_thenParameterSet()  {
        final Call call = new CsvReadCall()
                .setConfigKey(LIST_SIMPLE_CSV);

        EO eo = ProviderRootTestScope.createEoWithClasses(List.class);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.size()).isEqualTo(2);
        Map firstRow = (Map) eo.get("0");
        Assertions.assertThat(firstRow.size()).isEqualTo(2);
        Assertions.assertThat(firstRow.get("key1")).isEqualTo("value11");
    }
}
