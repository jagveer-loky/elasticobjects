package org.fluentcodes.projects.elasticobjects.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.config.Scope;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.eo.JSONToEO;
import org.fluentcodes.projects.elasticobjects.eo.LogLevel;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class TestObjectProvider {

    public static final EOConfigsCache EO_CONFIGS_CACHE = new EOConfigsCache(Scope.TEST);
    private static final Logger LOG = LogManager.getLogger(TestObjectProvider.class);

    public static final EOBuilder createEOBuilder() {
        try {
            return new EOBuilder(EO_CONFIGS_CACHE)
                    .setLogLevel(LogLevel.WARN);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final EOBuilder createEOBuilder(Class... classes) throws Exception {
        return new EOBuilder(EO_CONFIGS_CACHE)
                .setModels(classes)
                .setLogLevel(LogLevel.WARN);
    }

    public static final EO createEOFromJson(Class... classes) throws Exception {
        return new EOBuilder(EO_CONFIGS_CACHE)
                .setModels(classes)
                .setLogLevel(LogLevel.WARN)
                .build();
    }

    public static final JSONToEO createJSONToEOMapEmpty() throws Exception {
        return new JSONToEO("{}", EO_CONFIGS_CACHE);
    }

    public static final JSONToEO createJSONToEOListEmpty() throws Exception {
        return new JSONToEO("[]", EO_CONFIGS_CACHE);
    }

    public static final EO createEOFromJson() throws Exception {
        return createEOBuilder().build();
    }

    public static final EO create(Class... classes) throws Exception {
        try {
            return new EOBuilder(EO_CONFIGS_CACHE)
                    .setModels(classes)
                    .setLogLevel(LogLevel.WARN)
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final static EO create() {
        try {
            return TestObjectProvider.createEOBuilder()
                    .setLogLevel(LogLevel.WARN)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static final ModelInterface findModel(Class modelClass) {
        try {
            return EO_CONFIGS_CACHE
                    .findModel(modelClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final ModelInterface findModel(String modelKey) {
        try {
            return EO_CONFIGS_CACHE
                    .findModel(modelKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void checkLogNotEmpty(EO eo) {
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, eo.getLog().isEmpty());
    }

    public static void checkLogEmpty(EO eo) {
        Assert.assertTrue(INFO_EMPTY_FAILS + eo.getLog(), eo.getLog().isEmpty());
    }

    public static final EO createEOString() throws Exception {
        return createEOBuilder().set(S_STRING);
    }

    public static final EO createEOBoolean() throws Exception {
        return createEOBuilder().set(S_BOOLEAN);
    }

    public static final EO createEOLong() throws Exception {
        return createEOBuilder().set(SAMPLE_LONG);
    }

    public static final EO createEODouble() throws Exception {
        return createEOBuilder().set(SAMPLE_DOUBLE);
    }

    public static final EO createEOMapEmpty() throws Exception {
        return createEOBuilder()
                .setModels(Map.class)
                .set(new LinkedHashMap<>());
    }

    public static final EO createEOListEmpty() throws Exception {
        return createEOBuilder()
                .setModels(List.class)
                .set(new ArrayList<>());
    }

    public static EO createEOFromJson(final String json) throws Exception {
        JSONToEO tokener = new JSONToEO(json, EO_CONFIGS_CACHE);
        return tokener.createChild(createEOFromJson());
    }
}