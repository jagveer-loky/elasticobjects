package org.fluentcodes.projects.elasticobjects.calls.db;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DbTest {
/*
    @Before
    public void init() {
        final Call call = new DbSqlCall()
                .setConfigKey("h2:mem:basic:Create");
        EO eo = ProviderRootTestScope.createEo();
        Object value = call.execute(eo);
        Assertions.assertThat(eo.getLog())
                .isEmpty();
    }
    @Test
    public void givenH2MemBT_whenExecuteCall_thenResultSizeIs3()  {
        EO eo = ProviderRootTestScope.createEo();
        final Call callQuery = new DbQueryCall("h2:mem:basic:BasicTest");
        List result = (List)callQuery.execute(eo);
        Assertions.assertThat(result.size()).isEqualTo(3);
    }

    @Test
    public void givenH2MemBT_whenExecuteEo_thenResultSizeIs3()  {
        EO eo = ProviderRootTestScope.createEo();
        final Call callQuery = new DbQueryCall("h2:mem:basic:BasicTest").setTargetPath("abc");
        eo.addCall(callQuery);
        eo.execute();
        List result = (List) eo.get("abc");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result.size()).isEqualTo(3);
    }

    @Test
    public void givenH2MemBT_whenExecuteEoOnBT_thenResultSizeIs3()  {
        EO eo = ProviderRootTestScope.createEo();
        final Call callQuery = new DbQueryCall("h2:mem:basic:BasicTest").setTargetPath("abc");
        eo.addCall(callQuery);
        eo.setEmpty("(List,BasicTest)abc");
        eo.execute();
        List result = (List) eo.get("abc");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result.size()).isEqualTo(3);
        Assertions.assertThat(eo.getEo("abc/0").getModelClass()).isEqualTo(BasicTest.class);
    }

 */
}
