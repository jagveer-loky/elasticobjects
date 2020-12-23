package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObjectTest;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

public class EoConfigsCacheTest {

    /**
     * {@link AnObject} - {@link AnObjectTest}
     */
    @Test
    public void DEV__findModel_AnObject__exception()  {
        Assertions
                .assertThatThrownBy(()->{
                    ProviderRootDevScope.EO_CONFIGS.findModel(AnObject.class);})
                .hasMessageContaining("Could not find config entry for")
                .isInstanceOf(EoException.class);
    }

    @Test
    public void TEST__findModel__notNull()  {
        Assertions.assertThat(ProviderRootTestScope.EO_CONFIGS.findModel(AnObject.class)).isNotNull();
    }
}
