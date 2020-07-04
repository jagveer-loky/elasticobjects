package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.JSONSerializationType;

import java.io.File;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.JSON_PATH_TEST;

public class JSONInputReader {

    public static final String BOOLEAN = "Boolean";
    public static final String DATE = "Date";
    public static final String FLOAT = "Float";
    public static final String DOUBLE = "Double";
    public static final String LONG = "Long";
    public static final String INT = "Integer";
    public static final String STRING = "String";
    public static final String NAME = "Name";
    public static final String CONTENT = "Content";
    public static final String ALL = "All";
    public static final String SUB_TEST_LIST = "SubTestList";
    public static final String SUB_TEST_MAP = "SubTestMap";
    public static final String SUB_TEST = "SubTest";
    public static final String BASIC_TEST = "BasicTest";
    public static final String MAP = "Map";
    public static final String LIST = "List";
    public static final String SIMPLE = "Simple";
    public static final String SMALL_WITH_KEYS_AND_LIST = "SmallWithKeysAndList";
    public static final String SMALL_WITH_KEYS = "SmallWithKeys";
    public static final String SMALL = "Small";
    public static final String EMPTY_VALUES = "EmptyValues";
    public static final String EMPTY = "Empty";
    public static final String INPUT_DIR = "../test-resources/src/main/resources/input/json/";

    public final static EO compareInput(final String key, final Object object)  {
        return compareInput(TYPE.MAP, key, object);
    }

    public final static EO compareInput(final TYPE type, final String key, final Object object)  {
        EO eoInput = TestEOProvider
                .createEOBuilder()
                .set(object);
        final String target = inputEOFile(type, key);
        AssertEO.compare(target, eoInput);
        return TestEOProvider
                .createEOBuilder()
                .mapFile(target);
    }

    public final static String readInputJSON(final TYPE type, final String key, JSONSerializationType ser)  {
        final String target = inputEOFile(type, key, ser);
        return AssertBase.readPersisted(new File(target));
    }

    /**
     * @param key
     * @return file content for src/test/resources/input/json/[key].jsn
     * @ on any file read execption
     */
    public final static String readTestInputJSN(final String key)  {
        final String target = JSON_PATH_TEST + key + ".jsn";
        return AssertBase.readPersisted(new File(target));
    }

    /**
     * @param key
     * @return file content for src/test/resources/input/json/[key].json
     * @ on any file read execption
     */
    public final static String readTestInputJSON(final String key)  {
        final String target = JSON_PATH_TEST + key + ".json";
        return AssertBase.readPersisted(new File(target));
    }

    public final static String readInputJSON(final TYPE type, final String key)  {
        return readInputJSON(type, key, JSONSerializationType.STANDARD);
    }

    public final static String readInputJSON(final String key)  {
        return readInputJSON(TYPE.MAP, key, JSONSerializationType.STANDARD);
    }

    public final static String readInputJSN(final TYPE type, final String key)  {
        return readInputJSON(type, key, JSONSerializationType.EO);
    }

    public final static String readInputJSN(final String key)  {
        return readInputJSON(TYPE.MAP, key, JSONSerializationType.EO);
    }

    public final static EO compareInputJSON(final String key, final Object object, final Class... classes)  {
        EO input = TestEOProvider
                .createEOBuilder()
                .setModels(classes)
                .set(object);
        final String target = inputEOFile(TYPE.MAP, key, JSONSerializationType.STANDARD);
        AssertEO.compareJSON(target, input);
        return TestEOProvider.createEOBuilder().mapFile(target);
    }

    public final static EO compareInputJSON(final String key, final EO input)  {
        final String target = inputEOFile(TYPE.MAP, key, JSONSerializationType.STANDARD);
        AssertEO.compareJSON(target, input);
        return TestEOProvider.createEOBuilder().mapFile(target);
    }

    public final static EO compareInputJSON(final String key, final Object object)  {
        EO input = TestEOProvider
                .createEOBuilder()
                .set(object);
        final String target = inputEOFile(TYPE.MAP, key, JSONSerializationType.STANDARD);
        AssertEO.compareJSON(target, input);
        return TestEOProvider.createEOBuilder().mapFile(target);
    }

    public final static EO compareInputJSON(final TYPE type, final String key, final Object object)  {
        EO input = TestEOProvider
                .createEOBuilder()
                .set(object);
        final String target = inputEOFile(type, key, JSONSerializationType.STANDARD);
        AssertEO.compareJSON(target, input);
        return TestEOProvider.createEOBuilder().mapFile(target);
    }

    public final static EO compareInputJSON(final TYPE type, final String key, final EO input)  {
        final String target = inputEOFile(type, key, JSONSerializationType.STANDARD);
        AssertEO.compareJSON(target, input);
        return TestEOProvider.createEOBuilder().mapFile(target);
    }

    public final static EO compareInputJSN(final String key, final EO input)  {
        final String target = inputEOFile(TYPE.MAP, key, JSONSerializationType.EO);
        AssertEO.compareJSN(target, input);
        return TestEOProvider.createEOBuilder().mapFile(target);
    }

    public final static EO compareInputJSN(final TYPE type, final String key, final EO input)  {
        final String target = inputEOFile(type, key, JSONSerializationType.EO);
        AssertEO.compareJSN(target, input);
        return TestEOProvider.createEOBuilder().mapFile(target);
    }

    public final static EO compareInputJSN(final String key, final Object object)  {
        EO input = TestEOProvider
                .createEOBuilder()
                .set(object);
        final String target = inputEOFile(TYPE.MAP, key, JSONSerializationType.EO);
        AssertEO.compareJSN(target, input);
        return TestEOProvider.createEOBuilder().mapFile(target);
    }

    public static final String inputEOFile(final TYPE type, final String key, final JSONSerializationType ser) {
        return INPUT_DIR + type.name() + key + "." + ser.getFileExtension();
    }

    public static final String inputEOFile(final TYPE type, final String key) {
        return INPUT_DIR + type.name() + key + "." + JSONSerializationType.EO.getFileExtension();
    }

    public static final String inputEOFile(final String key) {
        return INPUT_DIR + TYPE.MAP.name() + key + "." + JSONSerializationType.EO.getFileExtension();
    }

    public final static EO compareInput(final String key, final EO input)  {
        final String target = inputEOFile(key);
        AssertEO.compare(target, input);
        return TestEOProvider.createEOBuilder().mapFile(target);
    }

    public final static EO compareInput(final String key, final Object object, final Class... classes)  {
        EO eoInput = TestEOProvider
                .createEOBuilder()
                .setModels(classes)
                .set(object);
        final String target = inputEOFile(key);
        AssertEO.compare(target, eoInput);
        return TestEOProvider
                .createEOBuilder()
                .mapFile(target);
    }

    public final static EO createFromInputTest(final String key)  {
        final String target = inputEOFile(key);
        return TestEOProvider
                .createEOBuilder()
                .mapFile(target);
    }

    public final static EO createFromInputDev(final String key)  {
        final String target = inputEOFile(key);
        return DevObjectProvider
                .createEOBuilder()
                .mapFile(target);
    }

    public final static String readInput(final String key)  {
        final String target = inputEOFile(key);
        return AssertBase.readPersisted(new File(target));
    }

    public enum TYPE {
        MAP, BT, LIST, ST;
    }
}
