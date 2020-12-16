package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.domain.test.ASubObject;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 04.11.2016.
 */
public class ModelConfigTest {
    private static final Logger LOG = LogManager.getLogger(ModelConfigTest.class);

    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(ModelConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(ModelConfig.class);
    }

    @Ignore
    @Test
    public void givenConfigEntries_whenResolve_thenNoErrors()  {
        ConfigChecks.resolveConfigs(ModelConfig.class);
    }

    @Ignore
    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(ModelConfig.class);
    }

    @Ignore
    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(ModelConfig.class);
    }

    @Test
    public void scopeTest__findModel_Unknown__exception()  {
        Assertions.assertThatThrownBy(()->{ProviderRootTestScope.EO_CONFIGS.findModel(SAMPLE_KEY_UNKNOW);})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void checkDependentModels()  {
        // Check if basic Models are available
        ModelConfig model = ProviderRootTestScope.EO_CONFIGS.findModel(AnObject.class.getSimpleName());
        Assert.assertEquals(AnObject.class.getSimpleName(), model.getModelKey());
        model = ProviderRootTestScope.EO_CONFIGS.findModel(ASubObject.class);
        Assert.assertEquals(ASubObject.class.getSimpleName(), model.getModelKey());
    }
}
