package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.junit.Assert;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.test.JSONInputReader.*;

/**
 * Provider for different SubTest objects with persistence directory input/json/ and ST* prefix
 * @since 07.09.2018
 */
public class STProviderEO {
    public static EOBuilder builder() {
        return TestObjectProvider.createEOBuilder();
    }

    public static EO createEmpty() throws Exception {
        final EO eo =  builder().map(STProvider.createEmpty());
        assertEmpty(eo);
        return eo;
    }

    public static void assertEmpty(final EO eo) throws Exception {
        Assert.assertEquals(SubTest.class, eo.getModelClass());
    }

    public static EO createString() throws Exception {
        final EO eo =  builder().map(STProvider.createString());
        assertString(eo);
        return eo;
    }

    public static void assertString(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
    }


    public static EO createName() throws Exception {
        final EO eo =  builder().map(STProvider.createName());
        assertName(eo);
        return eo;
    }

    public static void assertName(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING_OTHER, eo.get(EO_STATIC.F_NAME));
    }


    public static EO createST() throws Exception {
        final EO eo =  builder().map(STProvider.createST());
        assertST(eo);
        return eo;
    }

    public static void assertST(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING_OTHER, eo.get(F_SUB_TEST + Path.DELIMITER + EO_STATIC.F_NAME));
    }


    public static EO createSimple() throws Exception {
        final EO eo =  builder().map(STProvider.createSimple());
        assertSimple(eo);
        return eo;
    }

    public static void assertSimple(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(S_STRING_OTHER, eo.get(EO_STATIC.F_NAME));
    }


    public static EO create() throws Exception {
        final EO eo =  builder().map(STProvider.create());
        asserts(eo);
        return eo;
    }

    public static void asserts(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(S_STRING_OTHER, eo.get(EO_STATIC.F_NAME));
    }


    /**
     * creates a recursive path for 'subTest'
     * @param i number of loops...
     * @return the path
     */
    public static String createSTPath(int i) {
        if (i == 1) {
            return F_SUB_TEST;
        }
        if (i>1) {
            return F_SUB_TEST + Path.DELIMITER + createSTPath(i-1);
        }
        return "";
    }

}