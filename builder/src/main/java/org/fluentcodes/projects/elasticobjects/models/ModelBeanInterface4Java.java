package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Werner on 30.12.2020.
 */
public interface ModelBeanInterface4Java extends Model {
    default String getJavaComment() {
        return ModelBeanGen.replaceLinks(getDescription());
    }

    default String getJavaAccessors() {
        StringBuilder builder = new StringBuilder();
        if (isInterface()) {
            for (FieldBeanInterface field : getFieldBeans().values()) {
                builder.append(((FieldBeanGen)field).getJavaAccess4Interface());
            }
        }
        else {
            for (FieldBeanInterface field : getFieldBeans().values()) {
                builder.append(((FieldBeanGen) field).getJavaAccess4Bean());
            }
        }
        return builder.toString();
    }

    default String getJavaInstanceVars() {
        StringBuilder builder = new StringBuilder();
        for (FieldBeanInterface field: getFieldBeans().values()) {
            builder.append(((FieldBeanGen)field).getJavaInstanceVar());
        }
        return builder.toString();
    }

    default String getJavaBeanConstructor() {
        if (!hasBean()) {
            return "";
        }
        StringBuilder builder = new StringBuilder("  public ");
        builder.append(getModelKey());
        builder.append("(final ");
        builder.append(getBean());
        builder.append(" bean) {\n");
        if (hasSuperKey()) {
            builder.append("    super(bean);\n");
        }
        for (FieldBeanInterface field: getFieldBeans().values()) {
            builder.append(((FieldBeanGen)field).getJavaBeanConstructor());
        }
        builder.append("   }\n");
        return builder.toString();
    }

    default String getJavaStaticNames() {
        StringBuilder builder = new StringBuilder();
        for (FieldBeanInterface field: getFieldBeans().values()) {
            if (!(field instanceof FieldBeanGen)) {
                throw new EoInternalException("Not a fieldBeanGen instance for "+ getModelKey() + "." + field.getFieldKey() );
            }
            if (isInterface()) {
                builder.append(((FieldBeanGen) field).getJavaStaticName4Interface());
            }
            else {
                builder.append(((FieldBeanGen) field).getJavaStaticName());
            }
        }
        return builder.toString();
    }

    default String getJavaImport() {
        Set<ModelBean> modelBeanSet = ((ModelBean)this).getModelSet();
        StringBuilder imports = new StringBuilder();
        for (ModelBean modelBean:modelBeanSet) {
            if ("java.lang".equals(modelBean.getPackagePath())) continue;
            if (getPackagePath().equals(modelBean.getPackagePath())) continue;
            imports.append("import ");
            imports.append(modelBean.getPackagePath());
            imports.append(".");
            imports.append(modelBean.getModelKey());
            imports.append(";\n");
        }
        return imports.toString();
    }

    default String getJavaHeader() {
        StringBuilder header = new StringBuilder(getJavaImport());
        header.append("/**\n" +
                " * \n" +
                " * ");
        header.append(getJavaComment());
        header.append(" \n" +
                " * @author ");
        header.append(getAuthor());
        header.append("\n" +
                " * @creationDate ");
        header.append(getCreationDate());
        header.append("\n" +
                " * @modificationDate ");
        header.append(new Date().toString());
        header.append("\n */\n");

        header.append(getJavaDeclaration());
        header.append(" {\n");
        return header.toString();
    }

    default String getJavaDeclaration() {
        if (isInterface()) {
            return "public interface " + getModelKey() + " " + getExtends4Interfaces();
        }
        return "public " + createAbstract() + "class " +  getModelKey() + " " + getExtends() + getImplements();
    }

    default String createAbstract() {
        if (isAbstract()) {
            return "abstract ";
        }
        return "";
    }


    default String getExtends() {
        if (!hasSuperKey()) return "";
        return "extends " + getSuperKey() + " ";
    }

    default String getExtends4Interfaces() {
        if (!hasInterfaces()) return "";
        return "extends " + getInterfaces()+ " ";
    }

    default String getImplements() {
        if (!hasInterfaces()) return "";
        return "implements " + getInterfaces() + " ";
    }

    default String getInterfaces() {
        if (!hasInterfaces()) return "";
        return Arrays.stream(getInterfaces().split(",")).collect(Collectors.joining(", "));
    }
}
