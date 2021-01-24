package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Test;

public class FileConfigMapTest {
    public static final FileConfigMap FILE_CONFIG_MAP = new FileConfigMap(Scope.TEST);
    
    @Test
    public void TEST_fileBeanMap__find_AnObjectCsv__notNull() {
        FileConfig bean = (FileConfig) FILE_CONFIG_MAP.find("AnObject.csv");
        Assertions.assertThat(bean).isNotNull();
    }

}
