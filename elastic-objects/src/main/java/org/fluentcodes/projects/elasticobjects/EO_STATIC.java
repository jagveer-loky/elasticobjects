package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.paths.Path;

/**
 * Created 9.6.2018
 */

public class EO_STATIC {
    public static final String MODULE_SHORT = "EO";
    public static final String ELASTIC_OBJECTS = "elastic-objects";
    public static final String MODULE_NAME = ELASTIC_OBJECTS;

    public static final String _PARENT_KEY = "_parentKey";
    public static final String _VALUE = "_value";
    public static final String S_VALUE = "value";

    public static final String DYNAMIC_PATTERN = "$[";

    public static final String MAIN = "main";
    public static final String BUILD = "build";
    public static final String TEST = "test";

    public static final String CP_CALLS = "org.fluentcodes.projects.elasticobjects.calls";
    public static final String CP_STATICS = "org.fluentcodes.projects.elasticobjects.executor.statics";
    public static final String CP_CONFIG = "org.fluentcodes.projects.elasticobjects.config";
    public static final String STRING = "String";
    public static final String MODULE = "Module";
    public static final String A_CONFIG_PLACEHOLDER = "$[config]";
    public static final String GENERIC_CONFIG = "GenericConfig";
    public static final String FILE = "File";
    public static final String MODEL = "Model";
    public static final String FIELD = "Field";
    public static final String USER = "User";
    public static final String ROLE = "Role";
    public static final String CONFIG = "Config";
    public static final String CALL = "Call";
    public static final String TEMPLATE = "Template";
    public static final String VALUE = "Value";
    public static final String JSON = "Json";
    public static final String A_VALUES_SET = "set";
    public static final String A_VALUES_MAP = "map";
    public static final String A_VALUES = "values";
    public static final String A_VALUES_MAP_EXE = "ValueCall.map(empty)";
    public static final String A_VALUES_SET_EXE = "ValueCall.set(empty)";
    public static final String A_VALUES_TEMPLATE_EXE = "TemplateCall.execute(content)";
    public static final String A_TEMPLATE_KEY = "templateKey";
    public static final String HOST = "Host";

    public static final String A_IF = "if";
    public static final String A_IF_LOOP = "if-loop";
    public static final String A_KEEP = "keep";
    public static final String A_EXECUTE = "execute";
    public static final String A_PREFIX = "prefix";
    public static final String POSTFIX = "postfix";
    public static final String A_CONFIG = "config";

    public static final String CONFIG_JSON_END = ".json";
    public static final String CONFIG_PATH_MAIN_SIMPLE = "src/main/resources";
    public static final String CONFIG_PATH_TEST_SIMPLE = "src/test/resources";
    public static final String CONFIG_PATH_MAIN = CONFIG_PATH_MAIN_SIMPLE + Path.DELIMITER;
    public static final String CONFIG_PATH_TEST = CONFIG_PATH_TEST_SIMPLE + Path.DELIMITER;

    public static final String CONFIG_MODEL = MODEL + CONFIG;
    public static final String CONFIG_MODEL_FILE = CONFIG_MODEL + CONFIG_JSON_END;
    public static final String CONFIG_MODEL_MAIN = CONFIG_PATH_MAIN + CONFIG_MODEL_FILE;

    public static final String CONFIG_FIELD = FIELD + CONFIG;
    public static final String CONFIG_FIELD_FILE = CONFIG_FIELD + CONFIG_JSON_END;
    public static final String CONFIG_FIELD_MAIN = CONFIG_PATH_MAIN + CONFIG_FIELD_FILE;

    public static final String CONFIG_HOST = HOST + CONFIG;
    public static final String CONFIG_HOST_FILE = CONFIG_HOST + CONFIG_JSON_END;
    public static final String CONFIG_HOST_MAIN = CONFIG_PATH_MAIN +  CONFIG_HOST_FILE;
    public static final String CONFIG_HOST_TEST = CONFIG_PATH_TEST + CONFIG_HOST_FILE;

