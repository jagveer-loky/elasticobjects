package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.BaseInterface;
import org.junit.Test;

public class FieldBeanGenTest {
    private static final FieldBeanGen NATURAL_ID = (FieldBeanGen)ModelBeanGenTest.AN_OBJECT_BEAN.getFieldBean(BaseInterface.NATURAL_ID);

    @Test
    public void TEST_AnObject_NATURAL_ID___getJavaFieldType__String() {
        Assertions.assertThat(NATURAL_ID.getJavaFieldType()).isEqualTo("String");
    }
    @Test
    public void TEST_AnObject_NATURAL_ID__getJavaDescription___String() {
        Assertions.assertThat(NATURAL_ID.getJavaDescription()).isEqualTo("/*\n" +
                " The natural key in @Base\n" +
                "  */");
    }
    @Test
    public void TEST_AnObject_NATURAL_ID__createJavaOverride__String() {
        Assertions.assertThat(NATURAL_ID.getJavaOverrideAnnotation()).isEqualTo("");
    }

    @Test
    public void TEST_AnObject_NATURAL_ID__getJavaAccess4Bean__String() {
        Assertions.assertThat(NATURAL_ID.getJavaAccess4Bean()).isEqualTo("   public String getNaturalId() {\n" +
                "      return this.naturalId;\n" +
                "   }\n" +
                "   public boolean hasNaturalId() {\n" +
                "      return getNaturalId() != null && !getNaturalId().isEmpty();\n" +
                "   }\n" +
                "   public AnObject setNaturalId(final String naturalId) {\n" +
                "      this.naturalId = naturalId;\n" +
                "      return this;\n" +
                "    }\n" +
                "\n");
    }

    @Test
    public void TEST_AnObject_NATURAL_ID__getJavaSet4Bean__String() {
        Assertions.assertThat(NATURAL_ID.getJavaSet4Bean()).isEqualTo("   public AnObject setNaturalId(final String naturalId) {\n" +
                "      this.naturalId = naturalId;\n" +
                "      return this;\n" +
                "    }\n" +
                "\n");
    }

    @Test
    public void TEST_AnObject_NATURAL_ID__getJavaInstanceVar_String() {
        Assertions.assertThat(NATURAL_ID.getJavaInstanceVar()).isEqualTo("   /* The natural key in @Base */\n" +
                "   private String naturalId;\n");
    }

    @Test
    public void TEST_AnObject_NATURAL_ID__getJavaHas4Bean__String() {
        Assertions.assertThat(NATURAL_ID.getJavaHas4Bean()).isEqualTo("   public boolean hasNaturalId() {\n" +
                "      return getNaturalId() != null && !getNaturalId().isEmpty();\n" +
                "   }\n");
    }

}
