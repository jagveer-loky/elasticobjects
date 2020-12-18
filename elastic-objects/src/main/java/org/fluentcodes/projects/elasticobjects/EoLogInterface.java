package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface EoLogInterface {
    List<String> getLogList();
    EoLogInterface log(String message, LogLevel logLevel, Exception e);
    EoLogInterface log(String message, LogLevel logLevel);
    String getLog();

    LogLevel getLogLevel();
    EoLogInterface setLogLevel(LogLevel logLevel);

    LogLevel getErrorLevel();
    default boolean hasErrors() {
        return getErrorLevel() == LogLevel.ERROR;
    }

    default boolean checkLevel(LogLevel messageLevel) {
        return getLogLevel().ordinal() <= messageLevel.ordinal();
    }
    default EoLogInterface debug(String message) {
        if (checkLevel(LogLevel.DEBUG)) {
            return log(message, getLogLevel());
        }
        return this;
    }
    default EoLogInterface info(String message) {
        if (checkLevel(LogLevel.INFO)) {
            return log(message, LogLevel.INFO);
        }
        return this;
    }

    default EoLogInterface warn(String message) {
        if (checkLevel(LogLevel.WARN)) {
            return log(message, LogLevel.WARN);
        }
        return this;
    }

    default EoLogInterface error(String message) {
        if (checkLevel(LogLevel.ERROR)) {
            return log( message, LogLevel.ERROR);
        }
        return this;
    }

    default EoLogInterface warn(String message, Exception e) {
        if (checkLevel(LogLevel.WARN)) {
            return log( message, LogLevel.WARN, e);
        }
        return this;
    }

    default EoLogInterface error(String message, Exception e) {
        if (checkLevel(LogLevel.ERROR)) {
            return log( message, LogLevel.ERROR, e);
        }
        return this;
    }
}
