package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

import org.junit.Test;

/**
 * Created by Werner on 04.11.2016.
 */
public class LeftTest {
    private static final Logger LOG = LogManager.getLogger(LeftTest.class);

    @Test
    public void checkLeft()  {
        ModelInterface model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(Left.class);
        model.resolve();
    }

    @Test
    public void checkLeftListMany()  {
        ModelInterface model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(LeftList.class);
        model.resolve();
    }

    @Test
    public void checkLeftMapMany()  {
        ModelInterface model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(LeftMap.class);
        model.resolve();
    }


}
