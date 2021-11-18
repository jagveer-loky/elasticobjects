package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

public class FileFactoryTest {

    @Test
    public void TEST_fileBeanMap__find_AnObjectCsv__notNull() {
        FileBean bean = new FileFactory(ProviderRootTestScope.EO_CONFIGS).createBeanMap()
            .get("AnObject.csv");
        Assertions.assertThat(bean).isNotNull();
    }

}
