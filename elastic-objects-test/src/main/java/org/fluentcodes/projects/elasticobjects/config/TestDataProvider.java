package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 30.11.17.
 */
public class TestDataProvider extends TestHelper {


    public static EO createAdapterEmbedded()  {
        final EO adapter = TestEOProvider.create();
        adapter.setPathValue(toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_TEST_STRING),S_STRING);
        adapter.setPathValue(toPath(S_LEVEL0, S_LEVEL1, S_TEST_STRING),S_STRING);
        adapter.setPathValue(toPath(S_LEVEL0, S_TEST_STRING),S_STRING);
        adapter.setPathValue(S_TEST_STRING,S_STRING);
        return adapter;
    }
}
