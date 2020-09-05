package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.paths.Path;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

public class H_EO_STATIC {
    public static final String ACTIONS_HDB = "actions-db-hibernate";
    public static final String H = "H";
    public static final String H_QUERY = "HQuery";
    public static final String H_SQL = "HSql";
    public static final String F_HIBERNATE_KEY = "hibernateKey";
    public static final String F_HIBERNATE_MODEL_KEY = "hibernateModelKey";
    public static final String F_HIBERNATE_SQL_KEY = "hibernateSqlKey";
    public static final String F_HQL = "hql";

    public static final String F_MODELS = "models";
    public static final String F_CONFIG_FILE = "configFile";
    public static final String F_AUTO = "auto";
    public static final String F_PROPERTIES = "properties";
    public static final String F_SHOW_SQL = "showSql";
    public static final String F_FORMAT_SQL = "formatSql";
    public static final String F_USE_CACHE_SECOND_LEVEL = "useCacheSecondLevel";
    public static final String F_USE_CACHE_QUERY = "useCacheQuery";
    public static final String F_REGISTER_AUTO_LISTENER = "registerAutoListener";
    public static final String F_USER_REFLECTION_OPTIMIZER = "useReflectionOptimizer";
    public static final String F_MODEL_LIST = "modelList";

    public static final String M_H_QUERY_CALL = H_QUERY + CALL;
    public static final String M_H_QUERY_CONFIG = H_QUERY + CONFIG;
    public static final String CONFIG_H_QUERY_FILE = M_H_QUERY_CONFIG + CONFIG_JSON_END;
    public static final String CONFIG_H_QUERY_MAIN = CONFIG_PATH_MAIN + Path.DELIMITER + CONFIG_H_QUERY_FILE;
    public static final String CONFIG_H_QUERY_TEST = CONFIG_PATH_TEST + Path.DELIMITER + CONFIG_H_QUERY_FILE;

    public static final String M_H_SQL_CALL = H_SQL + CALL;
    public static final String M_H_SQL_CONFIG = H_SQL + CONFIG;
    public static final String CONFIG_H_SQL_FILE = M_H_SQL_CONFIG + CONFIG_JSON_END;
    public static final String CONFIG_H_SQL_MAIN = CONFIG_PATH_MAIN + Path.DELIMITER + CONFIG_H_SQL_FILE;
    public static final String CONFIG_H_SQL_TEST = CONFIG_PATH_TEST + Path.DELIMITER + CONFIG_H_SQL_FILE;

    public static final String M_H_CALL = H + CALL;
    public static final String M_H_CONFIG = H + CONFIG;
    public static final String CONFIG_H_FILE = M_H_CONFIG + CONFIG_JSON_END;
    public static final String CONFIG_H_MAIN = CONFIG_PATH_MAIN + Path.DELIMITER + CONFIG_H_FILE;
    public static final String CONFIG_H_TEST = CONFIG_PATH_TEST + Path.DELIMITER + CONFIG_H_FILE;
}
