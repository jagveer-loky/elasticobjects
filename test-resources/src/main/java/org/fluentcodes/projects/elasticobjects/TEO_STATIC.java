package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.condition.And;
import org.fluentcodes.projects.elasticobjects.condition.Condition;
import org.fluentcodes.projects.elasticobjects.condition.Or;
import org.fluentcodes.projects.elasticobjects.config.Scope;
import org.fluentcodes.projects.elasticobjects.config.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.MapProvider;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

public class TEO_STATIC {
    public static final String S_EMPTY = "";

    public static final String FILE_SOURCE_TXT = "source.txt";
    public static final String FILE_SOURCE_NAME_TXT = "input/file/source.txt";
    public static final String CS_SOURCE_CSV = "source.csv";
    public static final String CS_SOURCE_CSV_NAME = "input/csv/source.csv";
    public static final String CS_TARGET_CSV = "target.csv";
    public static final String FILE_TARGET_TXT = "target.txt";
    public static final String FILE_SOURCE_CACHED_TXT = "sourceCached.txt";
    public static final String FILE_SOURCE_CACHED_TXT_NAME = "input/file/sourceCached.txt";
    public static final String FILE_LOCALHOST_SOURCE_TXT = "localhost:source.txt";
    public static final String FILE_SOURCE_JSON = "source.json";
    public static final String FILE_CLASSPATH_SOURCE_TXT = "classpath_source.txt";
    public static final String FILE_TARGET_JSON = "target.json";

    public static final String MODULE_NAME="test-resources";
    public static final String F_SUB_TEST = "subTest";
    public static final String F_BASIC_TEST = "basicTest";
    public static final String F_TEST_STRING = "testString";
    public static final String F_TEST_DATE = "testDate";
    public static final String F_TEST_BOOLEAN = "testBoolean";
    public static final String F_TEST_INTEGER = "testInt";
    public static final String F_TEST_DOUBLE = "testDouble";
    public static final String F_TEST_FLOAT = "testFloat";
    public static final String F_TEST_LONG = "testLong";
    public static final String F_TEST_OBJECT = "testObject";
    public static final String F_UNTYPED_LIST = "untypedList";
    public static final String F_UNTYPED_MAP = "untypedMap";
    public static final String F_BASIC_TEST_MAP = "BasicTest.json";
    public static final String F_BASIC_TEST_LIST = "data/BasicTest.json";
    public static final String F_SUB_TEST_MAP = "subTestMap";
    public static final String F_SUB_TEST_LIST = "subTestList";

    public static final String M_BASIC_TEST = "BasicTest";
    public static final String M_SUB_TEST = "SubTest";

    public static final String S_VALUE11 = "value11";
    public static final String S_VALUE12 = "value12";
    public static final String S_VALUE21 = "value21";
    public static final String S_VALUE22 = "value22";

    public static final String S_ROW_DELIMITER = CON_NEWLINE;
    public static final String S_FIELD_DELIMITER = CON_SEMICOLON;
    public static final String CON_COMMA = ",";
    public static final String CON_COLON = ":";
    public static final String CON_DOT = ".";
    public static final String CON_LIKE = " like ";
    public static final String CON_SET = "set";
    public static final String CON_GET = "find";


    public static final String DB_H2_MEM_BASIC = "h2:mem:basic";
    public static final String DB_H2_FILE_BASIC = "h2:file:basic";
    public static final String DB_H2_MEM_BASIC_TEST = "h2:mem:BasicTest";
    public static final String DB_H2_MEM_SUB_TEST = "h2:file:SubTest";

    public static final String INFO_EXPECTED_EXCEPTION = "Expected Exception: ";
    public static final String INFO_EXPECTED_EXCEPTION_FAILS = "Expected Exception was not thrown";
    public static final String INFO_EXPECTED_NO_EXCEPTION = "An Exception should be thrown! ";

    public static final String INFO_CONDITION_TRUE_FAILS = "Expected condition result is true but is false: ";
    public static final String INFO_CONDITION_FALSE_FAILS = "Expected condition result is false but is true: ";

