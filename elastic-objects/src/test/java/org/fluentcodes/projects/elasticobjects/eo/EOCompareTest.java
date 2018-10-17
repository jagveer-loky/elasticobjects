package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
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
    public void simpleEquals() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = TestObjectProvider.createEO();
        adapter.add("first")
                .set(TEO_STATIC.S_STRING);

        EO other = TestObjectProvider.createEO();
        other.add("first")
                .set(TEO_STATIC.S_STRING);

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        Assert.assertEquals("", builder.toString());
    }

    @Test
    public void simpleScalarNotEquals() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = TestObjectProvider.createEO();
        adapter.add(TEO_STATIC.S_TEST_STRING)
                .set(TEO_STATIC.S_STRING);

        EO other = TestObjectProvider.createEO();
        other.add(TEO_STATIC.S_TEST_STRING)
                .set(TEO_STATIC.S_STRING_OTHER);

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        AssertEO.compare(builder.toString());
    }
    @Test
    public void simpleContainerNotEquals() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = TestObjectProvider.createEO();
        adapter.add(TEO_STATIC.S_KEY0)
                .set(TEO_STATIC.S_STRING);

        EO other = TestObjectProvider.createEO();
        other.add(TEO_STATIC.S_KEY1)
                .set(TEO_STATIC.S_STRING);

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        AssertEO.compare(builder.toString());
    }

}
