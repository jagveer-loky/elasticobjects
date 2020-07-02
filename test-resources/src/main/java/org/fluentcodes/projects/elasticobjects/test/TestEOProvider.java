package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.Scope;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.eo.JSONToEO;
import org.fluentcodes.projects.elasticobjects.eo.LogLevel;
import org.junit.Assert;

public class TestEOProvider {
    public static final EOConfigsCache EO_CONFIGS = new EOConfigsCache(Scope.TEST);

    public static final EO createEmptyMap() throws Exception {
        return createEOBuilder().build();
    }

    public static final EO create(Class... classes) {
        try {
            return createEOBuilder(classes)
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final static EO create() {
        try {
            return createEOBuilder()
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final JSONToEO createJSONToEOMapEmpty() throws Exception {
        return new JSONToEO("{}", EO_CONFIGS);
    }

    public static final JSONToEO createJSONToEOListEmpty() throws Exception {
        return new JSONToEO("[]", EO_CONFIGS);
    }

    public static final EO createEOFromJson(Class... classes) throws Exception {
        return createEOBuilder(classes)
                .build();
    }

    public static final EOBuilder createEOBuilder() {
        try {
            return new EOBuilder(EO_CONFIGS)
                    .setLogLevel(LogLevel.WARN);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final EOBuilder createEOBuilder(Class... classes) throws Exception {
        return new EOBuilder(EO_CONFIGS)
                .setModels(classes)
                .setLogLevel(LogLevel.WARN);
    }

    public static final EO assertEOSerialized(EO eo) throws Exception {
        String fileName = AssertEO.compare(eo);
        Assert.assertTrue(eo.getLog().isEmpty());
        eo.executeCalls();
        Assert.assertTrue(eo.getLog().isEmpty());
        EO eoFromSerialization = new EOBuilder(EO_CONFIGS).mapFile(fileName);
        AssertEO.compare(eoFromSerialization);
        Assert.assertTrue(eoFromSerialization.getLog().isEmpty());
        return eoFromSerialization;
    }
}
