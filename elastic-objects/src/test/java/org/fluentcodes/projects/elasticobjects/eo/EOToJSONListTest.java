package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.ListProviderJSON;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.test.ListProviderEO;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOToJSONListTest extends TestHelper {
    @Test
    public void withDefault() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = ListProviderEO.create();
        String json = new EOToJSON().toJSON(adapter);
        AssertEO.compare(json);
    }

    @Test
    public void withIndent0() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = ListProviderEO.create();
        String json = new EOToJSON()
                .setStartIndent(0)
                .toJSON(adapter);
        AssertEO.compare(json);
    }

    @Test
    public void withIndent1() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = ListProviderEO.create();
        String json = new EOToJSON()
                .setStartIndent(1)
                .toJSON(adapter);
        AssertEO.compare(json);
    }

    @Test
    public void withIndent2() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = ListProviderEO.create();
        String json = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        AssertEO.compare(json);
    }

    @Test
    public void withEmpty() throws Exception {
        ListProviderEO.createEmpty();
    }

    @Test
    public void withString() throws Exception {
        ListProviderEO.createString();
    }

    @Test
    public void withStringJSON() throws Exception {
        ListProviderJSON.createString();
    }

    @Test
    public void withStringScalarTypes() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = ListProviderEO.createString();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestObjectProvider.createEOBuilder().mapFile(file);
        Assert.assertEquals(S_STRING, fromJson.get(S0));
    }

    @Test
    public void withInteger() throws Exception {
        ListProviderEO.createInteger();
    }

    @Test
    public void withIntegerJSON() throws Exception {
        ListProviderJSON.createInteger();
    }

    @Test
    public void withIntegerScalarTypes() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = ListProviderEO.createInteger();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestObjectProvider.createEOBuilder().mapFile(file);
        Assert.assertEquals(S_INTEGER, fromJson.get(S0));
    }

    @Test
    public void withLong() throws Exception {
        ListProviderEO.createLong();
    }
    @Test
    public void withLongJSON() throws Exception {
        ListProviderJSON.createLong();
    }

    @Test
    public void withLongScalar() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = ListProviderEO.createLong();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestObjectProvider.createEOBuilder().mapFile(file);
        Assert.assertEquals(SAMPLE_LONG, fromJson.get(S0));
    }

    @Test
    public void compareEOFloat() throws Exception {
        ListProviderEO.compareFloat();
    }

    @Test
    public void withFloatScalar() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = ListProviderEO.createFloat();
        final String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        final String file = AssertEO.compare(stringified);
        final EO fromJson = TestObjectProvider
                .createEOBuilder()
                .mapFile(file);
        Assert.assertEquals(SAMPLE_FLOAT, fromJson.get(S0));
    }

    @Test
    public void withDouble() throws Exception {
        ListProviderEO.createDouble();
    }
    @Test
    public void withDoubleJSON() throws Exception {
        ListProviderJSON.createDouble();
    }

    @Test
    public void withDoubleScalar() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = ListProviderEO.createDouble();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestObjectProvider.createEOBuilder().mapFile(file);
        Assert.assertEquals(SAMPLE_DOUBLE, fromJson.get(S0));
    }


    @Test
    public void withDate() throws Exception {
        ListProviderEO.createDate();
    }
    @Test
    public void withDateJSON() throws Exception {
        ListProviderJSON.createDate();
    }

    @Test
    public void withDateScalar() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = ListProviderEO.createDate();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestObjectProvider.createEOBuilder().mapFile(file);
        Assert.assertEquals(SAMPLE_DATE, fromJson.get(S0));
    }

    @Test
    public void withBoolean() throws Exception {
        ListProviderEO.createBoolean();
    }
    @Test
    public void withBooleanJSON() throws Exception {
        ListProviderJSON.createBoolean();
    }

    @Test
    public void withBooleanScalar() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = ListProviderEO.createBoolean();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestObjectProvider.createEOBuilder().mapFile(file);
        Assert.assertEquals(S_BOOLEAN, fromJson.get(S0));
    }

    @Test
    public void withMap() throws Exception {
        ListProviderEO.createMap();
    }
    @Test
    public void withMapJSON() throws Exception {
        ListProviderJSON.createMap();
    }

    @Test
    public void withList() throws Exception {
        ListProviderEO.createList();
    }
    @Test
    public void withListJSON() throws Exception {
        ListProviderJSON.createList();
    }

    @Test
    public void withBasicTest() throws Exception {
        ListProviderEO.createBT();
    }
    @Test
    public void withBasicTestJSON() throws Exception {
        ListProviderJSON.createBT();
    }

    @Test
    public void withSubTest() throws Exception {
        ListProviderEO.createST();
    }
    @Test
    public void withSubTestJSON() throws Exception {
        ListProviderJSON.createST();
    }


    @Test
    public void withSmall() throws Exception {
        ListProviderEO.createSmall();
    }
    @Test
    public void withSmallJSON() throws Exception {
        ListProviderJSON.createSmall();
    }

    @Test
    public void withSimple() throws Exception {
        ListProviderEO.createSimple();
    }
    @Test
    public void withSimpleJSON() throws Exception {
        ListProviderJSON.createSimple();
    }
    
    @Test
    public void withIndentAndSerializationTypeSTANDARD() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = ListProviderEO.create();
        String json = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(adapter);
        AssertEO.compare(json);
    }


    @Test
    public void withAll() throws Exception {
        ListProviderEO.create();
    }
    @Test
    public void withAllJSON() throws Exception {
        ListProviderJSON.create();
    }

    @Test
    public void withIndentAndSerializationTypeSCALAR() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = ListProviderEO.create();
        String json = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        AssertEO.compare(json);
    }
}