    public static final String CONFIG_JSON = JSON + CONFIG;
    public static final String CONFIG_JSON_FILE = CONFIG_JSON + CONFIG_JSON_END;
    public static final String CONFIG_JSON_MAIN = CONFIG_PATH_MAIN +  CONFIG_JSON_FILE;
    public static final String CONFIG_JSON_TEST = CONFIG_PATH_TEST + CONFIG_JSON_FILE;

    public static final String CONFIG_CONFIG = CONFIG + CONFIG;
    public static final String CONFIG_CONFIG_FILE = CONFIG_CONFIG + CONFIG_JSON_END;
    public static final String CONFIG_CONFIG_MAIN = CONFIG_PATH_MAIN +  CONFIG_CONFIG_FILE;
    public static final String CONFIG_CONFIG_TEST = CONFIG_PATH_TEST + CONFIG_CONFIG_FILE;

    public static final String CONFIG_FILE = FILE + CONFIG;
    public static final String CONFIG_FILE_FILE = CONFIG_FILE + CONFIG_JSON_END;
    public static final String CONFIG_FILE_MAIN = CONFIG_PATH_MAIN + CONFIG_FILE_FILE;
    public static final String CONFIG_FILE_TEST = CONFIG_PATH_TEST + CONFIG_FILE_FILE;

    public static final String CONFIG_TEMPLATE = TEMPLATE + CONFIG;
    public static final String CONFIG_TEMPLATE_FILE = CONFIG_TEMPLATE + CONFIG_JSON_END;
    public static final String CONFIG_TEMPLATE_MAIN = CONFIG_PATH_MAIN + CONFIG_TEMPLATE_FILE;
    public static final String CONFIG_TEMPLATE_TEST = CONFIG_PATH_TEST + CONFIG_TEMPLATE_FILE;

    public static final String CONFIG_VALUE = VALUE + CONFIG;
    public static final String CONFIG_VALUE_FILE = CONFIG_VALUE + CONFIG_JSON_END;
    public static final String CONFIG_VALUE_TEST = CONFIG_PATH_TEST + CONFIG_VALUE_FILE;

    public static final String CONFIG_MODEL_PREFIX = "M_";
    public static final String CONFIG_FIELD_PREFIX = "F_";
    public static final String CONFIG_TEMPLATE_PREFIX = "T_";

    public static final String F_INSERT = "insert";
    public static final String F_INDENT = "indent";
    public static final String F_CONFIG_CLASS = "configClass";
    public static final String F_CONTENT = "content";
    public static final String F_SERIALIZATION_TYPE = "serializationType";
    public static final String F_TO_SERIALIZE = "toSerialize";
    public static final String F_PROVIDE_IN_ACTION = "provideInAction";
    public static final String F_DEFAULT_VALUE = "defaultValue";

    public static final String M_VALUE_CALL = "ValueCall";
    //<call templateKey="StaticValuesLoop.tpl" config:="Model" prefix:="M_" keep="JAVA">

