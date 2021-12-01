package org.fluentcodes.projects.elasticobjects.utils;

import org.fluentcodes.projects.elasticobjects.EoTestStatic;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class ScalarConverterDateTest {

    @Test
    public void transformDate()  {

        dateTestForms(EoTestStatic.SAMPLE_DATE);
    }

    @Test
    public void dateTest() {

        Date date = ScalarConverter.toDate(EoTestStatic.SAMPLE_DATE.toString());
        Assert.assertEquals(EoTestStatic.SAMPLE_DATE_LONG, date.getTime());
        date = ScalarConverter.toDate(EoTestStatic.SAMPLE_DATE);
        Assert.assertEquals(EoTestStatic.SAMPLE_DATE_LONG, date.getTime());


    }

    public void dateTestForms(Date date) {
        Assert.assertTrue(ScalarComparator.compareDate(date, date));
        Assert.assertTrue(ScalarComparator.compareDate(date.toString(), date));
        Assert.assertTrue(ScalarComparator.compareDate(date.getTime(), date));
        Assert.assertTrue(ScalarComparator.compareDate(new Long(date.getTime()).toString(), date));
    }

}