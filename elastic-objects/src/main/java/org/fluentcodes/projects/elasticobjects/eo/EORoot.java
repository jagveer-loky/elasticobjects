package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Offers serialized setter and getter for java models
 */

public class EORoot extends EOContainer {
    private ExecutorList actions;
    private List<String> roles;
    private JSONSerializationType serializationType;
    private LoggingObject log;
    private EOConfigsCache provider;
    private EOExtension adapterExtension;
    private List objectRegistry;
    private boolean checkObjectReplication = false;

    protected EORoot(final EOBuilder params) throws Exception {
        super(params);
    }

    protected void initRoot(EOBuilder params) throws Exception {
        this.actions = new ExecutorList();
        this.roles = params.getRoles();
        this.serializationType = params.getSerializationType();
        this.adapterExtension = params.getEoExtension();
        if (params.getConfigCache() == null) {
            throw new Exception("MODULE_NAME only works with an embedded provider!");
        } else {
            this.provider = params.getConfigCache();
        }
        this.log = new LoggingObjectsImpl(params.getLogLevel());
    }

    public boolean isRoot() {
        return true;
    }

    public EOConfigsCache getConfigsCache() {
        return provider;
    }

    public boolean isCheckObjectReplication() {
        return this.checkObjectReplication;
    }

    public void setCheckObjectReplication(boolean checkObjectReplication) {
        this.checkObjectReplication = checkObjectReplication;
    }

    public List<String> getRoles() {
        return roles;
    }

    public JSONSerializationType getSerializationType() {
        return serializationType;
    }

    @Override
    public LogLevel getErrorLevel() {
        return log.getErrorLevel();
    }

    @Override
    public String getLog() {
        if (log == null) {
            return null;
        }
        return log.getLog();
    }

    public void setLog(LoggingObject loggingObject) {
        log.addLog(loggingObject.getLog());
    }

    protected void log(String message, Exception e, LogLevel messageLogLevel) {
        log.logMessage(messageLogLevel, message);
        log.logException(messageLogLevel, e);
    }

    protected void log(String message, LogLevel messageLogLevel) {
        log.logMessage(messageLogLevel, message);
    }

    @Override
    public void debug(String message) {
        if (checkLevel(LogLevel.DEBUG)) {
            log(message, LogLevel.DEBUG);
        }
    }

    @Override
    public void info(String message) {
        if (checkLevel(LogLevel.INFO)) {
            log(message, LogLevel.INFO);
        }
    }
    @Override
    public void warn(String message) {
        if (checkLevel(LogLevel.WARN)) {
            log(message, LogLevel.WARN);
        }

    }

    @Override
    public void error(String message) {
        if (checkLevel(LogLevel.ERROR)) {
            log(message, LogLevel.ERROR);
        }
    }

    @Override
    public void warn(String message, Exception e) {
        if (checkLevel(LogLevel.WARN)) {
            log(message, LogLevel.WARN);
        }
    }

    @Override
    public void error(String message, Exception e) {
        if (checkLevel(LogLevel.ERROR)) {
            log( message, LogLevel.ERROR);
        }

    }

    public EOExtension getAdapterExtension() {
        return adapterExtension;
    }

    @Override
    public ExecutorList getCalls() {
        return actions;
    }

    public void setActions(ExecutorList actions) {
        this.actions = actions;
    }


    @Override
    public void addCall(CallExecutor callExecutor) {
        if (callExecutor == null) {
            return;
        }// directly add the builder without json parsing.
        this.actions.add(callExecutor);
    }

    public void addCallExecutor(Map attributes)  {
        if (attributes == null||attributes.isEmpty()) {
            return;
        }// directly add the builder without json parsing.
        try {
            this.actions.add(attributes);
        }
        catch (Exception e) {
            this.warn(e.getMessage());
        }
    }

    @Override
    public boolean hasActions() {
        return actions != null && !actions.isEmpty();
    }

    @Override
    public void executeActions() {
        actions.execute(this);
    }

    @Override
    protected void initObjectRegistry() {
        this.objectRegistry = new ArrayList();
    }

    protected boolean checkObjectRegistry(Object object) {
        if (objectRegistry == null) {
            objectRegistry = new ArrayList();
        }
        for (Object registered : objectRegistry) {
            if (registered == object) {
                return true;
            }
        }
        objectRegistry.add(object);
        return false;
    }
}
