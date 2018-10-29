package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.STProviderEO;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Test;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOToJSONSTTest extends TestHelper {

    @Test
    public void mapDefault() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = STProviderEO.create();
        String serialized = new EOToJSON().toJSON(adapter);
        AssertEO.compare(serialized);
    }

    @Test
    public void withIndent0() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = STProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(0)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withIndent1() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = STProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withIndent2() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = STProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withSTANDARD() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = STProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withString() throws Exception {
        STProviderEO.createString();
    }

    @Test
    public void withName() throws Exception {
        STProviderEO.createName();
    }

    @Test
    public void withSubTest() throws Exception {
        STProviderEO.createST();
    }

    @Test
    public void withSimple() throws Exception {
        STProviderEO.createSimple();
    }

    @Test
    public void withAll() throws Exception {
        STProviderEO.create();
    }

    @Test
    public void withIndentScalar() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = STProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }
}
