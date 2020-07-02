package org.fluentcodes.projects.elasticobjects.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.JSONToEO;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class TestObjectProvider {

    private static final Logger LOG = LogManager.getLogger(TestObjectProvider.class);


    public static final ModelInterface findModel(Class modelClass) {
        try {
            return TestEOProvider.EO_CONFIGS
                    .findModel(modelClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final ModelInterface findModel(String modelKey) {
        try {
            return TestEOProvider.EO_CONFIGS
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
        return TestEOProvider.createEOBuilder().set(S_STRING);
    }

    public static final EO createEOBoolean() throws Exception {
        return TestEOProvider.createEOBuilder().set(S_BOOLEAN);
    }

    public static final EO createEOLong() throws Exception {
        return TestEOProvider.createEOBuilder().set(SAMPLE_LONG);
    }

    public static final EO createEODouble() throws Exception {
        return TestEOProvider.createEOBuilder().set(SAMPLE_DOUBLE);
    }

    public static final EO createEOMapEmpty() throws Exception {
        return TestEOProvider.createEOBuilder()
                .setModels(Map.class)
                .set(new LinkedHashMap<>());
    }

    public static final EO createEOListEmpty() throws Exception {
        return TestEOProvider.createEOBuilder()
                .setModels(List.class)
                .set(new ArrayList<>());
    }

    public static EO createEOFromJson(final String json) throws Exception {
        JSONToEO tokener = new JSONToEO(json, TestEOProvider.EO_CONFIGS);
        return tokener.createChild(TestEOProvider.createEmptyMap());
    }
}