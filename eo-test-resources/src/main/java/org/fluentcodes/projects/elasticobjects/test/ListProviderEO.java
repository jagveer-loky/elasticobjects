package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.junit.Assert;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class ListProviderEO {
    public static EOBuilder builder() {
        return TestEOProvider.createEOBuilder();
    }

    public static EO createEmpty()  {
        final EO eoEmpty = builder()
                .setModels(List.class)
                .map(ListProvider.createEmpty());
        assertEmpty(eoEmpty);
        return eoEmpty;
    }

    public static void assertEmpty(final EO eoEmpty) {
        Assert.assertEquals(List.class, eoEmpty.getModelClass());
    }


    public static EO createString()  {
        EO eoString = builder()
                .setModels(List.class)
                .map(ListProvider.createString());
        assertString(eoString);
        return eoString;
    }

    public static void assertString(final EO eoString)  {
        Assert.assertEquals(S_STRING, eoString.get(S0));
    }

    public static EO createInteger()  {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createInteger());
        assertInteger(fromJson);
        return fromJson;
    }

    public static void assertInteger(final EO eoInteger)  {
        Assert.assertEquals(S_INTEGER, eoInteger.get(S0));
    }

    public static EO createLong()  {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createLong());
        assertLong(fromJson);
        return fromJson;
    }

    public static void assertLong(final EO eoLong)  {
        Assert.assertEquals(SAMPLE_LONG, eoLong.get(S0));
    }

    public static EO compareFloat()  {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createFloat());
        assertFloat(fromJson);
        return fromJson;
    }

    public static void assertFloat(final EO eoFloat)  {
        Assert.assertEquals(SAMPLE_FLOAT, eoFloat.get(S0));
    }

    public static EO createFloat()  {
        return compareFloat();
    }

    public static EO createDouble()  {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createDouble());
        assertDouble(fromJson);
        return fromJson;
    }

    public static void assertDouble(final EO eoDouble)  {
        Assert.assertEquals(SAMPLE_DOUBLE, eoDouble.get(S0));
    }

    public static EO createDate()  {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createDate());
        assertDate(fromJson);
        return fromJson;
    }

    public static void assertDate(final EO eoDate)  {
        Assert.assertEquals(SAMPLE_DATE, eoDate.get(S0));
    }

    public static EO createBoolean()  {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createBoolean());
        assertBoolean(fromJson);
        return fromJson;
    }

    public static void assertBoolean(final EO eoBoolean)  {
        Assert.assertEquals(S_BOOLEAN, eoBoolean.get(S0));
    }

    public static EO createMap()  {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createMap());
        assertMap(fromJson);
        return fromJson;
    }

    public static void assertMap(final EO eoMap)  {
        Assert.assertEquals(S_STRING, eoMap.get(S0 + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoMap.get(S0 + Path.DELIMITER + F_TEST_INTEGER));
    }

    public static EO createList()  {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createList());
        assertList(fromJson);
        return fromJson;
    }

    public static void assertList(final EO fromJson)  {
        Assert.assertEquals(S_STRING, fromJson.get(S0 + Path.DELIMITER + S0));
        Assert.assertEquals(S_INTEGER, fromJson.get(S0 + Path.DELIMITER + S1));
    }

    public static EO createBT()  {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createBT());
        assertBT(fromJson);
        return fromJson;
    }

    public static void assertBT(final EO fromJson)  {
        Assert.assertEquals(S_STRING, fromJson.get(S0 + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, fromJson.get(S0 + Path.DELIMITER + F_TEST_INTEGER));
    }

    public static EO createST()  {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createST());
        assertST(fromJson);
        return fromJson;
    }

    public static void assertST(final EO fromJson)  {
        Assert.assertEquals(S_STRING, fromJson.get(S0 + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_STRING_OTHER, fromJson.get(S0 + Path.DELIMITER + EO_STATIC.F_NAME));
    }

    public static EO createSmall()  {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createSmall());
        assertSmall(fromJson);
        return fromJson;
    }

    public static void assertSmall(final EO fromJson)  {
        Assert.assertEquals(S_STRING, fromJson.get(S0));
        Assert.assertEquals(S_INTEGER, fromJson.get(S1));
    }

    public static EO createSimple()  {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createSimple());
        assertSimple(fromJson);
        return fromJson;
    }

    public static void assertSimple(final EO fromJson)  {
        Assert.assertEquals(S_STRING, fromJson.get(S0));
        Assert.assertEquals(S_INTEGER, fromJson.get(S1));
        Assert.assertEquals(SAMPLE_LONG, fromJson.get(S2));
        Assert.assertEquals(SAMPLE_FLOAT, fromJson.get(S3));
        Assert.assertEquals(SAMPLE_DOUBLE, fromJson.get(S4));
        Assert.assertEquals(SAMPLE_DATE, fromJson.get(S5));
        Assert.assertEquals(S_BOOLEAN, fromJson.get(S6));
    }

    public static EO create()  {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.create());
        asserts(fromJson);
        return fromJson;
    }

    public static void asserts(final EO fromJson)  {
        Assert.assertEquals(S_STRING, fromJson.get(S0));
        Assert.assertEquals(S_INTEGER, fromJson.get(S1));
        Assert.assertEquals(SAMPLE_LONG, fromJson.get(S2));
        Assert.assertEquals(SAMPLE_FLOAT, fromJson.get(S3));
        Assert.assertEquals(SAMPLE_DOUBLE, fromJson.get(S4));
        Assert.assertEquals(SAMPLE_DATE, fromJson.get(S5));
        Assert.assertEquals(S_BOOLEAN, fromJson.get(S6));
    }

}