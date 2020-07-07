package org.fluentcodes.projects.elasticobjects.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EORoot;
import org.fluentcodes.projects.elasticobjects.eo.JSONToEO;
import org.fluentcodes.projects.elasticobjects.eo.LogLevel;
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

    public static final EO createEO(Class... classes)  {
        return new EORoot(TestEOProvider.EO_CONFIGS, LogLevel.WARN, classes);
    }

    public static final EO createEO()  {
        return new EORoot(TestEOProvider.EO_CONFIGS);
    }

    public static final EO createEO(Object value)  {
        return new EORoot(TestEOProvider.EO_CONFIGS, value);
    }

    public static final EO createEOString()  {
        return TestEOProvider.create(S_STRING);
    }

    public static final EO createEOBoolean()  {
        return TestEOProvider.create(S_BOOLEAN);
    }
}