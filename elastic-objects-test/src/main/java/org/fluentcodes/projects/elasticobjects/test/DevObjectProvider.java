package org.fluentcodes.projects.elasticobjects.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.Scope;
import org.fluentcodes.projects.elasticobjects.eo.*;
import org.fluentcodes.projects.elasticobjects.test.JSONInputReader.TYPE;
import org.junit.Assert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class DevObjectProvider {
    public static final EOConfigsCache EO_CONFIGS = new EOConfigsCache(Scope.DEV);
    private static final Logger LOG = LogManager.getLogger(DevObjectProvider.class);

    public static final EO createEO(Class... classes)  {
        return new EORoot(EO_CONFIGS, LogLevel.WARN, classes);
    }

    public static final EO createEO()  {
        return new EORoot(EO_CONFIGS);
    }

    public static final EO createEO(Object value)  {
        return new EORoot(EO_CONFIGS, value);
    }
    public static final EO createEOMapStringLevel0()  {
        return createEOMapStringPath(toPath(S_LEVEL0, F_TEST_STRING));
    }
    public static final EO createEOMapStringLevel1()  {
        return createEOMapStringPath(toPath(S_LEVEL0, S_LEVEL1, F_TEST_STRING));
    }
    public static final EO createEOMapStringPath(final String path)  {
        final EO child = createEO().setPathValue(path, S_STRING);
        final EO root = child.getRoot();
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, root.get(path));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get());
        return root;
    }

    public static EO createEOFromJson(final String json)  {
        JSONToEO tokener = new JSONToEO(json, EO_CONFIGS);
        return tokener.createChild(createEO());
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