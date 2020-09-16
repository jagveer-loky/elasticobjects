package org.fluentcodes.projects.elasticobjects;

public interface ConfigTypeAccessor {
    ConfigTypeImpl getConfigTypeImpl();

    default boolean hasConfigType() {
        return this.getConfigTypeImpl()!=null && this.getConfigTypeImpl().getName() != null && !this.getConfigTypeImpl().getName().isEmpty();
    }
    default String getConfigType() {
        return getConfigTypeImpl()!=null? getConfigTypeImpl().getName(): null;
    }
    default void setConfigType(final String configType) {
        if (getConfigTypeImpl()!=null) {
            getConfigTypeImpl().setName(configType);
        }
    }
}
