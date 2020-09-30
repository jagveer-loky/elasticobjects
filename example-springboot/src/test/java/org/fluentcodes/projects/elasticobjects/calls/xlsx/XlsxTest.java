package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.List;

public class XlsxTest {
    @Test
    public void givenCallWithAnObjectTest_whenExecute_thenListReturned()  {
        final Call call = new XlsxReadCall()
                .setConfigKey("AnObject.xlsx:test");
        EO eo = ProviderRootTestScope.createEo();
        call.execute(eo);
        List value = (List)eo.get();
        Assertions.assertThat(value).isNotEmpty();
     }
}