    public static final String M_LINKED_HASH_SET = "LinkedHashSet";
    public static final String M_DBFIELD_PARAMS = "DBFieldParams";
    public static final String M_VIEW_PARAMS = "ViewParams";
    public static final String M_EXECUTOR_ACTION = "CallExecutor";
    public static final String M_OR = "Or";
    public static final String M_VIEW_FIELD_PARAMS = "ViewFieldParams";
    public static final String M_HASH_MAP = "HashMap";
    public static final String M_FIELD_CONFIG = "FieldConfig";
    public static final String M_ARRAY_LIST = "ArrayList";
    public static final String M_STRING = "String";
    public static final String M_LIST_PARAMS = "ListParams";
    public static final String M_LINKED_HASH_MAP = "LinkedHashMap";
    public static final String M_LIKE = "Like";
    public static final String M_VALUE_CONFIG = "ValueConfig";
    public static final String M_LIST = "List";
    public static final String M_MODELS_LIST = "Models";
    public static final String M_BOOLEAN = "Boolean";
    public static final String M_EOPARAMS = "EOParams";
    public static final String M_SHORT = "Short";
    public static final String M_DBPARAMS = "DBParams";
    public static final String M_HASH_SET = "HashSet";
    public static final String M_VALUES_CONTENT = "ValuesContent";
    public static final String M_SHAPE_TYPES = "ShapeTypes";
    public static final String M_EXECUTOR_IMPL = "ExecutorImpl";
    public static final String M_EXECUTOR_LIST = "ExecutorList";
    public static final String M_MODEL_CONFIG_SET = "ModelConfigSet";
    public static final String M_ASSET_CONFIG = "ConfigIO";
    public static final String M_USER_CONFIG = "UserConfig";
    public static final String M_ASSET_ACTION = "CallIO";
    public static final String M_DOUBLE = "Double";
    public static final String M_MODEL_CONFIG_LIST = "ModelConfigList";
    public static final String M_MODEL_CONFIG_MAP = "ModelConfigMap";
    public static final String M_FLOAT = "Float";
    public static final String M_CONFIG_ACTION = "ConfigCall";
    public static final String M_EXECUTOR_VALUES = "ExecutorValues";
    public static final String M_AND = "And";
    public static final String M_MODEL_INTERFACE = "ModelInterface";
    public static final String M_LONG = "Long";
    public static final String M_CLASS = "Class";
    public static final String M_OBJECT = "Object";
    public static final String M_ARRAYS = "Arrays";
    public static final String M_MAP = "Map";
    public static final String M_VALUE_ACTION = "ValueCall";
    public static final String M_EXECUTOR_LIST_TEMPLATE = "ExecutorListTemplate";
    public static final String M_ACTION = "Call";
    public static final String M_JSON_ACTION = "JsonCall";
    public static final String M_ROLE_PERMISSIONS = "RolePermissions";
    public static final String M_SET = "Set";
    public static final String M_STRING_BUILDER = "StringBuilder";
    public static final String M_CONFIG = "Config";
    public static final String M_JSON_CONFIG = "JsonConfig";
    public static final String M_MODEL_CONFIG_NONE = "ModelConfigNone";
    public static final String M_EQ = "Eq";
    public static final String M_VALUES_MATH = "ValuesMath";
    public static final String M_MODEL_CONFIG = "ModelConfig";
    public static final String M_PATH_PATTERN = "PathPattern";
    public static final String M_EXECUTOR = "Executor";
    public static final String M_ROLE_CONFIG = "RoleConfig";
    public static final String M_LIST_MAPPER = "ListMapper";
    public static final String M_VALUES_CONTENT_HTML = "ValuesContentHtml";
    public static final String M_CONFIG_CONFIG = "ConfigConfig";
    public static final String M_HOST_CONFIG = "HostConfig";
    public static final String M_MODEL_IMPL = "ModelImpl";
    public static final String M_LIST_CONFIG = "ListConfig";
    public static final String M_EOFIELD_PARAMS = "EOFieldParams";
    public static final String M_PASSWORD = "Password";
    public static final String M_CONDITION = "Condition";
    public static final String M_PATH = "Path";
    public static final String M_FILE_CONFIG = "FileConfig";
    public static final String M_LOGGING_OBJECTS_IMPL = "LoggingObjectsImpl";
    public static final String M_TEMPLATE_CONFIG = "TemplateConfig";
    public static final String M_VALUES_MISC = "ValuesMisc";
    public static final String M_VALUE_PARAMS_HELPER = "ValueParamsHelper";
    public static final String M_DATE = "Date";
    public static final String M_INTEGER = "Integer";
    public static final String M_STRING_BUFFER = "StringBuffer";
    public static final String M_SCOPE = "Scope";
    public static final String M_TEMPLATE_ACTION = "TemplateCall";
    public static final String M_MODEL = "Model";
    public static final String M_LIST_ACTION = "ListCall";
    public static final String M_MODEL_CONFIG_SCALAR = "ModelConfigScalar";
    public static final String M_EOBUILDER = "EOBuilder";
    public static final String M_MODEL_CONFIG_OBJECT = "ModelConfigObject";
    public static final String M_HOST_ACTION = "HostCall";
    public static final String M_FILE_ACTION = "FileCall";
    public static final String M_CONFIG_IMPL = "ConfigImpl";
//</call>
    public static final String F_DO_MAP = "doMap";
    public static final String F_UPPER_ID_KEY = "upper.ID";
    //<call templateKey="StaticValuesLoop.tpl" config:="Field" prefix:="F_" keep="JAVA">

