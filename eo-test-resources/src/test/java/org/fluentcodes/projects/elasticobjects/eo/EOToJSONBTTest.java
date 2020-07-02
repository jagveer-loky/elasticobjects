package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.F_TEST_FLOAT;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.SAMPLE_FLOAT;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOToJSONBTTest extends TestHelper {

    @Test
    public void mapDefault() throws Exception {
        EO adapter = BTProviderEO.create();
        String serialized = new EOToJSON().toJSON(adapter);
        AssertEO.compare(serialized);
    }

    @Test
    public void withIndent0() throws Exception {
        EO adapter = BTProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(0)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withIndent1() throws Exception {
        EO adapter = BTProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withIndent2() throws Exception {
        EO adapter = BTProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withSTANDARD() throws Exception {
        EO adapter = BTProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }


    @Test
    public void withEO() throws Exception {
        BTProviderEO.create();
    }

    @Test
    public void withString() throws Exception {
        BTProviderEO.createString();
    }

    @Test
    public void withInteger() throws Exception {
        BTProviderEO.createInteger();
    }

    @Test
    public void withLong() throws Exception {
        BTProviderEO.createLong();
    }

    @Test
    public void withFloat() throws Exception {
        BTProviderEO.createFloat();
    }

    @Test
    public void withFloatStandard() throws Exception {
        EO adapter = BTProviderEO.createFloat();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestEOProvider.createEOBuilder().mapFile(file);
        Assert.assertEquals(new Double(SAMPLE_FLOAT.toString()), fromJson.get(F_TEST_FLOAT));
    }

    @Test
    public void withDouble() throws Exception {
        BTProviderEO.createDouble();
    }

    @Test
    public void withDate() throws Exception {
        BTProviderEO.createDate();
    }

    @Test
    public void withBoolean() throws Exception {
        BTProviderEO.createBoolean();
    }

    @Test
    public void withMap() throws Exception {
        BTProviderEO.createMap();
    }

    @Test
    public void withList() throws Exception {
        BTProviderEO.createList();
    }

    @Test
    public void withBasicTest() throws Exception {
        BTProviderEO.createBT();
    }

    @Test
    public void withSubTest() throws Exception {
        BTProviderEO.createST();
    }

    @Test
    public void withSubTestMap() throws Exception {
        BTProviderEO.createMapST();
    }

    @Test
    public void withSubTestList() throws Exception {
        BTProviderEO.createListST();
    }

    @Test
    public void withSmall() throws Exception {
        BTProviderEO.createSmall();
    }

    @Test
    public void withSimple() throws Exception {
        BTProviderEO.createSimple();
    }

    @Test
    public void withAll() throws Exception {
        BTProviderEO.create();
    }


    @Test
    public void withSubTestMapAndSerializationTypePARAMS() throws Exception {
        EO adapter = TestEOProvider
                .createEOBuilder()
                .set(BTProvider.createMapST());
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withIndentAndSerializationTypeSCALAR_TYPES() throws Exception {
        EO adapter = BTProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }
}
