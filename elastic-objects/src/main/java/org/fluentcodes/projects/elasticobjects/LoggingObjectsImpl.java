package org.fluentcodes.projects.elasticobjects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * class defining standard adaptor rows and its methods without mapTransportableKey and write method defined in the separate class
 * Created by Werner on 02.07.2014.
 */

public class LoggingObjectsImpl implements LoggingObject {

    private static final Logger LOG = LogManager.getLogger(LoggingObjectsImpl.class);
    Long start = System.currentTimeMillis();
    private StringBuffer log = new StringBuffer("");
    private LogLevel logLevel = LogLevel.ERROR;
    //Constructor
    private LogLevel errorLevel = LogLevel.DEBUG;

    public LoggingObjectsImpl() {
    }

    public LoggingObjectsImpl(LogLevel logLevel) {
        this();
        if (logLevel != null) {
            this.logLevel = logLevel;
        }
    }

    public LoggingObjectsImpl(LoggingObject shape) {
        this.log = new StringBuffer(shape.getLog());
        this.logLevel = shape.getLogLevel();
    }

    public Long start() {
        return this.start;
    }

    public Long startDiff() {
        return System.currentTimeMillis() - start();
    }

    //========================
    // Logging
    //========================

    public void addLog(String toAdd) {
        this.log.append(toAdd);
    }


    // debug
    //----------------------
    public final void debug(String message) {
        log(message, LogLevel.DEBUG);
    }

    public final void debug(String message, Exception e) {
        log(message, e, LogLevel.DEBUG);
    }

    // info
    //----------------------
    public void infoTest(String message) {
        log(message, LogLevel.INFO_TEST);
    }

    public void infoTest(String message, Exception e) {
        log(message, e, LogLevel.INFO_TEST);
    }

    // info
    //----------------------
    final public void infoQsu(String message) {
        log(message, LogLevel.INFO_QSU);
    }

    final public void infoQsu(String message, Exception e) {
        log(message, e, LogLevel.INFO_QSU);
    }

    // info prod
    //----------------------
    final public void infoProd(String message) {
        log(message, LogLevel.INFO_PROD);
    }

    final public void infoProd(String message, Exception e) {
        log(message, e, LogLevel.INFO_PROD);
    }

    // info
    //----------------------
    public final void info(String message) {
        log(message, LogLevel.INFO);
    }

    public final void info(String message, Exception e) {
        log(message, e, LogLevel.INFO);
    }

    // message
    //----------------------
    public final void warn(String message) {
        log(message, LogLevel.WARN);
    }

    public final void warn(String message, Exception e) {
        log(message, e, LogLevel.WARN);
    }


    // error
    //----------------------
    public final void error(String message) {
        log(message, LogLevel.ERROR);
    }

    public final void error(String message, Exception e) {
        log(message, e, LogLevel.ERROR);
    }
    // LOG
    //----------------------


    private boolean log(String message, LogLevel logLevel) {
        if (message == null || message.equals("")) {
            message = "log without message!";
        }
        this.errorLevel(logLevel);
        if (LogLevel.getLevel(logLevel) > LogLevel.getLevel(this.logLevel)) {
            return false;
        }
        logMessage(logLevel, message);
        return true;
    }

    private void log(String message, Exception e, LogLevel logLevel) {
        if (!log(message, logLevel)) {
            return;
        }
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        this.log.append(sw.toString() + "\n");
        logException(logLevel, e);
    }

    public void logMessage(LogLevel logLevel, String message) {
        this.log.append(logLevel);
        this.log.append(" - ");
        this.log.append(this.startDiff());
        this.log.append(" - ");

        boolean flag = false;
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (!flag && element.getMethodName().matches("error|warn|debug|info")) {
                flag = true;
                continue;
            }
            if (!flag) {
                continue;
            }
            if (flag && !element.getMethodName().matches("error|warn|debug|info")) {
                this.log.append(element.getClassName().replaceAll(".*\\.", "") + "." + element.getMethodName() + "(Line " + element.getLineNumber() + ")");
                break;
            }
        }

        //http://stackoverflow.com/questions/421280/how-do-i-find-the-caller-of-a-method-using-stacktrace-or-reflection

        this.log.append(": ");
        this.log.append(message);
        this.log.append("\n");
        if (logLevel == LogLevel.ERROR) {
            LOG.error(message);
        } else if (logLevel == LogLevel.WARN) {
            LOG.warn(message);
        } else {
            LOG.debug(message);
        }
    }

    public void logException(LogLevel logLevel, Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        this.log.append(sw.toString() + "\n");
        if (LogLevel.getLevel(logLevel) == 0) {
            LOG.error(e);
        } else if (logLevel == LogLevel.WARN) {
            LOG.warn(e);
        } else {
            LOG.info(e.getMessage());
        }
    }


    public String getLog() {
        return this.log.toString();
    }

    public void setLog(String log) {
        this.log.append(log);
    }

    // logLevel
    //----------------------
    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void setLogLevel(Object logLevel) {
        setLogLevel(ScalarConverter.toString(logLevel));
    }

    public LogLevel getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(String logLevel) {
        if (logLevel == null || logLevel.equals("")) {
            return;
        }
        try {
            this.logLevel = LogLevel.valueOf(logLevel);
        } catch (Exception e) {
            warn("logLevel must be DEBUG|INFO|INFO_TEST|INFO_QSU|INFO_PROD|WARN|ERROR but is: " + logLevel, e);
        }
    }


    // errorLevel
    //----------------------

    public boolean hasErrors() {
        return this.errorLevel == LogLevel.ERROR;
    }

    private void errorLevel(LogLevel errorLevel2) {
        if (LogLevel.getLevel(errorLevel2) > LogLevel.getLevel(this.errorLevel)) {
            return;
        }
        this.errorLevel = errorLevel2;
    }

    /**
     * This sets the errorLevel for different LOG levels limit
     *
     * @return
     */

    public LogLevel getErrorLevel() {
        return this.errorLevel;
    }

    public void setErrorLevel(String logLevel) {
        if (logLevel == null || logLevel.equals("")) {
            return;
        }
        try {
            this.errorLevel = LogLevel.valueOf(logLevel);
        } catch (Exception e) {
            warn("logLevel must be DEBUG|INFO|INFO_TEST|INFO_QSU|INFO_PROD|WARN|ERROR but is: " + logLevel, e);
        }
    }

    @Override
    public String toString() {
        if (this == null) {
            return "";
        }
        return log.toString();
    }

}


