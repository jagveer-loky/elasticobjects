package org.fluentcodes.projects.elasticobjects.calls;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.domain.BaseBean;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;

/**
 * Created by Werner on 3.8.2017.
 */
public class CallImplTest {

    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(CallImpl.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(CallImpl.class);
    }

    @Test
    public void load__compare__xpected()  {
        EOConfigsCache cache = ProviderRootTestScope.EO_CONFIGS;
        ModelConfig config = cache.findModel(CallImpl.class.getSimpleName());
        Assertions.assertThat(config).isNotNull();
        Assertions.assertThat(config.getClassPath()).isEqualTo("src.main.java");

        // ModelConfig
        Assertions.assertThat(config.getModelKey()).isEqualTo(CallImpl.class.getSimpleName());
        Assertions.assertThat(config.getInterfaces()).isEqualTo(SimpleCommand.class.getSimpleName());
        Assertions.assertThat(config.getSuperKey()).isEqualTo(BaseBean.class.getSimpleName());
        Assertions.assertThat(config.getFieldKeys()).isNotEmpty();
        Assertions.assertThat(config.getPackagePath()).isEqualTo("org.fluentcodes.projects.elasticobjects.calls");
        // ModelProperties
        Assertions.assertThat(config.getCreate()).isFalse();
        Assertions.assertThat(config.getShapeType()).isEqualTo(ShapeTypes.CALL_BEAN);
        Assertions.assertThat(config.getDefaultImplementation()).isNull();

        new XpectString().compareAsString(config.toString());
    }


}
