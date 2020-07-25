package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_NAME;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 9.7.2017.
 */
public class EoSetStTest {
    private static final Logger LOG = LogManager.getLogger(EoSetStTest.class);

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

    /**
     * Error handling getting non existent path.
     *
     * @
     */
    @Test
    public void errorGettingNotExistingObjectPath()  {

        SubTest subTest = new SubTest();
        subTest.setTestString(S_STRING);
        subTest.setName(S_STRING);
        EO adapter = TestProviderRootTest.createEo(Map.class);
        adapter
                .set(subTest, F_SUB_TEST);
        String subTestName = (String) adapter.get(F_SUB_TEST, F_NAME);
        Assert.assertEquals(S_STRING, subTestName);
        adapter.get(Path.ofs(F_SUB_TEST, SAMPLE_KEY_UNKNOW));
    }
}
