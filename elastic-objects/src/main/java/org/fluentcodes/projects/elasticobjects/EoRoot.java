package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorList;

import java.util.List;

/**
 * Offers serialized setter and getter for java models
 */

public class EoRoot extends EoChild {
    private ExecutorList calls;
    private List<String> roles;
    private LoggingObject log;
    private EOConfigsCache provider;
    private boolean checkObjectReplication = false;
    private boolean hasError = false;

    public EoRoot(final EOConfigsCache cache)  {
        super(new Models(cache),LogLevel.DEBUG);
        this.provider = cache;
        this.log = new LoggingObjectsImpl(LogLevel.DEBUG);
    }

    public EoRoot(final EOConfigsCache cache, Object value)  {
        super(new Models(cache, value), LogLevel.DEBUG);
        this.log = new LoggingObjectsImpl(LogLevel.DEBUG);
        this.provider = cache;
        mapObject(value);
    }

    public EoRoot(final EOConfigsCache cache, Class... classes)  {
        super(new Models(cache, classes), LogLevel.DEBUG);
        this.log = new LoggingObjectsImpl(LogLevel.DEBUG);
        this.provider = cache;
    }

    public EoRoot(final EOConfigsCache cache, final LogLevel logLevel, Class<?>... classes)  {
        super(new Models(cache, classes), logLevel);
        this.provider = cache;
        this.log = new LoggingObjectsImpl(LogLevel.DEBUG);
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

    @Override
    public List<String> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }


    @Override
    public boolean hasRoles() {
        return roles != null && !roles.isEmpty();
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
            log(message, LogLevel.ERROR);
        }
        hasError = true;
    }

    @Override
    public boolean hasErrors() {
        return hasError;
    }

    @Override
    public ExecutorList getCalls() {
        return calls;
    }

    public void setCalls(ExecutorList actions) {
        this.calls = actions;
    }


    @Override
    public void addCall(CallExecutor callExecutor) {
        if (callExecutor == null) {
            return;
        }
        if (calls == null) {
            calls = new ExecutorList();
        }
        // directly add the builder without json parsing.
        this.calls.add(callExecutor);
    }

    @Override
    public boolean hasCalls() {
        return calls != null && !calls.isEmpty();
    }

    @Override
    public void execute() {
        calls.execute(this);
    }
}