    public static final String INFO_EMPTY_FAILS = "Log not empty but expected empty! ";
    public static final String INFO_NOT_EMPTY_FAILS = "Log expected not empty but is empty! ";

    public static final String INFO_LOG_EMPTY_FAILS = "Log not empty but expected empty! ";
    public static final String INFO_LOG_NOT_EMPTY_FAILS = "Log empty but expected NOT empty! ";

    public static final String INFO_NULL_FAILS = "Expected null fails! ";
    public static final String INFO_NOT_NULL_FAILS = "Value is null! ";

    public static final String INFO_CONTAINS_FAILS = "Result does not contain item! ";
    public static final String INFO_NOT_CONTAINS_FAILS = "Result contain item! ";
    public static final String INFO_SIZE_FAILS = "Result has wrong size! ";
    public static final String INFO_COMPARE_FAILS = "Result compare fails! ";

    public static final String S0 = "0";
    public static final String S1 = "1";
    public static final String S2 = "2";
    public static final String S3 = "3";
    public static final String S4 = "4";
    public static final String S5 = "5";
    public static final String S6 = "6";

    public static final String S_DATE_STRING = "Tue Jun 07 08:16:55 CEST 2016";
    public static final Date SAMPLE_DATE = ScalarConverter.toDate(S_DATE_STRING);
    public static final long SAMPLE_DATE_LONG = SAMPLE_DATE.getTime();
    public static final Boolean S_BOOLEAN = true;
    public static final Integer S_INTEGER = 1;

    public static final Long SAMPLE_LONG = new Long(2);
    public static final Float SAMPLE_FLOAT = new Float(1.1);
    public static final Double SAMPLE_DOUBLE = 2.2;
    public static final String SAMPLE_CONTENT = "content";
    public static final String SAMPLE_KEY_UNKNOW = "unknow ";
    public static final Map SAMPLE_MAP_EMPTY = new HashMap();
    public static final String S_MESSAGE = "message";
    public static final String S_STRING = "test";
    public static final String S_STRING_OTHER = "testOther";
    public static final String S_STRING_OVERRIDE = "testOverride";
    public static final List SAMPLE_LIST_EMPTY = new ArrayList();
    public static final String SAMPLE_STRING_JSON_MAP_EMPTY = "{}";
    public static final String S_KEY = "key";
    public static final String S_KEY0 = "key0";
    public static final String S_KEY1 = "key1";
    public static final String S_KEY2 = "key2";
    public static final String S_KEY3 = "key3";
    public static final String S_KEY4 = "key4";

    public static final String S_KEY_NOT_EXISTING = "NotExisting";
    public static final String S_KEY_OVERRIDE = "keyOverride";

    public static final String S_LEVELMAP = "levelMap";

    public static final String S_LEVEL0 = "level0";
    public static final String S_LEVEL1 = "level1";
    public static final String S_LEVEL2 = "level2";
    public static final String S_LEVEL3 = "level3";
    public static final String S_LEVEL4 = "level4";
    public static final String S_LEVEL5 = "level5";
    public static final String S_LEVEL6 = "level6";
    public static final String S_LEVEL7 = "level7";

    public static final String S__LEVEL0 = Path.DELIMITER + S_LEVEL0;
    public static final String S_LEVEL0_LEVELMAP = S_LEVEL0 + Path.DELIMITER + S_LEVELMAP;

    public static final String S_PATH_DYNAMIC = S_LEVEL0 + Path.DELIMITER + "$[key]";
    public static final String S_PATH1 = S_LEVEL0 + Path.DELIMITER + S_LEVEL1;
    public static final String S_PATH2 = S_PATH1 + Path.DELIMITER + S_LEVEL2;
    public static final String S_PATH3 = S_PATH2 + Path.DELIMITER + S_LEVEL3;
    public static final String S_PATH4 = S_PATH3 + Path.DELIMITER + S_LEVEL4;
    public static final String S_PATH5 = S_PATH4 + Path.DELIMITER + S_LEVEL5;
    public static final String S_PATH6 = S_PATH5 + Path.DELIMITER + S_LEVEL6;
    public static final String S_PATH7 = S_PATH6 + Path.DELIMITER + S_LEVEL7;
    public static final String S_PATH00 = toPath(S0,S0);
    public static final String S_PATH0_KEY1 = toPath(S0,S_KEY1);
    public static final String S_PATH0_KEY2 = toPath(S0,S_KEY2);
    public static final String S_PATH1_KEY1 = toPath(S1,S_KEY1);
    public static final String S_PATH1_KEY2 = toPath(S1,S_KEY2);

