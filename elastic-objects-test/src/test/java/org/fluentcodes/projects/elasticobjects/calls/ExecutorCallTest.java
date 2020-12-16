package org.fluentcodes.projects.elasticobjects.calls;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 9.7.2017.
 */
public class ExecutorCallTest {
    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(ExecutorCall.class);
    }

    @Test
    public void createModelConfig()  {
        ConfigModelChecks.createThrowsException(ExecutorCall.class);
    }
}
