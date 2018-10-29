package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.BTProvider;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.fluentcodes.projects.elasticobjects.test.MapProvider;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EONoPathChildSet_models_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EONoPathChildSet_models_Test.class);

    private void setEO_ok(final EO root, final Object value) throws Exception {
        final Class valueClass = value.getClass();
        root.add()
                .set(value);
        Assert.assertEquals(value, root.get());
        Assert.assertEquals(INFO_COMPARE_FAILS, valueClass, root.getModelClass());
        Assert.assertTrue(INFO_EMPTY_FAILS, root.getLog().isEmpty());
    }

    private void setEO_fails(final EO root, final Object value) throws Exception {
        final Class valueClass = value.getClass();
        root.add()
                .set(value);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, root.getLog().isEmpty());
    }

    @Test
    public void givenStringWithInteger() throws Exception {
        final EO eoString = DevObjectProvider
                .createEOBuilder()
                .setPath(F_TEST_STRING)
                .set(S_STRING);
        setEO_ok(eoString, S_INTEGER);
    }


    @Test
    public void givenStringWithMap() throws Exception {
        final EO eoString = DevObjectProvider
                .createEOBuilder()
                .setPath(F_TEST_STRING)
                .set(S_STRING);
        setEO_ok(eoString, new LinkedHashMap());
    }

    @Test
    public void givenBTWithBT() throws Exception {
        final EO eoBTString = TestObjectProvider.createEOBuilder()
                .setPath(F_BASIC_TEST)
                .set(BTProvider.createString());
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));

        eoBTString
                .add()
                .set(BTProvider.createInteger());
        Assert.assertNull(S_STRING, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }

    @Test
    public void givenBTWithMap() throws Exception {
        final EO eoBTString = TestObjectProvider.createEOBuilder()
                .setPath(F_BASIC_TEST)
                .set(BTProvider.createString());
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));

        setEO_ok(eoBTString, MapProvider.createInteger());
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }


}