    public static final String F_PATH_PATTERN_AS_STRING = "pathPatternAsString";
    public static final String F_FILE_NAME = "fileName";
    public static final String F_NATURAL_ID = "naturalId";
    public static final String F_LIST_MAPPER = "listMapper";
    public static final String F_PATH = "path";
    public static final String F_PASSWORD = "password";
    public static final String F_PACKAGE_GROUP = "packageGroup";
    public static final String F_DB_FIELD_PARAMS = "dbFieldParams";
    public static final String F_ROLE_PERMISSIONS = "rolePermissions";
    public static final String F_ACTION = "call";
    public static final String F_MODEL = "model";
    public static final String F_ID = "id";
    public static final String F_SHORT = "short";
    public static final String F_JOIN = "join";
    public static final String F_COL_KEYS = "colKeys";
    public static final String F_WRITE = "write";
    public static final String F_AND_AS_STRING = "andAsString";
    public static final String F_MODELS = "models";
    public static final String F_HIBERNATE_ANNOTATIONS = "hibernateAnnotations";
    public static final String F_NOT_NULL = "notNull";
    public static final String F_MODULE = "module";
    public static final String F_EXECUTE = "execute";
    public static final String F_IGNORE_HEADER = "ignoreHeader";
    public static final String F_FILTER_SUB_MODULE = "filterSubModule";
    public static final String F_SHAPE_TYPE = "shapeType";
    public static final String F_UNIQUE = "unique";
    public static final String F_INPUT_KEY = "inputKey";
    public static final String F_VIEW_FIELD_PARAMS = "viewFieldParams";
    public static final String F_WHERE_AS_STRING = "whereAsString";
    public static final String F_TEMPLATE = "template";
    public static final String F_HOST_NAME = "hostName";
    public static final String F_LOG = "log";
    public static final String F_ROW_HEAD = "rowHead";
    public static final String F_METHODS = "methods";
    public static final String F_EO_FIELD_PARAMS = "eoFieldParams";
    public static final String F_EOPARAMS_CREATE = "create";
    public static final String F_FILE_KEY = "fileKey";
    public static final String F_HIBERNATE = "hibernate";
    public static final String F_COL_KEYS_AS_STRING = "colKeysAsString";
    public static final String F_LIST_PARAMS = "listParams";
    public static final String F_PERMISSIONS_CREATE = "create";
    public static final String F_CACHED = "cached";
    public static final String F_ROW_START = "rowStart";
    public static final String F_VIEW_PARAMS = "viewParams";
    public static final String F_KEY = "key";
    public static final String F_SCS_KEY = "scsKey";
    public static final String F_TABLE = "table";
    public static final String F_UPPER_ID = "ID";
    public static final String F_LOOP_PATH = "loopPath";
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
    public static final String F_ACTION_MAP = "actionMap";
    public static final String F_CREATE = "create";
    public static final String F_NATURAL_KEYS = "naturalKeys";
    public static final String F_MODEL_KEYS = "modelKeys";
    public static final String F_INFO = "info";
    public static final String F_PACKAGE_PATH = "packagePath";
    public static final String F_ROW_DELIMITER = "rowDelimiter";
    public static final String F_READ = "read";
    public static final String F_DEFAULT_IMPLEMENTATION = "defaultImplementation";
    public static final String F_FILTER_MODULE = "filterModule";
    public static final String F_CUSTOM_PARAMS = "customParams";
    public static final String F_FIELD_KEY = "fieldKey";
    public static final String F_AUTHOR = "author";
    public static final String F_SUPER_KEY = "superKey";
    public static final String F_MODELS_LIST = "modelsList";
    public static final String F_CREATION_DATE = "creationDate";
    public static final String F_USER_KEY = "userKey";
    public static final String F_FILTER_KEY = "filterKey";
    public static final String F_CONFIG_KEY = "configKey";
    public static final String F_ENTRIES = "entries";
    public static final String F_ID_KEY = "idKey";
    public static final String F_PORT = "port";
    public static final String F_JSON_KEY = "jsonKey";
    public static final String F_SUB_MODULE = "subModule";
    public static final String F_NAME = "name";
    public static final String F_FIELDS = "fields";
    public static final String F_JOIN_INVERSE = "joinInverse";
    public static final String F_OR_AS_STRING = "orAsString";
    public static final String F_FIELD_NAME = "fieldName";
    public static final String F_ATTRIBUTE_LIST = "attributeList";
    public static final String F_ORIGIN = "origin";
    public static final String F_ROLES = "roles";
    public static final String F_DESCRIPTION = "description";
    public static final String F_FIELD_DELIMITER = "fieldDelimiter";
    public static final String F_MODEL_CONFIG_KEY = "modelConfigKey";
    public static final String F_DELETE = "delete";
    public static final String F_EXPANDED = "expanded";
    public static final String F_AND = "and";
    public static final String F_SCOPE = "scope";
    public static final String F_PATH_PATTERN = "pathPattern";
    public static final String F_VALUE_KEY = "valueKey";
    public static final String F_VALUE = "value";
    public static final String F_SERIALIZED = "serialized";
    public static final String F_INTERFACES = "interfaces";
    public static final String F_OR = "or";
    public static final String F_FILE_PATH = "filePath";
    public static final String F_VALUE_AS_STRING = "valueAsString";
    public static final String F_LENGTH = "length";
    public static final String F_MAP_KEY = "mapKey";
    public static final String F_HOST_KEY = "hostKey";
    public static final String F_USER_NAME = "userName";
    public static final String F_FILTER_CONFIG_CLASS = "filterConfigClass";
    public static final String F_FILTER_CONFIG_NAME = "filterConfigName";
    public static final String F_USER = "user";
    public static final String F_TEMPLATE_KEY = "templateKey";
//</call>

    //<call templateKey="StaticValuesLoop.tpl" config:="Host" prefix:="H_" keep="JAVA">

    public static final String H_LOCALHOST = "localhost";
    public static final String H_LOCAL = "local";
//</call>
    //<call templateKey="StaticValuesLoop.tpl" config:="File" prefix:="FILE_" keep="JAVA">

    public static final String FILE_EO_STATIC_JAVA = "EO_STATIC.java";
    public static final String FILE_EO_STATIC_TEST_JAVA = "EO_STATIC_TEST.java";
    public static final String FILE_EO_STATIC_BUILD_JAVA = "EO_STATIC_BUILD.java";
//</call>
    //<call templateKey="StaticValuesLoop.tpl" config:="Json" prefix:="J_" keep="JAVA">

    public static final String J_CONTENT = "content";
    public static final String J_MODULE_CONFIG_JSON = "ModuleConfig.json";
//</call>
    //<call templateKey="StaticValuesLoop.tpl" config:="Template" prefix:="T_" keep="JAVA">

    public static final String T_CONTENT = "content";
    public static final String CON_NEWLINE = "\n";
    public static final String CON_TAB = "\t";
    public static final String CON_SEMICOLON = ";";
    public static final String CON_SPACE = " ";

//</call>


}
