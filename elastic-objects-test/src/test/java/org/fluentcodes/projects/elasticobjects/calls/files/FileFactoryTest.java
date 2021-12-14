package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

public class FileFactoryTest {

    @Test
    public void fileBeanMap__find_AnObjectCsv__notNull() {
        FileBean bean = new FileFactory(ProviderConfigMaps.CONFIG_MAPS).createBeanMap()
            .get("AnObject.csv");
        Assertions.assertThat(bean).isNotNull();
    }

}
