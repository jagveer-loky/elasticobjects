package org.fluentcodes.projects.elasticobjects;

import java.util.List;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface IEOLog {
    List<String> getLogList();
    IEOLog log(String message, LogLevel logLevel, Exception e);
    IEOLog log(String message, LogLevel logLevel);
    String getLog();

    LogLevel getLogLevel();
    IEOLog setLogLevel(LogLevel logLevel);

    LogLevel getErrorLevel();
    default boolean hasErrors() {
        return getErrorLevel() == LogLevel.ERROR;
    }

    default boolean checkLevel(LogLevel messageLevel) {
        return getLogLevel().ordinal() <= messageLevel.ordinal();
    }
    default IEOLog debug(String message) {
        if (checkLevel(LogLevel.DEBUG)) {
            return log(message, getLogLevel());
        }
        return this;
    }
    default IEOLog info(String message) {
        if (checkLevel(LogLevel.INFO)) {
            return log(message, LogLevel.INFO);
        }
        return this;
    }

    default IEOLog warn(String message) {
        if (checkLevel(LogLevel.WARN)) {
            return log(message, LogLevel.WARN);
        }
        return this;
    }

    default IEOLog error(String message) {
        if (checkLevel(LogLevel.ERROR)) {
            return log( message, LogLevel.ERROR);
        }
        return this;
    }

    default IEOLog warn(String message, Exception e) {
        if (checkLevel(LogLevel.WARN)) {
            return log( message, LogLevel.WARN, e);
        }
        return this;
    }

    default IEOLog error(String message, Exception e) {
        if (checkLevel(LogLevel.ERROR)) {
            return log( message, LogLevel.ERROR, e);
        }
        return this;
    }
}
