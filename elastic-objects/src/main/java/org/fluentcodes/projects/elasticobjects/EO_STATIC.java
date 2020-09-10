package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created 9.6.2018
 */

public class EO_STATIC {

    public static final String MAIN = "main";
    public static final String TEST = "test";

    public static final String CONFIG = "Config";
    public static final String CALL = "Call";
    public static final String JSON = "Json";

    public static final String CONFIG_JSON_END = ".json";
    public static final String CONFIG_PATH_MAIN_SIMPLE = "src/main/resources";
    public static final String CONFIG_PATH_TEST_SIMPLE = "src/test/resources";
    public static final String CONFIG_PATH_MAIN = CONFIG_PATH_MAIN_SIMPLE + Path.DELIMITER;
    public static final String CONFIG_PATH_TEST = CONFIG_PATH_TEST_SIMPLE + Path.DELIMITER;

    public static final String F_INDENT = "indent";
    public static final String F_PROVIDE_IN_ACTION = "provideInAction";
    public static final String F_DEFAULT_VALUE = "defaultValue";

    public static final String M_STRING = "String";

    public static final String M_MODEL_INTERFACE = "ModelInterface";
    public static final String M_MAP = "Map";
    public static final String M_CONFIG_IMPL = "ConfigImpl";

    public static final String F_UPPER_ID_KEY = "upper.ID";

    public static final String F_JOIN = "join";
    public static final String F_HIBERNATE_ANNOTATIONS = "hibernateAnnotations";
    public static final String F_NOT_NULL = "notNull";
    public static final String F_UNIQUE = "unique";
    public static final String F_INPUT_KEY = "inputKey";
    public static final String F_FILE_KEY = "fileKey";
    public static final String F_HIBERNATE = "hibernate";
    public static final String F_TABLE = "table";
    public static final String F_UPPER_ID = "ID";
    public static final String F_VIEW_KEY = "viewKey";
    public static final String F_NATURAL_KEYS = "naturalKeys";
    public static final String F_ID_KEY = "idKey";
    public static final String F_NAME = "name";
    public static final String F_JOIN_INVERSE = "joinInverse";
    public static final String F_FIELD_NAME = "fieldName";
    public static final String F_EXPANDED = "expanded";
    public static final String F_AND = "and";
    public static final String F_PATH_PATTERN = "pathPattern";
    public static final String F_MAP_KEY = "mapKey";


    public static final String J_MODULE_CONFIG_JSON = "ModuleConfig.json";
    public static final String CON_NEWLINE = "\n";
    public static final String CON_SEMICOLON = ";";
    public static final String CON_SPACE = " ";

    public static final void addMap(Map map, Object... keyValues)  {
        for (int i = 0; i < keyValues.length; i++) {
            if (i == keyValues.length - 1) {
                throw new EoException("Uneven key value pairs at " + i);
            }
            map.put(keyValues[i], keyValues[i + 1]);
            i++;
        }
    }
}
