package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_STRING;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.NATURAL_ID;

public class AnObjectTest {

    @Test
    public void TEST__setNaturalIdTest__getNaturalIdTest()  {
        ModelConfig config = ProviderRootTestScope.EO_CONFIGS
                .findModel(AnObject.class);
        Object object = config.create();
        Assertions.assertThat(object).isNotNull();
        config.set(NATURAL_ID, object, "test");
        Assertions.assertThat(((AnObject)object).getNaturalId()).isEqualTo("test");
        Assertions.assertThat(config.get(NATURAL_ID, object)).isEqualTo("test");
    }

    @Test
    public void TEST__setMyStringTest__getMyStringTest()  {
        ModelConfig config = ProviderRootTestScope.EO_CONFIGS
                .findModel(AnObject.class);
        Object object = config.create();
        Assertions.assertThat(object).isNotNull();
        config.set(MY_STRING, object, "test");
        Assertions.assertThat(((AnObject)object).getMyString()).isEqualTo("test");
        Assertions.assertThat(config.get(MY_STRING, object)).isEqualTo("test");
    }

    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(AnObject.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(AnObject.class);
    }


}
