package org.fluentcodes.projects.elasticobjects;


/**
 * Created by Werner on 18.11.2014.
 */
public enum LogLevel {
    DEBUG(5), INFO_TEST(4), INFO_QSU(3), INFO_PROD(2), INFO(2), WARN(1), ERROR(0);
    private int level = 0;

    LogLevel(int level) {
        this.level = level;
    }

    public static int getLevel(LogLevel debugLevel) {
        return debugLevel.level;
    }


}
