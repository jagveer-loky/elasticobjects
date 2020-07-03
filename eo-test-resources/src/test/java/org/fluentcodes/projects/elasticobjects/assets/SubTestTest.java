package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 9.7.2017.
 */
public class SubTestTest {
    private static final Logger LOG = LogManager.getLogger(SubTestTest.class);

    @Test
    public void bean() {
        SubTest test = new SubTest();
        test.setTestString("x");
        Assert.assertEquals("x", test.getTestString());
    }

    @Test
    public void readDataFile()  {
        /*List<Object> list = JacksonHelperObsolete.readListFromDataClassPath(SubTest.class);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);*/
        //TODO  AssertObject.compare(list);
    }
}
