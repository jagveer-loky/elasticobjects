package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.lists.ScsReadCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class XlsxTest {
    @Test
    public void givenCallWithBasicTestTest_whenExecute_thenListReturned()  {
        final Call call = new XlsxReadCall()
                .setConfigKey("BasicTest.xlsx:test");
        EO eo = ProviderRootTestScope.createEo();
        List value = (List)call.execute(eo);
        Assertions.assertThat(value).isNotEmpty();
     }
}
