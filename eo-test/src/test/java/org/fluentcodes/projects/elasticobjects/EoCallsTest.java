package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.values.SinusValueCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

/**
 * Created by werner.diwischek on 14.8.2020.
 */
public class EoCallsTest {

    @Test
    public void givenTest_thenCallsAreEmpty()  {
        EO eo = ProviderRootTestScope.createEo();
        Assertions.assertThat(((EoChild)eo).hasCalls()).isEqualTo(false);
        Assertions.assertThat(eo.sizeEo()).isEqualTo(0);
    }
}
