package org.fluentcodes.projects.elasticobjects.calls.values;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderJsonCalls;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

/**
 * Created by werner.diwischek on 11.03.18.
 */
public class SinusValueCallTest {

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(SinusValueCall.class);
    }

    @Test
    public void call()  {
        String json = ProviderJsonCalls.CALL_SINUS_ARRAY.content();
        Assertions.assertThat(json).isNotEmpty();
        EO eo = ProviderRootTestScope.createEo(json);
        eo.execute();
        Assertions.assertThat(eo.get("_template")).isEqualTo("sin(1.0) = 0.8414709848078965\n" +
                "sin(2.0) = 0.9092974268256817\n" +
                "sin(3.0) = 0.1411200080598672\n");
    }
}
