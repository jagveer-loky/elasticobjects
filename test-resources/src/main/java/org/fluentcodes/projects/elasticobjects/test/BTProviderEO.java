package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.junit.Assert;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class BTProviderEO {
    public static EOBuilder builder() {
        return TestEOProvider.createEOBuilder();
    }

    public static EO createEmpty() throws Exception {
        final EO eo = builder().map(BTProvider.createEmpty());
        assertEmpty(eo);
        return eo;
    }

    public static void assertEmpty(final EO eo) throws Exception {
    }

    public static EO createString() throws Exception {
        final EO eo = builder().map(BTProvider.createString());
        assertString(eo);
        return eo;
    }

    public static void assertString(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
    }

    public static EO createInteger() throws Exception {
        final EO eo = builder().map(BTProvider.createInteger());
        assertInteger(eo);
        return eo;
    }

    public static void assertInteger(final EO eo) throws Exception {
        Assert.assertEquals(S_INTEGER, eo.get(F_TEST_INTEGER));
    }

    public static EO createLong() throws Exception {
        final EO eo = builder().map(BTProvider.createLong());
        assertLong(eo);
        return eo;
    }

    public static void assertLong(final EO eo) throws Exception {
        Assert.assertEquals(SAMPLE_LONG, eo.get(F_TEST_LONG));
    }

    public static EO createFloat() throws Exception {
        final EO eo = builder().map(BTProvider.createFloat());
        assertFloat(eo);
        return eo;
    }

    public static void assertFloat(final EO eo) throws Exception {
        Assert.assertEquals(SAMPLE_FLOAT, eo.get(F_TEST_FLOAT));
    }

    public static EO createDouble() throws Exception {
        final EO eo = builder().map(BTProvider.createDouble());
        assertDouble(eo);
        return eo;
    }

    public static void assertDouble(final EO eo) throws Exception {
        Assert.assertEquals(SAMPLE_DOUBLE, eo.get(F_TEST_DOUBLE));
    }

    public static EO createDate() throws Exception {
        final EO eo = builder().map(BTProvider.createDate());
        assertDate(eo);
        return eo;
    }

    public static void assertDate(final EO eo) throws Exception {
        Assert.assertEquals(SAMPLE_DATE, eo.get(F_TEST_DATE));
    }

    public static EO createBoolean() throws Exception {
        final EO eo = builder().map(BTProvider.createBoolean());
        assertBoolean(eo);
        return eo;
    }

    public static void assertBoolean(final EO eo) throws Exception {
        Assert.assertEquals(S_BOOLEAN, eo.get(F_TEST_BOOLEAN));
    }

    public static EO createMap() throws Exception {
        final EO eo = builder().map(BTProvider.createMap());
        assertMap(eo);
        return eo;
    }

    public static void assertMap(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(F_UNTYPED_MAP + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eo.get(F_UNTYPED_MAP + Path.DELIMITER + F_TEST_INTEGER));
    }

    public static EO createList() throws Exception {
        final EO eo = builder().map(BTProvider.createList());
        assertList(eo);
        return eo;
    }

    public static void assertList(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(F_UNTYPED_LIST + Path.DELIMITER + S0));
        Assert.assertEquals(S_INTEGER, eo.get(F_UNTYPED_LIST + Path.DELIMITER + S1));
    }

    public static EO createBT() throws Exception {
        final EO eo = builder().map(BTProvider.createBT());
        assertBT(eo);
        return eo;
    }

    public static void assertBT(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(F_BASIC_TEST + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eo.get(F_BASIC_TEST + Path.DELIMITER + F_TEST_INTEGER));
    }

    public static EO createST() throws Exception {
        final EO eo = builder().map(BTProvider.createST());
        assertST(eo);
        return eo;
    }

    public static void assertST(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(F_SUB_TEST + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_STRING_OTHER, eo.get(F_SUB_TEST + Path.DELIMITER + EO_STATIC.F_NAME));
    }

    public static EO createMapST() throws Exception {
        final EO eo = builder().map(BTProvider.createMapST());
        assertMapST(eo);
        return eo;
    }

    public static void assertMapST(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(F_SUB_TEST_MAP + Path.DELIMITER + S_KEY0 + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_STRING_OTHER, eo.get(F_SUB_TEST_MAP + Path.DELIMITER + S_KEY0 + Path.DELIMITER + EO_STATIC.F_NAME));
    }

    public static EO createListST() throws Exception {
        final EO eo = builder().map(BTProvider.createListST());
        assertListST(eo);
        return eo;
    }

    public static void assertListST(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(F_SUB_TEST_LIST + Path.DELIMITER + S0 + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_STRING_OTHER, eo.get(F_SUB_TEST_LIST + Path.DELIMITER + S0 + Path.DELIMITER + EO_STATIC.F_NAME));

    }

    public static EO createSimple() throws Exception {
        final EO eo = builder().map(BTProvider.createSimple());
        assertSimple(eo);
        return eo;
    }

    public static void assertSimple(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eo.get(F_TEST_INTEGER));
        Assert.assertEquals(SAMPLE_LONG, eo.get(F_TEST_LONG));
        Assert.assertEquals(SAMPLE_FLOAT, eo.get(F_TEST_FLOAT));
        Assert.assertEquals(SAMPLE_DOUBLE, eo.get(F_TEST_DOUBLE));
        Assert.assertEquals(SAMPLE_DATE, eo.get(F_TEST_DATE));
        Assert.assertEquals(S_BOOLEAN, eo.get(F_TEST_BOOLEAN));
    }

    public static EO createSmall() throws Exception {
        final EO eo = builder().map(BTProvider.createSmall());
        assertSmall(eo);
        return eo;
    }

    public static void assertSmall(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eo.get(F_TEST_INTEGER));
    }

    public static EO create() throws Exception {
        final EO eo = builder().map(BTProvider.create());
        asserts(eo);
        return eo;
    }

    public static void asserts(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eo.get(F_TEST_INTEGER));
        Assert.assertEquals(SAMPLE_LONG, eo.get(F_TEST_LONG));
        Assert.assertEquals(SAMPLE_FLOAT, eo.get(F_TEST_FLOAT));
        Assert.assertEquals(SAMPLE_DOUBLE, eo.get(F_TEST_DOUBLE));
        Assert.assertEquals(SAMPLE_DATE, eo.get(F_TEST_DATE));
        Assert.assertEquals(S_BOOLEAN, eo.get(F_TEST_BOOLEAN));
    }
}