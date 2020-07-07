package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.eo.EO;

import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.junit.Assert;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class BTProviderEO {
    public static EO createEmpty()  {
        final EO eo = TestEOProvider.create(BTProvider.createEmpty());
        assertEmpty(eo);
        return eo;
    }

    public static void assertEmpty(final EO eo)  {
    }

    public static EO createString()  {
        final EO eo = TestEOProvider.create(BTProvider.createString());
        assertString(eo);
        return eo;
    }

    public static void assertString(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
    }

    public static EO createInteger()  {
        final EO eo = TestEOProvider.create(BTProvider.createInteger());
        assertInteger(eo);
        return eo;
    }

    public static void assertInteger(final EO eo)  {
        Assert.assertEquals(S_INTEGER, eo.get(F_TEST_INTEGER));
    }

    public static EO createLong()  {
        final EO eo = TestEOProvider.create(BTProvider.createLong());
        assertLong(eo);
        return eo;
    }

    public static void assertLong(final EO eo)  {
        Assert.assertEquals(SAMPLE_LONG, eo.get(F_TEST_LONG));
    }

    public static EO createFloat()  {
        final EO eo = TestEOProvider.create(BTProvider.createFloat());
        assertFloat(eo);
        return eo;
    }

    public static void assertFloat(final EO eo)  {
        Assert.assertEquals(SAMPLE_FLOAT, eo.get(F_TEST_FLOAT));
    }

    public static EO createDouble()  {
        final EO eo = TestEOProvider.create(BTProvider.createDouble());
        assertDouble(eo);
        return eo;
    }

    public static void assertDouble(final EO eo)  {
        Assert.assertEquals(SAMPLE_DOUBLE, eo.get(F_TEST_DOUBLE));
    }

    public static EO createDate()  {
        final EO eo = TestEOProvider.create(BTProvider.createDate());
        assertDate(eo);
        return eo;
    }

    public static void assertDate(final EO eo)  {
        Assert.assertEquals(SAMPLE_DATE, eo.get(F_TEST_DATE));
    }

    public static EO createBoolean()  {
        final EO eo = TestEOProvider.create(BTProvider.createBoolean());
        assertBoolean(eo);
        return eo;
    }

    public static void assertBoolean(final EO eo)  {
        Assert.assertEquals(S_BOOLEAN, eo.get(F_TEST_BOOLEAN));
    }

    public static EO createMap()  {
        final EO eo = TestEOProvider.create(BTProvider.createMap());
        assertMap(eo);
        return eo;
    }

    public static void assertMap(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_UNTYPED_MAP + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eo.get(F_UNTYPED_MAP + Path.DELIMITER + F_TEST_INTEGER));
    }

    public static EO createList()  {
        final EO eo = TestEOProvider.create(BTProvider.createList());
        assertList(eo);
        return eo;
    }

    public static void assertList(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_UNTYPED_LIST + Path.DELIMITER + S0));
        Assert.assertEquals(S_INTEGER, eo.get(F_UNTYPED_LIST + Path.DELIMITER + S1));
    }

    public static EO createBT()  {
        final EO eo = TestEOProvider.create(BTProvider.createBT());
        assertBT(eo);
        return eo;
    }

    public static void assertBT(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_BASIC_TEST + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eo.get(F_BASIC_TEST + Path.DELIMITER + F_TEST_INTEGER));
    }

    public static EO createST()  {
        final EO eo = TestEOProvider.create(BTProvider.createST());
        assertST(eo);
        return eo;
    }

    public static void assertST(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_SUB_TEST + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_STRING_OTHER, eo.get(F_SUB_TEST + Path.DELIMITER + EO_STATIC.F_NAME));
    }

    public static EO createMapST()  {
        final EO eo = TestEOProvider.create(BTProvider.createMapST());
        assertMapST(eo);
        return eo;
    }

    public static void assertMapST(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_SUB_TEST_MAP + Path.DELIMITER + S_KEY0 + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_STRING_OTHER, eo.get(F_SUB_TEST_MAP + Path.DELIMITER + S_KEY0 + Path.DELIMITER + EO_STATIC.F_NAME));
    }

    public static EO createListST()  {
        final EO eo = TestEOProvider.create(BTProvider.createListST());
        assertListST(eo);
        return eo;
    }

    public static void assertListST(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_SUB_TEST_LIST + Path.DELIMITER + S0 + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_STRING_OTHER, eo.get(F_SUB_TEST_LIST + Path.DELIMITER + S0 + Path.DELIMITER + EO_STATIC.F_NAME));

    }

    public static EO createSimple()  {
        final EO eo = TestEOProvider.create(BTProvider.createSimple());
        assertSimple(eo);
        return eo;
    }

    public static void assertSimple(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eo.get(F_TEST_INTEGER));
        Assert.assertEquals(SAMPLE_LONG, eo.get(F_TEST_LONG));
        Assert.assertEquals(SAMPLE_FLOAT, eo.get(F_TEST_FLOAT));
        Assert.assertEquals(SAMPLE_DOUBLE, eo.get(F_TEST_DOUBLE));
        Assert.assertEquals(SAMPLE_DATE, eo.get(F_TEST_DATE));
        Assert.assertEquals(S_BOOLEAN, eo.get(F_TEST_BOOLEAN));
    }

    public static EO createSmall()  {
        final EO eo = TestEOProvider.create(BTProvider.createSmall());
        assertSmall(eo);
        return eo;
    }

    public static void assertSmall(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eo.get(F_TEST_INTEGER));
    }

    public static EO create()  {
        final EO eo = TestEOProvider.create(BTProvider.create());
        asserts(eo);
        return eo;
    }

    public static void asserts(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eo.get(F_TEST_INTEGER));
        Assert.assertEquals(SAMPLE_LONG, eo.get(F_TEST_LONG));
        Assert.assertEquals(SAMPLE_FLOAT, eo.get(F_TEST_FLOAT));
        Assert.assertEquals(SAMPLE_DOUBLE, eo.get(F_TEST_DOUBLE));
        Assert.assertEquals(SAMPLE_DATE, eo.get(F_TEST_DATE));
        Assert.assertEquals(S_BOOLEAN, eo.get(F_TEST_BOOLEAN));
    }
}