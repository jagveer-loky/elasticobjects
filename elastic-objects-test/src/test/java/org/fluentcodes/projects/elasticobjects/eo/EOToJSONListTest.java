package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOToJSONListTest extends TestHelper {
    @Test
    public void withDefault()  {
        TestHelper.printStartMethod();
        EO adapter = ListProviderEO.create();
        String json = new EOToJSON().toJSON(adapter);
        AssertEO.compare(json);
    }

    @Test
    public void withIndent0()  {
        TestHelper.printStartMethod();
        EO adapter = ListProviderEO.create();
        String json = new EOToJSON()
                .setStartIndent(0)
                .toJSON(adapter);
        AssertEO.compare(json);
    }

    @Test
    public void withIndent1()  {
        TestHelper.printStartMethod();
        EO adapter = ListProviderEO.create();
        String json = new EOToJSON()
                .setStartIndent(1)
                .toJSON(adapter);
        AssertEO.compare(json);
    }

    @Test
    public void withIndent2()  {
        TestHelper.printStartMethod();
        EO adapter = ListProviderEO.create();
        String json = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        AssertEO.compare(json);
    }

    @Test
    public void withEmpty()  {
        ListProviderEO.createEmpty();
    }

    @Test
    public void withString()  {
        ListProviderEO.createString();
    }

    @Test
    public void withStringJSON()  {
        ListProviderJSON.createString();
    }

    @Test
    public void withStringScalarTypes()  {
        TestHelper.printStartMethod();
        final EO adapter = ListProviderEO.createString();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestEOProvider.create(new File(file));
        Assert.assertEquals(S_STRING, fromJson.get(S0));
    }

    @Test
    public void withInteger()  {
        ListProviderEO.createInteger();
    }

    @Test
    public void withIntegerJSON()  {
        ListProviderJSON.createInteger();
    }

    @Test
    public void withIntegerScalarTypes()  {
        TestHelper.printStartMethod();
        final EO adapter = ListProviderEO.createInteger();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestEOProvider.create(new File(file));
        Assert.assertEquals(S_INTEGER, fromJson.get(S0));
    }

    @Test
    public void withLong()  {
        ListProviderEO.createLong();
    }

    @Test
    public void withLongJSON()  {
        ListProviderJSON.createLong();
    }

    @Test
    public void withLongScalar()  {
        TestHelper.printStartMethod();
        final EO adapter = ListProviderEO.createLong();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestEOProvider.create(new File(file));
        Assert.assertEquals(SAMPLE_LONG, fromJson.get(S0));
    }

    @Test
    public void compareEOFloat()  {
        ListProviderEO.compareFloat();
    }

    @Test
    public void withFloatScalar()  {
        TestHelper.printStartMethod();
        final EO adapter = ListProviderEO.createFloat();
        final String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        final String file = AssertEO.compare(stringified);
        /*final EO fromJson = TestEOProvider
                .createFile(file);
        Assert.assertEquals(SAMPLE_FLOAT, fromJson.get(S0));*/
    }

    @Test
    public void withDouble()  {
        ListProviderEO.createDouble();
    }

    @Test
    public void withDoubleJSON()  {
        ListProviderJSON.createDouble();
    }

    @Test
    public void withDoubleScalar()  {
        TestHelper.printStartMethod();
        final EO adapter = ListProviderEO.createDouble();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestEOProvider.create(new File(file));
        Assert.assertEquals(SAMPLE_DOUBLE, fromJson.get(S0));
    }


    @Test
    public void withDate()  {
        ListProviderEO.createDate();
    }

    @Test
    public void withDateJSON()  {
        ListProviderJSON.createDate();
    }

    @Test
    public void withDateScalar()  {
        TestHelper.printStartMethod();
        final EO adapter = ListProviderEO.createDate();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestEOProvider.create(new File(file));
        Assert.assertEquals(SAMPLE_DATE, fromJson.get(S0));
    }

    @Test
    public void withBoolean()  {
        ListProviderEO.createBoolean();
    }

    @Test
    public void withBooleanJSON()  {
        ListProviderJSON.createBoolean();
    }

    @Test
    public void withBooleanScalar()  {
        TestHelper.printStartMethod();
        final EO adapter = ListProviderEO.createBoolean();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestEOProvider.create(new File(file));
        Assert.assertEquals(S_BOOLEAN, fromJson.get(S0));
    }

    @Test
    public void withMap()  {
        ListProviderEO.createMap();
    }

    @Test
    public void withMapJSON()  {
        ListProviderJSON.createMap();
    }

    @Test
    public void withList()  {
        ListProviderEO.createList();
    }

    @Test
    public void withListJSON()  {
        ListProviderJSON.createList();
    }

    @Test
    public void withBasicTest()  {
        ListProviderEO.createBT();
    }

    @Test
    public void withBasicTestJSON()  {
        ListProviderJSON.createBT();
    }

    @Test
    public void withSubTest()  {
        ListProviderEO.createST();
    }

    @Test
    public void withSubTestJSON()  {
        ListProviderJSON.createST();
    }


    @Test
    public void withSmall()  {
        ListProviderEO.createSmall();
    }

    @Test
    public void withSmallJSON()  {
        ListProviderJSON.createSmall();
    }

    @Test
    public void withSimple()  {
        ListProviderEO.createSimple();
    }

    @Test
    public void withSimpleJSON()  {
        ListProviderJSON.createSimple();
    }

    @Test
    public void withIndentAndSerializationTypeSTANDARD()  {
        TestHelper.printStartMethod();
        EO adapter = ListProviderEO.create();
        String json = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(adapter);
        AssertEO.compare(json);
    }


    @Test
    public void withAll()  {
        ListProviderEO.create();
    }

    @Test
    public void withAllJSON()  {
        ListProviderJSON.create();
    }

    @Test
    public void withIndentAndSerializationTypeSCALAR()  {
        TestHelper.printStartMethod();
        EO adapter = ListProviderEO.create();
        String json = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        AssertEO.compare(json);
    }
}
