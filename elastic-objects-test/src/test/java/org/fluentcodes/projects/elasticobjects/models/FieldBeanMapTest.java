package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class FieldBeanMapTest {
    public static final FieldBeanMap FIELD_BEAN_MAP = new FieldBeanMap(Scope.TEST);

    @Test
    public void TEST_fieldBeanMap__find_id__notNull() {
        FieldBean bean = FIELD_BEAN_MAP.find("id");
        Assertions.assertThat(bean).isNotNull();
    }
}
