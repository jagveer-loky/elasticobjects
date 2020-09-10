package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EOGetTest {
    private static final Logger LOG = LogManager.getLogger(EOGetTest.class);

    @Test
    public void devEmpty__get_path__exception_thrown()  {
        EO eo = ProviderRootDevScope.createEo();
        Assertions.assertThatThrownBy(()->{eo.get(S_LEVEL0);})
                .hasMessage("Could not move to path 'level0' because key 'level0' does not exist on '/'.");
    }
}

