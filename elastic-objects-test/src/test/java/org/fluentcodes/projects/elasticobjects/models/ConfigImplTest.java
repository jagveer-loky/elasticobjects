package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

/**
 * Created by Werner on 13.4.2017.
 */
public class ConfigImplTest {

    @Test
    public void changeProperties()  {
        ModelConfigMethods model = ProviderRootTestScope.EO_CONFIGS.findModel(ConfigConfig.class);
        /*Map map = model.getProperties();
        Assertions
                .assertThatThrownBy(
                        () -> {
                            model.getProperties().put("newKey", "newValue");
                        })
                .isInstanceOf(UnsupportedOperationException.class);*/
    }

    @Test
    public void createByModelConfig_throwsException()  {
        ModelConfigChecks.createThrowsException(ConfigConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(ConfigConfig.class);
    }
}
