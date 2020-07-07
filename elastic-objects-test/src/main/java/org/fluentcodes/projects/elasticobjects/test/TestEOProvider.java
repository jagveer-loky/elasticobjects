package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.Scope;
import org.fluentcodes.projects.elasticobjects.eo.*;
import org.junit.Assert;

import java.io.File;

public class TestEOProvider {
    public static final EOConfigsCache EO_CONFIGS = new EOConfigsCache(Scope.TEST);

    public static final EO createWithClasses(Class... classes) {
        try {
            return new EORoot(EO_CONFIGS, classes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final EO create() {
        try {
            return new EORoot(EO_CONFIGS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final EO create(Object value) {
        try {
            return new EORoot(EO_CONFIGS, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final JSONToEO createJSONToEOMapEmpty()  {
        return new JSONToEO("{}", EO_CONFIGS);
    }

    public static final EO assertEOSerialized(EO eo)  {
        String fileName = AssertEO.compare(eo);
        Assert.assertTrue(eo.getLog().isEmpty());
        eo.executeCalls();
        Assert.assertTrue(eo.getLog().isEmpty());
        EO eoFromSerialization = new EORoot(EO_CONFIGS, new File(fileName));
        AssertEO.compare(eoFromSerialization);
        Assert.assertTrue(eoFromSerialization.getLog().isEmpty());
        return eoFromSerialization;
    }
}
