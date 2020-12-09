package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL0;

public class EOGetTest {

    @Test
    public void devEmpty__get_path__exception_thrown()  {
        EO eo = ProviderRootDevScope.createEo();
        Assertions.assertThatThrownBy(()->{eo.get(S_LEVEL0);})
                .hasMessage("No value add for fieldName=level0");
    }
}

