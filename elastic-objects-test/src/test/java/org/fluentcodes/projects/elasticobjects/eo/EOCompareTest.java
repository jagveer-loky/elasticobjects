package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * New compare method tests
 * Created by Werner on 6.6.2018.
 */
public class EOCompareTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOCompareTest.class);

    @Test
    public void simpleEquals()  {
        TestHelper.printStartMethod();
        EO adapter = TestEOProvider.create();
        adapter.setPathValue("first", TEO_STATIC.S_STRING);

        EO other = TestEOProvider.create();
        other.setPathValue("first" ,TEO_STATIC.S_STRING);

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        Assert.assertEquals("", builder.toString());
    }

    @Test
    public void simpleScalarNotEquals()  {
        TestHelper.printStartMethod();
        EO adapter = TestEOProvider.create();
        adapter.setPathValue(TEO_STATIC.S_TEST_STRING
                ,TEO_STATIC.S_STRING);

        EO other = TestEOProvider.create();
        other.setPathValue(TEO_STATIC.S_TEST_STRING
                ,TEO_STATIC.S_STRING_OTHER);

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        AssertEO.compare(builder.toString());
    }

    @Test
    public void simpleContainerNotEquals()  {
        TestHelper.printStartMethod();
        EO adapter = TestEOProvider.create();
        adapter.setPathValue(TEO_STATIC.S_KEY0,TEO_STATIC.S_STRING);

        EO other = TestEOProvider.create();
        other.setPathValue(TEO_STATIC.S_KEY1,TEO_STATIC.S_STRING);

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        AssertEO.compare(builder.toString());
    }

}
