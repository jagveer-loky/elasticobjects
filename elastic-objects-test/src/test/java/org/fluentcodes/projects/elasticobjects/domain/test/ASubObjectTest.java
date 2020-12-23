package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Test;

public class ASubObjectTest {

    @Test
    public void DEV__find_ASubObject__exception()  {
        Assertions
                .assertThatThrownBy(()->{ProviderRootDevScope.EO_CONFIGS.findModel(ASubObject.class);})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(ASubObject.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(ASubObject.class);
    }



}
