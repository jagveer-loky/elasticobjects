package org.fluentcodes.projects.elasticobjects.calls.csv;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * These Tests will checks all CsvActions.
 * Created by Werner on 11.10.2016.
 */
public class CsvReadCallTest {
    private static final String LIST_SIMPLE_CSV = "ListSimple.csv";

    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(CsvReadCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(CsvReadCall.class);
    }

    @Test
    public void resolveModelConfig()  {
        ModelConfigChecks.resolve(CsvConfig.class);
    }

    // TODO so why in mvn does not work
    @Ignore
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

    // TODO so why in mvn does not work
    @Ignore
    @Test
    public void givenEoWithListSimpleCsv_whenExecute_thenParameterSet()  {
        final Call call = new CsvReadCall(LIST_SIMPLE_CSV);

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
