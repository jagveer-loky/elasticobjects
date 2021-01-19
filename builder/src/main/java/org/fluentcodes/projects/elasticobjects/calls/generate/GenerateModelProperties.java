package org.fluentcodes.projects.elasticobjects.calls.generate;

public interface GenerateModelProperties extends GenerateProperties {
    /*=>{}.*/
    /*=>{javaStaticNames}|*/
    default void defaultOverwrite() {
        if (getOverwrite() == null) {
            setOverwrite(false);
        }
    }
    String getPackagePath();

    void setPackagePath(String packagePath);
    default boolean hasPackagePath() {
        return getPackagePath()!=null && !getPackagePath().isEmpty();
    }
}
