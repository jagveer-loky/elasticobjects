package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

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
    public void givenBT_whenOverwriteWithMap_isMap()  {
        BasicTest bt = new BasicTest();
        bt.setTestString(S_TEST_STRING);
        EoChild child = (EoChild) ProviderRootTestScope.createEo(new LinkedHashMap<>())
                .set(bt, S_LEVEL0,S_LEVEL1,S_LEVEL2);

        EoRoot root = child.getRoot();
        Assert.assertEquals(BasicTest.class, root.getEo(S_LEVEL0,S_LEVEL1,S_LEVEL2).getModelClass());
        Assert.assertEquals(S_TEST_STRING, root.get(S_LEVEL0,S_LEVEL1,S_LEVEL2, BasicTest.TEST_STRING));

        Map map = new HashMap();
        map.put(S_KEY1, S_STRING);
        root.overWrite( map, S_LEVEL0,S_LEVEL1,S_LEVEL2);

        Assert.assertEquals(HashMap.class, root.getEo(S_LEVEL0,S_LEVEL1,S_LEVEL2).getModelClass());
        Assert.assertEquals(S_STRING, root.get(S_LEVEL0,S_LEVEL1,S_LEVEL2, S_KEY1));
    }
}
