package org.fluentcodes.projects.elasticobjects.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class ScalarConverterDateTest {
    private static final Logger log = LogManager.getLogger(ScalarConverterDateTest.class);

    @Test
    public void transformDate()  {

        dateTestForms(TEO_STATIC.SAMPLE_DATE);
    }

    @Test
    public void dateTest() {

        Date date = ScalarConverter.toDate(TEO_STATIC.SAMPLE_DATE.toString());
        Assert.assertEquals(TEO_STATIC.SAMPLE_DATE_LONG, date.getTime());
        date = ScalarConverter.toDate(TEO_STATIC.SAMPLE_DATE);
        Assert.assertEquals(TEO_STATIC.SAMPLE_DATE_LONG, date.getTime());


    }

    public void dateTestForms(Date date) {
        Assert.assertTrue(ScalarComparator.compareDate(date, date));
        Assert.assertTrue(ScalarComparator.compareDate(date.toString(), date));
        Assert.assertTrue(ScalarComparator.compareDate(date.getTime(), date));
        Assert.assertTrue(ScalarComparator.compareDate(new Long(date.getTime()).toString(), date));
    }

}