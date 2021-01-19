package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.FieldBeanGen;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 3.1.2021.
 */
public class EoExampleFieldTest {
    public static FieldBeanGen getFieldBean(final String fieldKey) {
        return (FieldBeanGen)EoExampleModelTest.A_TEST_OBJECT_MODEL.getFieldBean(fieldKey);
    }

    @Test
    public void testString__getJavaFieldType__String() {
        Assertions.assertThat(getFieldBean("testString").getJavaFieldType()).isEqualTo("String");
    }

    @Test
    public void testMap__getJavaFieldType__HashMap() {
        Assertions.assertThat(getFieldBean("testMap").getJavaFieldType()).isEqualTo("HashMap");
    }

    @Test
    public void testMap__getJavaGetMethod__expected() {
        Assertions.assertThat(getFieldBean("testMap").getJavaGet4Bean()).isEqualTo("   public HashMap getTestMap() {\n" +
                "      return this.testMap;\n" +
                "   }\n" +
                "   public boolean hasTestMap() {\n" +
                "      return getTestMap() != null && !getTestMap().isEmpty();\n" +
                "   }\n");
    }

}
