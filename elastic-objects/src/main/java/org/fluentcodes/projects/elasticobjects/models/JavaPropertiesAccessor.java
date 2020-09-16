package org.fluentcodes.projects.elasticobjects.models;

public interface JavaPropertiesAccessor {
    JavaProperties getJavaProperties();

    default boolean hasProperties() {
        return getJavaProperties()!=null;
    }

    default boolean isComplete() {
        return hasModule() && hasClassPath() && hasPackagePath();
    }

    default boolean hasModule() {
        return getPackagePath()!=null && !getPackagePath().isEmpty();
    }

    default String getModule() {
        return hasProperties()?getJavaProperties().getModule():null;
    }

    default void setModule(final String module) {
        if (hasProperties()) {
            getJavaProperties().setModule(module);
        }
    }

    default boolean hasClassPath() {
        return getClassPath()!=null && !getClassPath().isEmpty();
    }

    default String getClassPath() {
        return hasProperties()?getJavaProperties().getClassPath():null;
    }

    default void setClassPath(final String classPath) {
        if (hasProperties()) {
            getJavaProperties().setClassPath(classPath);
        }
    }

    default boolean hasPackagePath() {
        return getPackagePath()!=null && !getPackagePath().isEmpty();
    }

    default String getPackagePath() {
        return hasProperties()?getJavaProperties().getPackagePath():null;
    }

    default void setPackagePath(final String packagePath) {
        if (hasProperties()) {
            getJavaProperties().setPackagePath(packagePath);
        }
    }

    default boolean hasTargetDirectory() {
        return getTargetDirectory()!=null;
    }

    default String getTargetDirectory() {
        return isComplete() ? getModule() + "/"
                + getClassPath() + "/"
                + getPackagePath().replaceAll("\\.", "/"):null;
    }
}
