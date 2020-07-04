package org.fluentcodes.projects.elasticobjects.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.Scope;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.eo.JSONToEO;
import org.fluentcodes.projects.elasticobjects.eo.LogLevel;
import org.fluentcodes.projects.elasticobjects.test.JSONInputReader.TYPE;
import org.junit.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class DevObjectProvider {
    public static final EOConfigsCache EO_CONFIGS = new EOConfigsCache(Scope.DEV);
    private static final Logger LOG = LogManager.getLogger(DevObjectProvider.class);

    public static final EOBuilder createEOBuilder() {
        try {
            return new EOBuilder(EO_CONFIGS)
                    .setLogLevel(LogLevel.WARN);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final EOBuilder createEOBuilder(Class... classes)  {
        return new EOBuilder(EO_CONFIGS)
                .setModels(classes)
                .setLogLevel(LogLevel.WARN);
    }

    public static final EO createEO(Class... classes)  {
        return new EOBuilder(EO_CONFIGS)
                .setModels(classes)
                .setLogLevel(LogLevel.WARN)
                .build();
    }

    public static final EO createEO()  {
        return createEOBuilder().build();
    }

    public static final EO createEOString()  {
        return createEOBuilder().set(S_STRING);
    }

    public static final EO createEOBoolean()  {
        return createEOBuilder().set(S_BOOLEAN);
    }

    public static final EO createEOLong()  {
        return createEOBuilder().set(SAMPLE_LONG);
    }

    public static final EO createEODouble()  {
        return createEOBuilder().set(SAMPLE_DOUBLE);
    }

    public static final EO createEOMapEmpty()  {
        return createEOBuilder()
                .setModels(Map.class)
                .set(new LinkedHashMap<>());
    }

    public static final EO createEOMapString()  {
        return createEOMapStringPath(F_TEST_STRING);
    }

    public static final EO createEOMapStringLevel0()  {
        return createEOMapStringPath(toPath(S_LEVEL0, F_TEST_STRING));
    }

    public static final EO createEOMapStringLevel1()  {
        return createEOMapStringPath(toPath(S_LEVEL0, S_LEVEL1, F_TEST_STRING));
    }

    public static final EO createEOMapStringLevel2()  {
        return createEOMapStringPath(toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2, F_TEST_STRING));
    }

    public static final EO createEOMapStringPath(final String path)  {
        final EO child = createEOBuilder()
                .setPath(path)
                .set(S_STRING);
        final EO root = child.getRoot();
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, root.get(path));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get());
        return root;
    }

    public static EO createEOFromJson(final String json)  {
        JSONToEO tokener = new JSONToEO(json, EO_CONFIGS);
        return tokener.createChild(createEO());
    }


    public static EO createEOFromTestJsonFile(final String jsonFile)  {
        final String json = JSONInputReader.readTestInputJSON(jsonFile);
        return createEOFromJson(json);

    }

    public static EO createEOFromJsonKeyMap(final String jsonKey)  {
        final String json = JSONInputReader.readInputJSON(jsonKey);
        return createEOFromJson(json);
    }

    public static EO createEOFromJsonListKey(final String jsonKey)  {
        final String json = JSONInputReader.readInputJSON(TYPE.LIST, jsonKey);
        return createEOFromJson(json);
    }

    public static EO createEOFromJsonMapKey(final String jsonKey)  {
        final String json = JSONInputReader.readInputJSON(jsonKey);
        return createEOFromJson(json);
    }

}