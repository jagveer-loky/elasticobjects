package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.PropertiesAccessor;

public interface PropertiesTemplateResourceAccessor extends PropertiesAccessor {
    public String DIRECTIVE = "directive";
    public String END_DIRECTIVE = "endDirective";
    public String TEMPLATE_KEY = "templateKey";
    public String KEEP_CALL = "keepCall";

    default boolean hasKeepCall() {
        return getKeepCall()!=null && getKeepCall() != KeepCalls.NONE;
    }

    default KeepCalls getKeepCall() {
        if (!hasProperties() || !getProperties().containsKey(KEEP_CALL) || getProperties().get(KEEP_CALL) == null  ) {
            return KeepCalls.NONE;
        }
        if (getProperties().get(KEEP_CALL) instanceof KeepCalls) {
            return (KeepCalls) getProperties().get(KEEP_CALL);
        }
        if (getProperties().get(KEEP_CALL) instanceof String) {
            String value =  (String) getProperties().get(KEEP_CALL);
            try {
                return KeepCalls.valueOf(value);
            }
            catch (Exception e) {
                throw new EoException("Problem with keepCall value " + value,e);
            }
        }
        throw new EoException("Problem with keepCall value " + getProperties().get(KEEP_CALL).getClass() );
    }

    default void setKeepCall(final String value) {
        if (!hasProperties()) {
            return;
        }
        if (value == null || value.isEmpty()) {
            getProperties().put(KEEP_CALL, null);
            return;
        }
        try {
            getProperties().put(KEEP_CALL, KeepCalls.valueOf(value));
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }

    default void setKeepCall(final KeepCalls value) {
        if (hasProperties()) {
            getProperties().put(KEEP_CALL, value);
        }
    }

    default boolean hasDirective() {
        return getProperties()!=null && getProperties().containsKey(DIRECTIVE) && getProperties().get(DIRECTIVE)!=null;
    }

    default String getDirective() {
        return hasDirective() ? (String) getProperties().get(DIRECTIVE) : "";
    }

    default void setDirective(final String value) {
        if (hasProperties()) {
            getProperties().put(DIRECTIVE, value);
        }
    }
    
    default boolean hasEndDirective() {
        return getProperties()!=null && getProperties().containsKey(END_DIRECTIVE)  && getProperties().get(END_DIRECTIVE)!=null;
    }

    default String getEndDirective() {
        return hasEndDirective() ? (String) getProperties().get(END_DIRECTIVE) : "";
    }

    default void setEndDirective(final String value) {
        if (hasProperties()) {
            getProperties().put(END_DIRECTIVE, value);
        }
    }

    default boolean hasTemplateKey() {
        return getTemplateKey()!=null  && getProperties().containsKey(TEMPLATE_KEY);
    }

    default String getTemplateKey() {
        return hasProperties() ? (String) getProperties().get(TEMPLATE_KEY) : null;
    }

    default void setTemplateKey(final String value) {
        if (hasProperties()) {
            getProperties().put(TEMPLATE_KEY, value);
        }
    }
}
