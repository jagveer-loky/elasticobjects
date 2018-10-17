package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Werner on 04.11.2016.
 */
public class BasicTestTest  {
    private static final Logger LOG = LogManager.getLogger(BasicTestTest.class);
    @Test
    public void bean() {
        BasicTest test = new BasicTest();
        test.setTestInt(1);
        Assert.assertEquals(new Integer(1), test.getTestInt());
    }

    @Test
    public void readDataFile()  throws Exception {
        /* TODOList list = JacksonHelperObsolete.readListFromDataClassPath(BasicTest.class);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);*/
        //AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, list);
    }



    @Test
    public void readBasicTestSimple() throws Exception {
        /* TODOList<Object> map = JacksonHelperObsolete.readListFromUrl("BasicTestSimple.json", BasicTest.class);
        Assert.assertNotNull(map);
        Assert.assertTrue(map.size()==3);
        BasicTest test = (BasicTest) map.find(0);
        Assert.assertEquals("testString1", test.getTestString());*/
    }


}
