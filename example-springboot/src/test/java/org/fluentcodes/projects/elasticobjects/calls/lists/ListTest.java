package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Ignore;
import org.junit.Test;

public class ListTest {

    @Test
    public void givenEoBasicTestCsvWithModels_whenExecuteEo_thenBasicTestForRow()  {
        final Call call = new ScsReadCall()
                .setConfigKey("BasicTest.csv")
                .setTargetPath("(List,BasicTest)level0");
        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        Assertions.assertThat(eo.getEo("level0/0").get("testString")).isEqualTo("value1");
        Assertions.assertThat(eo.getEo("level0/0").getModelClass()).isEqualTo(BasicTest.class);
    }
}
