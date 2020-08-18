package org.fluentcodes.projects.elasticobjects;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

public class DB_EO_STATIC {

    public static final String ACTIONS_DB = "actions-db";
    public static final String DB = "Db";
    public static final String DB_QUERY = "DbQuery";
    public static final String DB_SQL = "DbSql";
    public static final String DB_H2_FILE = "h2:file";
    public static final String DB_H2_MEM = "h2:mem";
    public static final String DB_HSQL_FILE = "hsql:fileTest";
    public static final String DB_HSQL_MEM = "hsql:mem";

    public static final String F_DB_QUERY_KEY = "dbQueryKey";
    public static final String F_DB_SQL_KEY = "dbSqlKey";
    public static final String F_SQL = "sql";
    public static final String F_DB_KEY = "dbKey";
    public static final String F_DB_SQL_LIST = "sqlList";
    public static final String F_SCHEMA = "schema";
    public static final String F_DRIVER = "driver";
    public static final String F_JNDI = "jndi";
    public static final String F_DB_TYPE = "dbType";
    public static final String F_EXTENSION = "extension";

    public static final String DQ_H2_MEM_BASIC_SUBTEST = "h2:mem:basic:SubTest";

    public static final String M_DB_QUERY_CALL = DB_QUERY + CALL;
    public static final String M_DB_QUERY_CONFIG = DB_QUERY + CONFIG;
    public static final String CONFIG_DB_QUERY_FILE = M_DB_QUERY_CONFIG + CONFIG_JSON_END;
    public static final String CONFIG_DB_QUERY_MAIN = CONFIG_PATH_MAIN + Path.DELIMITER + CONFIG_DB_QUERY_FILE;
    public static final String CONFIG_DB_QUERY_TEST = CONFIG_PATH_TEST + Path.DELIMITER + CONFIG_DB_QUERY_FILE;

    public static final String M_DB_SQL_CALL = DB_SQL + CALL;
    public static final String M_DB_SQL_CONFIG = DB_SQL + CONFIG;
    public static final String CONFIG_DB_SQL_FILE = M_DB_SQL_CONFIG + CONFIG_JSON_END;
    public static final String CONFIG_DB_SQL_MAIN = CONFIG_PATH_MAIN + Path.DELIMITER + CONFIG_DB_SQL_FILE;
    public static final String CONFIG_DB_SQL_TEST = CONFIG_PATH_TEST + Path.DELIMITER + CONFIG_DB_SQL_FILE;

    public static final String M_DB_CALL = DB + CALL;
    public static final String M_DB_CONFIG = DB + CONFIG;
    public static final String CONFIG_DB_FILE = M_DB_CONFIG + CONFIG_JSON_END;
    public static final String CONFIG_DB_MAIN = CONFIG_PATH_MAIN + Path.DELIMITER + CONFIG_DB_FILE;
    public static final String CONFIG_DB_TEST = CONFIG_PATH_TEST + Path.DELIMITER + CONFIG_DB_FILE;
}
