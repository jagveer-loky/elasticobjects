package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_NAME;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 9.7.2017.
 */
public class StSetTest {
    private static final Logger LOG = LogManager.getLogger(StSetTest.class);

    @Test
    public void givenBean_whenSetTestString_thenOk() {
        SubTest test = new SubTest();
        test.setTestString("x");
        Assert.assertEquals("x", test.getTestString());
    }
}