    public static final String PCK_CONFIG = "config";
    public static final String PACK_ROOT = "org.fluentcodes.projects.elasticobjects";
    public static final String PACK_CONFIG = PACK_ROOT + "." + PCK_CONFIG;

    public static final String AUTHOR0 = "Werner Diwischek";

    public static final String S_KEY_PATH = "keyPath";
    public static final String S_KEY_MAP = "keyMap";
    public static final String S_TEST_STRING = "string";
    public static final String S_KEY_BOOLEAN = "boolean";
    public static final String S_KEY_DATE = "date";
    public static final String S_KEY_FLOAT = "float";
    public static final String S_KEY_DOUBLE = "double";
    public static final String S_KEY_INTEGER = "int";
    public static final String S_KEY_LONG = "long";
    public static final String S_KEYLIST = "list";

    public static final String R_ADMIN = "adminRole";
    public static final String R_SUPER_ADMIN = "superadminRole";
    public static final String R_GUEST = "guest";
    public static final String R_ANONYM = "anonym";
    public static final String R_NOTHING = "nothing";
    public static final String R_TEST_ROLE_READ = "testRoleRead";
    public static final String R_TEST_ROLE_WRITE = "testRoleWrite";
    public static final String R_TEST_ROLE_CREATE = "testRoleCreate";
    public static final String R_TEST_ROLE_DELETE = "testRoleDelete";
    public static final String R_TEST_ROLE_EXECUTE = "testRoleExecute";
    //</call>

    public static final String FILE_BASIC_TEST_SIMPLE = "BasicTestSimple.json";

    public static final String join(String delimiter, String... keyValues) {
        if (keyValues == null || keyValues.length == 0) {
            return "";
        }
        return String.join(delimiter, keyValues);
    }

    public static final String toPath(String... keyValues) {
        return join(Path.DELIMITER, keyValues);
    }
    public static final String toEq(String... keyValues) {
        return join(CON_SPACE + Condition.EQ + CON_SPACE, keyValues);
    }
    public static final String toLike(String... keyValues) {
        return join(CON_SPACE + Condition.LIKE + CON_SPACE, keyValues);
    }

    public static final String toEx(String... keyValues) {
        return join(CON_SPACE + Condition.EX + CON_SPACE, keyValues);
    }

    public static final String toAnd(String... keyValues) {
        return join(CON_SPACE + And.DELIMITER + CON_SPACE, keyValues);
    }
    public static final String toOr(String... keyValues) {
        return join(CON_SPACE + Or.DELIMITER + CON_SPACE, keyValues);
    }

    public static final Map<String, Object> createListMapperMap() {
      Map<String, Object> map = new HashMap<>();
      map.put(F_COL_KEYS, F_COL_KEYS);
      map.put(F_DO_MAP, S_BOOLEAN);
      map.put(F_IGNORE_HEADER, S_BOOLEAN);
      map.put(F_MAP_PATH, F_MAP_PATH);
      map.put(F_MODEL_KEYS, join(CON_COMMA,List.class.getSimpleName(),Map.class.getSimpleName()));
      map.put(F_PATH_PATTERN, F_PATH_PATTERN);
      return map;
    }

    public static final Map<String, Object> createListParamsMap() throws Exception{
      Map<String, Object> map = MapProvider.toMap(
              F_LENGTH, 1,
              F_ROW_HEAD, 2,
              F_ROW_START, 3,
              F_ROW_END, 4,
              F_FILTER, toEq(S4, S_INTEGER.toString()));
      return map;
    }

}
