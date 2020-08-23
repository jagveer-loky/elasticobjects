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
    public static final String F_TO_SERIALIZE = "toSerialize";
    public static final String F_PROVIDE_IN_ACTION = "provideInAction";
    public static final String F_DEFAULT_VALUE = "defaultValue";

    public static final String M_STRING = "String";

    public static final String M_MODEL_INTERFACE = "ModelInterface";
    public static final String M_MAP = "Map";
    public static final String M_CONFIG_IMPL = "ConfigImpl";

    public static final String F_DO_MAP = "doMap";
    public static final String F_UPPER_ID_KEY = "upper.ID";

    public static final String F_FILE_NAME = "fileName";
    public static final String F_PATH = "path";
    public static final String F_PASSWORD = "password";
    public static final String F_PACKAGE_GROUP = "packageGroup";
    public static final String F_DB_FIELD_PARAMS = "dbFieldParams";
    public static final String F_ROLE_PERMISSIONS = "rolePermissions";
    public static final String F_JOIN = "join";
    public static final String F_COL_KEYS = "colKeys";
    public static final String F_WRITE = "write";
    public static final String F_HIBERNATE_ANNOTATIONS = "hibernateAnnotations";
    public static final String F_NOT_NULL = "notNull";
    public static final String F_MODULE = "module";
    public static final String F_EXECUTE = "execute";
    public static final String F_IGNORE_HEADER = "ignoreHeader";
    public static final String F_SHAPE_TYPE = "shapeType";
    public static final String F_UNIQUE = "unique";
    public static final String F_INPUT_KEY = "inputKey";
    public static final String F_VIEW_FIELD_PARAMS = "viewFieldParams";
    public static final String F_HOST_NAME = "hostName";
    public static final String F_ROW_HEAD = "rowHead";
    public static final String F_METHODS = "methods";
    public static final String F_EO_FIELD_PARAMS = "eoFieldParams";
    public static final String F_FILE_KEY = "fileKey";
    public static final String F_HIBERNATE = "hibernate";
    public static final String F_CACHED = "cached";
    public static final String F_ROW_START = "rowStart";
    public static final String F_VIEW_PARAMS = "viewParams";
    public static final String F_TABLE = "table";
    public static final String F_UPPER_ID = "ID";
    public static final String F_VIEW_KEY = "viewKey";
    public static final String F_EO_PARAMS = "eoParams";
    public static final String F_CUSTOM_FIELD_PARAMS = "customFieldParams";
    public static final String F_FIELD_KEYS = "fieldKeys";
    public static final String F_MODEL_KEY = "modelKey";
    public static final String F_FILTER = "filter";
    public static final String F_ATTRIBUTES = "attributes";
    public static final String F_ROW_END = "rowEnd";
    public static final String F_CALC = "calc";
    public static final String F_MAP_PATH = "mapPath";
    public static final String F_DB_PARAMS = "dbParams";
    public static final String F_PROTOCOL = "protocol";
    public static final String F_CREATE = "create";
    public static final String F_NATURAL_KEYS = "naturalKeys";
    public static final String F_MODEL_KEYS = "modelKeys";
    public static final String F_PACKAGE_PATH = "packagePath";
    public static final String F_ROW_DELIMITER = "rowDelimiter";
    public static final String F_READ = "read";
    public static final String F_DEFAULT_IMPLEMENTATION = "defaultImplementation";
    public static final String F_CUSTOM_PARAMS = "customParams";
    public static final String F_FIELD_KEY = "fieldKey";
    public static final String F_SUPER_KEY = "superKey";
    public static final String F_ID_KEY = "idKey";
    public static final String F_PORT = "port";
    public static final String F_SUB_MODULE = "subModule";
    public static final String F_NAME = "name";
    public static final String F_JOIN_INVERSE = "joinInverse";
    public static final String F_FIELD_NAME = "fieldName";
    public static final String F_ATTRIBUTE_LIST = "attributeList";
    public static final String F_FIELD_DELIMITER = "fieldDelimiter";
    public static final String F_MODEL_CONFIG_KEY = "modelConfigKey";
    public static final String F_DELETE = "delete";
    public static final String F_EXPANDED = "expanded";
    public static final String F_AND = "and";
    public static final String F_SCOPE = "scope";
    public static final String F_PATH_PATTERN = "pathPattern";
    public static final String F_INTERFACES = "interfaces";
    public static final String F_FILE_PATH = "filePath";
    public static final String F_LENGTH = "length";
    public static final String F_MAP_KEY = "mapKey";
    public static final String F_HOST_KEY = "hostKey";
    public static final String F_USER = "user";



    public static final String H_LOCALHOST = "localhost";
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
