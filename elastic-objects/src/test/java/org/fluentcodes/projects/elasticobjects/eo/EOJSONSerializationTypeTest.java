package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by werner.diwischek on 15.11.17.
 */
public class EOJSONSerializationTypeTest extends TestHelper {
    /**
     * Setting scalar values with a simple fieldName.
     *
     * @throws Exception
     */
    @Test
    public void setString() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = TestEOProvider.createEmptyMap();
        adapter.add(TEO_STATIC.S_LEVEL0)
                .set(TEO_STATIC.S_STRING);
        Assert.assertEquals(TEO_STATIC.S_STRING, adapter.get(TEO_STATIC.S_LEVEL0));
        AssertEO.compare(adapter);
    }

    /**
     * Sets double value with a simple fieldName.
     *
     * @throws Exception on error
     */
    @Test
    public void setDoubleValue() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = TestEOProvider.createEmptyMap();
        adapter.add(TEO_STATIC.S_KEY_DOUBLE)
                .set(1.1D);

        Assert.assertEquals(1.1D, adapter.get(TEO_STATIC.S_KEY_DOUBLE));
        Assert.assertEquals(Double.class, adapter.getChild(TEO_STATIC.S_KEY_DOUBLE).getModelClass());

        AssertEO.compare(adapter);
    }

    /**
     * Sets long value with a simple fieldName.
     *
     * @throws Exception on error
     */
    @Test
    public void setLong() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = TestEOProvider.createEmptyMap();
        adapter.add(TEO_STATIC.S_KEY_LONG)
                .set(1L);

        Assert.assertEquals(1L, adapter.get(TEO_STATIC.S_KEY_LONG));
        Assert.assertEquals(Long.class, adapter.getChild(TEO_STATIC.S_KEY_LONG).getModelClass());

        AssertEO.compare(adapter);
    }

    /**
     * Sets float value with a simple fieldName.
     *
     * @throws Exception on error
     */
    @Test
    public void setFloatValue() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = TestEOProvider.createEmptyMap();
        adapter.add(TEO_STATIC.S_KEY_FLOAT)
                .set(1.1F);

        Assert.assertEquals(1.1F, adapter.get(TEO_STATIC.S_KEY_FLOAT));
        Assert.assertEquals(Float.class, adapter.getChild(TEO_STATIC.S_KEY_FLOAT).getModelClass());

        AssertEO.compare(adapter);
    }

    /**
     * Sets int value with a simple fieldName.
     *
     * @throws Exception on error
     */
    @Test
    public void setInt() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = TestEOProvider.createEmptyMap();
        adapter.add(TEO_STATIC.S_KEY_INTEGER)
                .set(1);

        Assert.assertEquals(1, adapter.get(TEO_STATIC.S_KEY_INTEGER));
        Assert.assertEquals(Integer.class, adapter.getChild(TEO_STATIC.S_KEY_INTEGER).getModelClass());

        AssertEO.compare(adapter);
    }

}
