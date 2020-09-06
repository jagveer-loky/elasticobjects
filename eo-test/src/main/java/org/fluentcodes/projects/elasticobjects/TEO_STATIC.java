package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.condition.And;
import org.fluentcodes.projects.elasticobjects.calls.condition.Condition;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListMapper;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

public class TEO_STATIC {
    public static final String S_EMPTY = "";

    public static final String FILE_SOURCE_TXT = "FileTest.txt";
    public static final String PATH_INPUT = "input/";
    public static final String PATH_INPUT_JSON = PATH_INPUT + "json/";

    public static final String FILE_TMP_JSON = "target.json";
    public static final String F_SUB_TEST = "subTest";

    public static final String M_BASIC_TEST = "BasicTest";
    public static final String M_SUB_TEST = "SubTest";

    public static final String S_VALUE11 = "value11";
    public static final String S_VALUE12 = "value12";
    public static final String S_VALUE21 = "value21";
    public static final String S_VALUE22 = "value22";
    public static final String CON_COMMA = ",";

    public static final String INFO_EXPECTED_EXCEPTION = "Expected Exception: ";
    public static final String INFO_EXPECTED_EXCEPTION_FAILS = "Expected Exception was not thrown";
    public static final String INFO_EXPECTED_NO_EXCEPTION = "An Exception should be thrown! ";

    public static final String INFO_CONDITION_TRUE_FAILS = "Expected condition result is true but is false: ";
    public static final String INFO_CONDITION_FALSE_FAILS = "Expected condition result is false but is true: ";

    public static final String INFO_EMPTY_FAILS = "Log not empty but expected empty! ";
    public static final String INFO_NOT_EMPTY_FAILS = "Log expected not empty but is empty! ";

    public static final String INFO_LOG_EMPTY_FAILS = "Log not empty but expected empty! ";
    public static final String INFO_LOG_NOT_EMPTY_FAILS = "Log empty but expected NOT empty! ";

    public static final String INFO_NOT_NULL_FAILS = "Value is null! ";

    public static final String INFO_COMPARE_FAILS = "Result compare fails! ";

    public static final String S0 = "0";
    public static final String S1 = "1";
    public static final String S2 = "2";
    public static final String S3 = "3";

    public static final String S_DATE_STRING = "Tue Jun 07 08:16:55 CEST 2016";
    public static final Date SAMPLE_DATE = ScalarConverter.toDate(S_DATE_STRING);
    public static final long SAMPLE_DATE_LONG = SAMPLE_DATE.getTime();
    public static final Boolean S_BOOLEAN = true;
    public static final Integer S_INTEGER = 1;

    public static final Long SAMPLE_LONG = new Long(2);
    public static final Float SAMPLE_FLOAT = new Float(1.1);
    public static final Double SAMPLE_DOUBLE = 2.2;
    public static final String SAMPLE_KEY_UNKNOW = "unknow ";
    public static final Map SAMPLE_MAP_EMPTY = new HashMap();
    public static final String S_MESSAGE = "message";
    public static final String S_STRING = "test";
    public static final String S_STRING_OTHER = "testOther";
    public static final List SAMPLE_LIST_EMPTY = new ArrayList();
    public static final String S_KEY = "key";
    public static final String S_KEY0 = "key0";
    public static final String S_KEY1 = "key1";
    public static final String S_KEY2 = "key2";

    public static final String S_LEVEL0 = "level0";
    public static final String S_LEVEL1 = "level1";
    public static final String S_LEVEL2 = "level2";
    public static final String S_LEVEL3 = "level3";
    public static final String S_LEVEL4 = "level4";
    public static final String S_LEVEL5 = "level5";

    public static final String S_PATH1 = S_LEVEL0 + Path.DELIMITER + S_LEVEL1;
    public static final String S_PATH2 = S_PATH1 + Path.DELIMITER + S_LEVEL2;

    public static final String S_TEST_STRING = "string";

    public static final String R_SUPER_ADMIN = "superadmin";
    public static final String R_GUEST = "guest";
    public static final String R_ANONYM = "anonym";
    public static final String R_TEST_ROLE_READ = "testRoleRead";
    public static final String R_TEST_ROLE_WRITE = "testRoleWrite";
    public static final String R_TEST_ROLE_CREATE = "testRoleCreate";
    public static final String R_TEST_ROLE_DELETE = "testRoleDelete";
    public static final String R_TEST_ROLE_EXECUTE = "testRoleExecute";

    public static final String join(String delimiter, String... keyValues) {
        if (keyValues == null || keyValues.length == 0) {
            return "";
        }
        return String.join(delimiter, keyValues);
    }

    public static final String toEq(String... keyValues) {
        return join(CON_SPACE + Condition.EQ + CON_SPACE, keyValues);
    }

    public static final String toLike(String... keyValues) {
        return join(CON_SPACE + Condition.LIKE + CON_SPACE, keyValues);
    }

    public static final String toAnd(String... keyValues) {
        return join(CON_SPACE + And.DELIMITER + CON_SPACE, keyValues);
    }

    public static final String toOr(String... keyValues) {
        return join(CON_SPACE + Or.DELIMITER + CON_SPACE, keyValues);
    }

    public static final Map<String, Object> createListMapperMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(ListMapper.COL_KEYS, ListMapper.COL_KEYS);
        map.put(ListMapper.DO_MAP, S_BOOLEAN);
        map.put(ListMapper.IGNORE_HEADER, S_BOOLEAN);
        map.put(ListMapper.MAP_PATH, ListMapper.MAP_PATH);
        map.put(F_MODEL_KEYS, join(CON_COMMA, List.class.getSimpleName(), Map.class.getSimpleName()));
        map.put(F_PATH_PATTERN, F_PATH_PATTERN);
        return map;
    }


}
