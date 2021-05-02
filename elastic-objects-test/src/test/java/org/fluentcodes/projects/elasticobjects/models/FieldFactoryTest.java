package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

public class FieldFactoryTest {

    @Test
    public void TEST_fieldBeanMap__find_id__notNull() {
        FieldBean bean = new FieldFactory().createBeanMap(ProviderRootTestScope.EO_CONFIGS)
                .get("id");
        Assertions.assertThat(bean).isNotNull();
    }
}
