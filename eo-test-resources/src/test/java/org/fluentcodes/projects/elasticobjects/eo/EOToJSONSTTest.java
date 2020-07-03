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
    public void mapDefault()  {
        TestHelper.printStartMethod();
        EO adapter = STProviderEO.create();
        String serialized = new EOToJSON().toJSON(adapter);
        AssertEO.compare(serialized);
    }

    @Test
    public void withIndent0()  {
        TestHelper.printStartMethod();
        EO adapter = STProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(0)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withIndent1()  {
        TestHelper.printStartMethod();
        EO adapter = STProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withIndent2()  {
        TestHelper.printStartMethod();
        EO adapter = STProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withSTANDARD()  {
        TestHelper.printStartMethod();
        EO adapter = STProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withString()  {
        STProviderEO.createString();
    }

    @Test
    public void withName()  {
        STProviderEO.createName();
    }

    @Test
    public void withSubTest()  {
        STProviderEO.createST();
    }

    @Test
    public void withSimple()  {
        STProviderEO.createSimple();
    }

    @Test
    public void withAll()  {
        STProviderEO.create();
    }

    @Test
    public void withIndentScalar()  {
        TestHelper.printStartMethod();
        EO adapter = STProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }
}
