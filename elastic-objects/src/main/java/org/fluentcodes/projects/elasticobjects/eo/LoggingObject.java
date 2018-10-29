package org.fluentcodes.projects.elasticobjects.eo;

/**
 * Created by Werner on 10.07.2014.
 */

public interface LoggingObject {

    //object lifetime
    Long start();

    Long startDiff();

    //logging
    void debug(String message);

    void info(String message);

    void infoTest(String message);

    void infoQsu(String message);

    void infoProd(String message);

    void warn(String message);

    void error(String message);

    void debug(String message, Exception e);

    void info(String message, Exception e);

    void infoTest(String message, Exception e);

    void infoQsu(String message, Exception e);

    void infoProd(String message, Exception e);

    void warn(String message, Exception e);

    void error(String message, Exception e);

    LogLevel getLogLevel();

    void setLogLevel(LogLevel logLevel);

    void setLogLevel(String logLevel);

    void setLogLevel(Object logLevel);

    boolean hasErrors();

    LogLevel getErrorLevel();

    String getLog();

    void addLog(String log);

    void logMessage(LogLevel logLevel, String message);

    void logException(LogLevel logLevel, Exception e);

    // interface wrapping
}


