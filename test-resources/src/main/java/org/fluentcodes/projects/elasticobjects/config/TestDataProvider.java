package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 30.11.17.
 */
public class TestDataProvider extends TestHelper {


    public static EO createAdapterEmbedded() throws Exception {
        final EO adapter = TestObjectProvider.createEO();
        adapter.add(toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_TEST_STRING)).set(S_STRING);
        adapter.add(toPath(S_LEVEL0, S_LEVEL1, S_TEST_STRING)).set(S_STRING);
        adapter.add(toPath(S_LEVEL0, S_TEST_STRING)).set(S_STRING);
        adapter.add(S_TEST_STRING).set(S_STRING);
        return adapter;
    }
}
