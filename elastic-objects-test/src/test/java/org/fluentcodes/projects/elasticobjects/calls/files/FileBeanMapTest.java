package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.junit.Test;

public class FileBeanMapTest {
    public static final FileBeanMap FILE_BEAN_MAP = new FileBeanMap(Scope.TEST);

    @Test
    public void TEST_fileBeanMap__find_AnObjectCsv__notNull() {
        FileBean bean = FILE_BEAN_MAP.find("AnObject.csv");
        Assertions.assertThat(bean).isNotNull();
    }

}
