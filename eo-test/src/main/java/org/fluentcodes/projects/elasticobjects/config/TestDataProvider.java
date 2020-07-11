package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;


import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 30.11.17.
 */
public class TestDataProvider {


    public static EO createAdapterEmbedded()  {
        final EO adapter = TestProviderRootTest.createEo();
        adapter.setPathValue(Path.ofs(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_TEST_STRING),S_STRING);
        adapter.setPathValue(Path.ofs(S_LEVEL0, S_LEVEL1, S_TEST_STRING),S_STRING);
        adapter.setPathValue(Path.ofs(S_LEVEL0, S_TEST_STRING),S_STRING);
        adapter.setPathValue(S_TEST_STRING,S_STRING);
        return adapter;
    }
}
