package org.fluentcodes.projects.elasticobjects.calls.db;

/**
 * Created by Werner on 13.11.2016.
 * Values from https://gist.github.com/mortezaadi/8619433
 */
public enum DbTypes {
    UNDEFINED("",""),
    ORACLE("com.mysema.query.jpa.support.ExtendedOracleDialect", "oracle.jdbc.driver.OracleDriver"),
    MYSQL("org.hibernate.dialect.MySQLInnoDBDialect", "com.mysql.jdbc.Driver"),
    H2("org.hibernate.dialect.H2Dialect", "org.h2.Driver"),
    HSQL("com.mysema.query.jpa.support.ExtendedHSQLDialect", "org.hsqldb.jdbcDriver");
    private String dialect;
    private String driver;

    DbTypes(String dialect, String driver) {
        this.driver = driver;
        this.dialect = dialect;
    }

    public String getDialect() {
        return dialect;
    }

    public String getDriver() {
        return driver;
    }
}
