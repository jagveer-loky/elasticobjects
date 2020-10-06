package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.PropertiesAccessor;

import static org.fluentcodes.projects.elasticobjects.calls.CallResource.CONFIG_KEY;
import static org.fluentcodes.projects.elasticobjects.calls.files.FileConfig.FILE_NAME;

public interface PropertiesTemplateResourceAccessor extends PropertiesAccessor {
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

    default boolean hasFileName() {
        return getFileName()!=null  && getProperties().containsKey(FILE_NAME);
    }

    default String getFileName() {
        return hasProperties() ? (String) getProperties().get(FILE_NAME) : null;
    }

    default void setFileName(final String value) {
        if (hasProperties()) {
            getProperties().put(FILE_NAME, value);
        }
    }

    default boolean hasConfigKey() {
        return getConfigKey()!=null  && getProperties().containsKey(CONFIG_KEY);
    }

    default String getConfigKey() {
        return hasProperties() ? (String) getProperties().get(CONFIG_KEY) : null;
    }

    default void setConfigKey(final String value) {
        if (hasProperties()) {
            getProperties().put(CONFIG_KEY, value);
        }
    }

}
