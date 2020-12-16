package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.generate.java.JavaCommentCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.JavaFieldTypeCall;
import org.fluentcodes.projects.elasticobjects.calls.values.StringUpperFirstCharCall;

public interface FieldBeanJavaMethods extends FieldBeanInterface {
    default String getJavaGetMethod() {
        return getJavaOverride() +
                "   public " + getJavaFieldType() + " get" + upperFieldKey() + "() {\n" +
                "      return this." + getFieldKey() + ";\n" +
                "   }\n";
    }

    default String getJavaSetMethod() {
        if (getFinal()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        if (getSuper())  {
            builder.append(getOverride());
        };
        builder.append(
                "   public " + getModelBean().getModelKey() + " set" + upperFieldKey() + "(final " + getJavaFieldType() + " " + getFieldKey() + ") {\n" +
                "      this." + getFieldKey() + " = " + getFieldKey() + ";\n" +
                "      return this;\n " +
                "   }\n"
        );
        return builder.toString();
    }

    default String upperFieldKey() {
        return StringUpperFirstCharCall.upper(getFieldKey());
    }
    default String getJavaFieldType() {
        return JavaFieldTypeCall.TYPE(getModelKeys());
    }
    default boolean hasEmpty() {
        return getModelKeys().matches(".*(Map|List|String).*");
    }
    default String getJavaDescription() {
        if (getSuper()) {
            return "";
        }
        return JavaCommentCall.FIELD_COMMENT(getDescription());
    }

    default String getJavaHasMethod() {
        if (getSuper()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(
                "   public boolean has" + upperFieldKey() + "() {\n" +
                        "      return this." + getFieldKey() + " != null");
        builder.append(hasEmpty()? " && this." + getFieldKey() + ".isEmpty()":"");
        builder.append(";\n   }\n");
        return builder.toString();
    }

    default String getJavaOverride() {
        return getOverride()?
                "   @Override\n":
                "";
    }


}
