package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 11.07.2020.
 */
public class EoOverwriteTest {
    private static final Logger LOG = LogManager.getLogger(EoOverwriteTest.class);

    @Test
    public void givenObject_whenOverwriteWithMap_isMap()  {
        String path = Path.ofs(S_LEVEL0,S_LEVEL1,S_LEVEL2);
        BasicTest bt = new BasicTest();
        bt.setTestString(S_TEST_STRING);
        EoChild child = (EoChild) TestProviderRootTest.createEo(new LinkedHashMap<>())
                .setPathValue(path,bt);

        Assert.assertEquals(BasicTest.class, child.getEo(path).getModelClass());
        Assert.assertEquals(S_TEST_STRING, child.get(path + Path.DELIMITER + F_TEST_STRING));

        Map map = new HashMap();
        map.put(S_KEY1, S_STRING);
        child.overWrite(path, map);
        Assert.assertEquals(HashMap.class, child.getEo(path).getModelClass());
        Assert.assertNull(child.get(path + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(S_STRING, child.get(path + Path.DELIMITER + S_KEY1));
    }
}
