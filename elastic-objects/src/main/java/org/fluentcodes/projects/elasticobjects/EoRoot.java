package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.ExecutorCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.PathElement.ERROR_LEVEL;
import static org.fluentcodes.projects.elasticobjects.PathElement.LOGS;
import static org.fluentcodes.projects.elasticobjects.PathElement.LOG_LEVEL;
import static org.fluentcodes.projects.elasticobjects.PathElement.SERIALIZATION_TYPE;

/**
 * Offers serialized setter and getter for java models
 */

public class EoRoot extends EoChild {
    private List<String> roles;
    private boolean checkObjectReplication = false;

    protected EoRoot(Object rootValue, Models rootModels) {
        super();
        setModels(rootModels);
        if (rootModels.isScalar()) {
            throw new EoException("Root could not be a scalar type but starting value is '" + rootModels.toString() + "'!");
        }
        if (rootModels.getModelClass() != Map.class) {
            new EoChild(this, PathElement.ROOT_MODEL, rootModels.toString(), new Models(rootModels.getConfigMaps(), String.class));
        }
        set(rootModels.create());
        mapObject(rootValue);
    }

    public static EoRoot of(final ConfigMaps cache)  {
        return ofClass(cache, Map.class);
    }

    public static EoRoot ofValue(final ConfigMaps cache, Object rootValue)  {
        if (rootValue == null) return ofClass(cache, Map.class);
        if (rootValue instanceof Class)  return ofClass(cache, (Class) rootValue);
        return new EoRoot(rootValue, Models.ofValue(cache, rootValue));
    }

    public static EoRoot ofClass(final ConfigMaps cache, Class... rootClasses)  {
        Models models = new Models(cache, rootClasses);
        if (!models.isCreate()) {
            throw new EoException("Could not create value from " + models.toString());
        }
        Object value = models.create();
        return new EoRoot(value, new Models(cache, rootClasses));
    }

    public static EoRoot ofClass(final ConfigMaps cache, final Object rootValue, Class... rootClasses)  {
        Models rootModels  = new Models(cache, rootClasses);
        if (rootModels.isScalar()) {
            throw new EoException("Could not create root with an scalar entry: '" + rootClasses[0].getSimpleName() + "'");
        }
        return new EoRoot(rootValue, rootModels);
    }

    @Override
    public String getPathAsString() {
        return Path.DELIMITER;
    }

    @Override
    void getPathAsString(final StringBuilder builder) {
        return;
    }

    @Override
    public EoRoot getRoot() {
        return this;
    }

    @Override
    public List<String> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }

    @Override
    public boolean execute() {
        String result = ExecutorCall.executeEo(this) ;
        return true;
    }


    @Override
    public LogLevel getLogLevel() {
        if (hasLogLevel()) {
            return (LogLevel) get(LOG_LEVEL);
        }
        return LogLevel.WARN;
    }

    private EO getLogEo() {
        if (!hasEo(LOGS)) {
            return createChild(new PathElement(LOGS));
        }
        return getEo(LOGS);
    }

    @Override
    public void log(String message, LogLevel logLevel) {
        if (message == null) {
            return;
        }
        setErrorLevel(logLevel);
        EO logEo = getLogEo();
        PathElement logElement = new PathElement(Integer.toString(logEo.size()));
        logEo.createChild(logElement, logLevel.name() + " - " + LocalDateTime.now().toString() + " - " + message);
    }

    @Override
    public List<String> getLogList() {
        return (List<String>) getLogEo().get();
    }

    private void setErrorLevel(LogLevel messageLogLevel) {
        if (getErrorLevel().ordinal() <= messageLogLevel.ordinal()) {
            set(messageLogLevel, ERROR_LEVEL);
        }
    }

    @Override
    public LogLevel getErrorLevel() {
        if (!hasEo(ERROR_LEVEL)) {
            return LogLevel.DEBUG;
        }
        return (LogLevel) get(ERROR_LEVEL);
    }

    @Override
    public JSONSerializationType getSerializationType() {
        if (!hasEo(SERIALIZATION_TYPE)) {
            return JSONSerializationType.EO;
        }
        return (JSONSerializationType) get(SERIALIZATION_TYPE);
    }

    @Override
    public void setSerializationType(JSONSerializationType serializationType) {
        createChild(PathElement.OF_SERIALIZATION_TYPE, serializationType);
    }

    @Override
    public void setCheckObjectReplication(boolean checkObjectReplication) {
        this.checkObjectReplication = checkObjectReplication;
    }

    @Override
    public boolean isCheckObjectReplication() {
        return this.checkObjectReplication;
    }


}
