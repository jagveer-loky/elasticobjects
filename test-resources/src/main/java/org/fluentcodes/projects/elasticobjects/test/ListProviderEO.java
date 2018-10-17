package org.fluentcodes.projects.elasticobjects.test;
import java.util.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.test.JSONInputReader.*;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.junit.Assert;

public class ListProviderEO {
    public static EOBuilder builder() {
        return TestObjectProvider.createEOBuilder();
    }

    public static EO createEmpty() throws Exception {
        final EO eoEmpty = builder()
                .setModels(List.class)
                .map(ListProvider.createEmpty());
        assertEmpty(eoEmpty);
        return eoEmpty;
    }

    public static void assertEmpty(final EO eoEmpty) {
        Assert.assertEquals(List.class, eoEmpty.getModelClass());
    }


    public static EO createString() throws Exception {
        EO eoString = builder()
                .setModels(List.class)
                .map(ListProvider.createString());
        assertString(eoString);
        return eoString;
    }

    public static void assertString(final EO eoString) throws Exception{
        Assert.assertEquals(S_STRING, eoString.get(S0));
    }

    public static EO createInteger() throws Exception {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createInteger());
        assertInteger(fromJson);
        return fromJson;
    }

    public static void assertInteger(final EO eoInteger) throws Exception{
        Assert.assertEquals(S_INTEGER, eoInteger.get(S0));
    }

    public static EO createLong() throws Exception {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createLong());
        assertLong(fromJson);
        return fromJson;
    }

    public static void assertLong(final EO eoLong) throws Exception{
        Assert.assertEquals(SAMPLE_LONG, eoLong.get(S0));
    }

    public static EO compareFloat() throws Exception {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createFloat());
        assertFloat(fromJson);
        return fromJson;
    }

    public static void assertFloat(final EO eoFloat) throws Exception{
        Assert.assertEquals(SAMPLE_FLOAT, eoFloat.get(S0));
    }

    public static EO createFloat() throws Exception {
        return compareFloat();
    }

    public static EO createDouble() throws Exception {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createDouble());
        assertDouble(fromJson);
        return fromJson;
    }

    public static void assertDouble(final EO eoDouble) throws Exception{
        Assert.assertEquals(SAMPLE_DOUBLE, eoDouble.get(S0));
    }

    public static EO createDate() throws Exception {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createDate());
        assertDate(fromJson);
        return fromJson;
    }

    public static void assertDate(final EO eoDate) throws Exception{
        Assert.assertEquals(SAMPLE_DATE, eoDate.get(S0));
    }

    public static EO createBoolean() throws Exception {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createBoolean());
        assertBoolean(fromJson);
        return fromJson;
    }

    public static void assertBoolean(final EO eoBoolean) throws Exception{
        Assert.assertEquals(S_BOOLEAN, eoBoolean.get(S0));
    }

    public static EO createMap() throws Exception {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createMap());
        assertMap(fromJson);
        return fromJson;
    }

    public static void assertMap(final EO eoMap) throws Exception{
        Assert.assertEquals(S_STRING, eoMap.get(S0 + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoMap.get(S0 + Path.DELIMITER + F_TEST_INTEGER));
    }

    public static EO createList() throws Exception {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createList());
        assertList(fromJson);
        return fromJson;
    }

    public static void assertList(final EO fromJson) throws Exception{
        Assert.assertEquals(S_STRING, fromJson.get(S0 + Path.DELIMITER + S0));
        Assert.assertEquals(S_INTEGER, fromJson.get(S0 + Path.DELIMITER + S1));
    }

    public static EO createBT() throws Exception {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createBT());
        assertBT(fromJson);
        return fromJson;
    }

    public static void assertBT(final EO fromJson)throws Exception {
        Assert.assertEquals(S_STRING, fromJson.get(S0 + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, fromJson.get(S0 + Path.DELIMITER + F_TEST_INTEGER));
    }

    public static EO createST() throws Exception {
        EO fromJson =builder()
                .setModels(List.class)
                .map(ListProvider.createST());
        assertST(fromJson);
        return fromJson;
    }

    public static void assertST(final EO fromJson) throws Exception{
        Assert.assertEquals(S_STRING, fromJson.get(S0 + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_STRING_OTHER, fromJson.get(S0 + Path.DELIMITER + EO_STATIC.F_NAME));
    }

    public static EO createSmall() throws Exception {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createSmall());
        assertSmall(fromJson);
        return fromJson;
    }

    public static void assertSmall(final EO fromJson) throws Exception {
        Assert.assertEquals(S_STRING, fromJson.get(S0));
        Assert.assertEquals(S_INTEGER, fromJson.get(S1));
    }

    public static EO createSimple() throws Exception {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.createSimple());
        assertSimple(fromJson);
        return fromJson;
    }

    public static void assertSimple(final EO fromJson) throws Exception{
        Assert.assertEquals(S_STRING, fromJson.get(S0));
        Assert.assertEquals(S_INTEGER, fromJson.get(S1));
        Assert.assertEquals(SAMPLE_LONG, fromJson.get(S2));
        Assert.assertEquals(SAMPLE_FLOAT, fromJson.get(S3));
        Assert.assertEquals(SAMPLE_DOUBLE, fromJson.get(S4));
        Assert.assertEquals(SAMPLE_DATE, fromJson.get(S5));
        Assert.assertEquals(S_BOOLEAN, fromJson.get(S6));
    }

    public static EO create() throws Exception {
        EO fromJson = builder()
                .setModels(List.class)
                .map(ListProvider.create());
        asserts(fromJson);
        return fromJson;
    }

    public static void asserts(final EO fromJson) throws Exception{
        Assert.assertEquals(S_STRING, fromJson.get(S0));
        Assert.assertEquals(S_INTEGER, fromJson.get(S1));
        Assert.assertEquals(SAMPLE_LONG, fromJson.get(S2));
        Assert.assertEquals(SAMPLE_FLOAT, fromJson.get(S3));
        Assert.assertEquals(SAMPLE_DOUBLE, fromJson.get(S4));
        Assert.assertEquals(SAMPLE_DATE, fromJson.get(S5));
        Assert.assertEquals(S_BOOLEAN, fromJson.get(S6));
    }

}