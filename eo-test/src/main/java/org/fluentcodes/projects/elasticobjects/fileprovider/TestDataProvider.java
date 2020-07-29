package org.fluentcodes.projects.elasticobjects.fileprovider;

import org.fluentcodes.projects.elasticobjects.EO;


import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 30.11.17.
 */
public class TestDataProvider {


    public static EO createAdapterEmbedded()  {
        final EO adapter = TestProviderRootTest.createEo();
        adapter.set(S_STRING, S_LEVEL0, S_LEVEL1, S_LEVEL2, S_TEST_STRING);
        adapter.set(S_STRING, S_LEVEL0, S_LEVEL1, S_TEST_STRING);
        adapter.set(S_STRING, S_LEVEL0, S_TEST_STRING);
        adapter.set(S_STRING, S_TEST_STRING);
        return adapter;
    }
}
