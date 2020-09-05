package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.BEO_STATIC;
import org.fluentcodes.projects.elasticobjects.B_STATIC;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.XEO_STATIC;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created 7.6.2018
 */
public class CreatorCallsBaseTest extends CreatorConfigs {

    @Test
    public void valueEOTest()  {
        String result = createJsonConfig(BEO_STATIC.VALUE_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.TEST);
        Assert.assertTrue(result.isEmpty());
    }

    // Not used yet
    @Ignore
    @Test
    public void valueEOMain()  {
        String result = createJsonConfig(BEO_STATIC.VALUE_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    /**
     * Hosts
     */

    @Test
    public void hostEOTest()  {
        String result = createJsonConfig(BEO_STATIC.HOST_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.TEST);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void hostEOMain()  {
        String result = createJsonConfig(BEO_STATIC.HOST_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    /**
     * Files
     */

    @Test
    public void fileEOTest()  {
        String result = createJsonConfig(BEO_STATIC.FILE_XLXS, EO_STATIC.MODULE_NAME, EO_STATIC.TEST);
        Assert.assertTrue(result.isEmpty());
    }

    // No config file in main!
    @Ignore
    @Test
    public void fileEOMain()  {
        String result = createJsonConfig(BEO_STATIC.FILE_XLXS, EO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void fileXEOTest()  {
        String result = createJsonConfig(BEO_STATIC.FILE_XLXS, XEO_STATIC.MODULE_NAME, EO_STATIC.TEST);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void fileCEOTest()  {
        String result = createJsonConfig(BEO_STATIC.FILE_XLXS, "actions-csv", EO_STATIC.TEST);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void fileBEOMain()  {
        String result = createJsonConfig(BEO_STATIC.FILE_XLXS, BEO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    /**
     * Templates
     */

    @Test
    public void templateEOTest()  {
        String result = createJsonConfig(BEO_STATIC.TEMPLATE_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.TEST);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void templateEOMain()  {
        String result = createJsonConfig(BEO_STATIC.TEMPLATE_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void templateBMain()  {
        String result = createJsonConfig(BEO_STATIC.TEMPLATE_XLSX, B_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    /**
     * Json
     */

    @Test
    public void jsonEOTest()  {
        String result = createJsonConfig(BEO_STATIC.JSON_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.TEST);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void jsonEOMain()  {
        String result = createJsonConfig(BEO_STATIC.JSON_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void jsonBEOMain()  {
        String result = createJsonConfig(BEO_STATIC.JSON_XLSX, BEO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

}
