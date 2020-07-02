package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 14.04.2017.
 */
public class DivReflectionTest {
    private static final String METHOD_SET_TEST = "setTest";
    private static final String METHOD_SET_TEST_OBJECT = "setTestObject";

    @Test
    public void callObjectClass() {
        ObjectClass object = new ObjectClass();
        object.setTest(S_STRING);
        Assert.assertEquals(S_STRING, object.getTest());
    }

    @Test
    public void callObjectSetter() throws Exception {
        ObjectClass object = new ObjectClass();
        Method setter = ObjectClass.class.getMethod(METHOD_SET_TEST, Object.class);
        setter.invoke(object, S_STRING);
        Assert.assertEquals(S_STRING, object.getTest());
    }

    @Test
    public void callObjectSetterNull() {
        ObjectClass object = new ObjectClass();
        try {
            Method setter = ObjectClass.class.getMethod(METHOD_SET_TEST, null);
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            System.out.println(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }


    @Test
    public void callBasicTestTestObject() throws Exception {
        BasicTest object = new BasicTest();

        object.setTestObject(S1);
        Assert.assertEquals(S1, object.getTestObject());

        Method setter = BasicTest.class.getMethod(METHOD_SET_TEST_OBJECT, Object.class);
        setter.invoke(object, S_STRING);
        Assert.assertEquals(S_STRING, object.getTestObject());
    }

    private class ObjectClass {
        private Object test;

        public Object getTest() {
            return test;
        }

        public void setTest(final Object test) {
            this.test = test;
        }
    }
}
