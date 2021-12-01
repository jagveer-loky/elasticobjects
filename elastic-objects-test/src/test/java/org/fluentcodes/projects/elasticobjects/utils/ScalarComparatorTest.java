package org.fluentcodes.projects.elasticobjects.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoTestStatic;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class ScalarComparatorTest {
    private static final Logger LOG = LogManager.getLogger(ScalarComparatorTest.class);
    private static final Double DOUBLE = 1.1111;
    private static final Float FLOAT = 1.1111f;
    private static final Integer INT1 = 1;
    private static final Double DOUBLE1 = 1.0;
    private static final Float FLOAT1 = 1.0f;
    private static final Long LONG1 = 1L;

    @Test
    public void compareScalar()  {

        doubleTest();
        objectCompare(DOUBLE, FLOAT, true);
        objectCompare(FLOAT, DOUBLE, true);
        objectCompare(DOUBLE1, INT1, true);
        objectCompare(DOUBLE1, LONG1, true);
        objectCompare(FLOAT1, INT1, true);
        objectCompare(LONG1, INT1, true);
        byte[] testBytes = new String(EoTestStatic.S_STRING).getBytes();
        objectCompare(new String(EoTestStatic.S_STRING), testBytes, true);
        //Assert.assertEquals(1, FLOAT.doubleValue().compareTo(DOUBLE));
        //http://stackoverflow.com/questions/17898266/why-cant-we-use-to-compare-two-float-or-double-numbers
    }

    @Ignore
    //TODO for later...
    @Test
    public void compareDate() {

        Long longDate = EoTestStatic.SAMPLE_DATE.getTime();
        String stringDate2 = new SimpleDateFormat("dd.MM.yyyy z").format(EoTestStatic.SAMPLE_DATE);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("PST"));
        String stringDate3 = formatter.format(EoTestStatic.SAMPLE_DATE);
        Assert.assertTrue(ScalarComparator.compareDate(EoTestStatic.SAMPLE_DATE, EoTestStatic.S_DATE_STRING));
        Assert.assertTrue(ScalarComparator.compareDate(EoTestStatic.S_DATE_STRING, stringDate2));
        Assert.assertTrue(ScalarComparator.compareDate(EoTestStatic.SAMPLE_DATE, stringDate2));
        Assert.assertTrue(ScalarComparator.compareDate(EoTestStatic.SAMPLE_DATE, stringDate3));
        Assert.assertTrue(ScalarComparator.compareDate(EoTestStatic.SAMPLE_DATE, longDate));
    }

    private void objectCompare(Object left, Object right, boolean expected) {
        LOG.info("Compare " + left + " (" + left.getClass().getSimpleName() + ") == " + right + "(" + right.getClass().getSimpleName() + ") : " + expected);
        Assert.assertEquals(expected, ScalarComparator.compare(left, right));
    }

    private void doubleTest() {
        LOG.info("Compare " + new Double(FLOAT) + " <>" + DOUBLE + " from " + FLOAT + ": " + DOUBLE.compareTo(new Double(FLOAT)));
        Assert.assertEquals(1, DOUBLE.compareTo(new Double(FLOAT)));
        Assert.assertEquals(-1, (new Double(FLOAT)).compareTo(DOUBLE));
        LOG.info("Compare " + FLOAT.doubleValue() + " <>" + DOUBLE + " from " + FLOAT + ": " + DOUBLE.compareTo(FLOAT.doubleValue()));
        Assert.assertEquals(1, DOUBLE.compareTo(FLOAT.doubleValue()));

    }
}