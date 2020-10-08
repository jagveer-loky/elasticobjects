package org.fluentcodes.projects.elasticobjects.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class ScalarConverterTest {
    private static final Logger log = LogManager.getLogger(ScalarConverterTest.class);
    private static final Double DOUBLE = 1.1;
    private static final Float FLOAT = 1.1F;

    //TODO ScalarPaths actually not used...
    @Ignore
    @Test
    public void numbers()  {

        transformNumberTest(Integer.class, SAMPLE_LONG);
        transformNumberTest(Long.class, S_INTEGER);
        transformNumberTest(Double.class, SAMPLE_DOUBLE);
        transformFloatTest(Double.class, SAMPLE_FLOAT);
        transformNumberTest(Float.class, SAMPLE_FLOAT);
        transformFloatTest(Float.class, SAMPLE_FLOAT);
        transformNumberTest(Short.class, new Short(S1));
    }

    @Test
    public void transformProblemsTest()  {

        transform(Integer.class, Integer.MAX_VALUE, Integer.MAX_VALUE);
        transform(Integer.class, Integer.MIN_VALUE, Integer.MIN_VALUE);
        int smaller = Integer.MIN_VALUE - 1;
        transform(Integer.class, smaller, smaller);
        transform(Integer.class, Integer.MIN_VALUE - 1, Integer.MIN_VALUE - 1);
    }

    public void transformNumberTest(Class<?> transformClass, Object compare)  {
        transform(transformClass, SAMPLE_DOUBLE, compare);
        transform(transformClass, compare, compare);
        transform(transformClass, SAMPLE_FLOAT, compare);
        transform(transformClass, 1.0, compare);
        transform(transformClass, 1.0F, compare);
        transform(transformClass, new Long(S_INTEGER), compare);
        transform(transformClass, new String(S1), compare);
        transform(transformClass, SAMPLE_FLOAT.toString(), compare);
        transform(transformClass, SAMPLE_DOUBLE.toString(), compare);
    }

    private void transformFloatTest(Class<?> transformClass, Object compare)  {
        transform(transformClass, SAMPLE_DOUBLE, compare);
        transform(transformClass, compare, compare);
        transform(transformClass, new Float(1.1), compare);
        transform(transformClass, 1.1, compare);
        transform(transformClass, 1.1F, compare);
        transform(transformClass, new String("1,1"), compare);
        transform(transformClass, new String("1.1"), compare);
    }


    public void transform(Class<?> clazz, Object right, Object expected)  {
        log.info("Compare " + clazz.getSimpleName() + " == " + right + ": " + expected);
        Object rightObject = ScalarConverter.transformScalar(clazz, right);
        if (clazz == Date.class) {
            log.info(((Date) rightObject).getTime() + "=" + ((Date) expected).getTime());
        }
        // TODO check if necessary. Integer value will be returned! Assert.assertFalse(rightObject == right);
        Assert.assertEquals(expected, rightObject);
    }

    //http://stackoverflow.com/questions/17898266/why-cant-we-use-to-compare-two-float-or-double-numbers

    /**
     * This shows the mapping from FLOAT to DOUBLE.
     */
    @Test
    public void doubleTest() {
        Assert.assertEquals(-1, DOUBLE.compareTo(new Double(FLOAT)));
        Assert.assertEquals(1, (new Double(FLOAT)).compareTo(DOUBLE));
        Assert.assertEquals(-1, DOUBLE.compareTo(FLOAT.doubleValue()));
    }

}