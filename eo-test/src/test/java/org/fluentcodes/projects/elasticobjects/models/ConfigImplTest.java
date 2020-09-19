package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_CONFIG_IMPL;
import static org.fluentcodes.projects.elasticobjects.models.ConfigImpl.PROPERTIES;

/**
 * Created by Werner on 13.4.2017.
 */
public class ConfigImplTest {
    private static final Logger LOG = LogManager.getLogger(ConfigImplTest.class);

    @Test
    public void changeProperties()  {
        ModelInterface model = ProviderRootTestScope.EO_CONFIGS.findModel(ConfigImpl.class);
        Map map = model.getProperties();
        Assertions
                .assertThatThrownBy(
                        () -> {
                            model.getProperties().put("newKey", "newValue");
                        })
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(ConfigImpl.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(ConfigImpl.class);
    }
}
