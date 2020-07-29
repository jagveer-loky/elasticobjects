package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestDataProvider;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;


import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;

/**
 * Created by Werner on 22.03.2017.
 */
public class TemplateCallEmbeddedTest {
    private static final Logger LOG = LogManager.getLogger(TemplateCallEmbeddedTest.class);

    @Test
    public void callEmbedded0()  {
        final EO adapter = TestDataProvider.createAdapterEmbedded();
        final TemplateCallResource action = new TemplateCallResource().setConfigKey( T_EMBEDDED_0);
        final String result = action.execute(adapter);
        Assert.assertNotNull(result);
        //AssertEO.compare(result);
    }

    @Test
    public void callEmbedded0WithPlaceHolder()  {
        final EO adapter = TestDataProvider.createAdapterEmbedded();
        final TemplateCallResource action = new TemplateCallResource().setConfigKey(T_EMBEDDED_0_WITH_PLACE_HOLDER);
        final String result = action.execute(adapter);
        Assert.assertNotNull(result);
        //AssertEO.compare(result);
    }


    @Test
    public void callEmbedded2()  {
        final EO adapter = TestDataProvider.createAdapterEmbedded();
        final TemplateCallResource action = new TemplateCallResource().setConfigKey( T_EMBEDDED_2);
        final String result = action.execute(adapter);
        Assert.assertNotNull(result);
        //AssertEO.compare(result);
    }


    @Test
    public void callEmbedded2WithPlaceHolder()  {
        final EO adapter = TestDataProvider.createAdapterEmbedded();
        final TemplateCallResource action = new TemplateCallResource().setConfigKey( T_EMBEDDED_2_WITH_PLACE_HOLDER);
        final String result = action.execute(adapter);
        Assert.assertNotNull(result);
        //AssertEO.compare(result);
    }

    @Test
    public void callEmbedded2WithValueAction()  {
        final EO adapter = TestDataProvider.createAdapterEmbedded();
        final TemplateCallResource action = new TemplateCallResource().setConfigKey( T_EMBEDDED_2_WITH_PLACE_HOLDER);
        final String result = action.execute(adapter);
        Assert.assertNotNull(result);
        Assert.assertEquals(TEO_STATIC.S_STRING, adapter.get(Path.ofs(TEO_STATIC.S_LEVEL0, TEO_STATIC.S_LEVEL1, TEO_STATIC.S_LEVEL2, TEO_STATIC.S_TEST_STRING)));
        //AssertEO.compare(result);
    }


}
