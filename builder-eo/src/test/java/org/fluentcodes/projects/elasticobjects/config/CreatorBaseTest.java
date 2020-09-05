package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.BEO_STATIC;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.XEO_STATIC;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created 7.6.2018
 */
public class CreatorBaseTest extends CreatorConfigs {

    @Test
    public void fieldEOMain()  {
        String result = createJsonConfig(BEO_STATIC.FIELD_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void fieldEOTest()  {
        String result = createJsonConfig(BEO_STATIC.FIELD_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.TEST);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void fieldXEOTest()  {
        String result = createJsonConfig(BEO_STATIC.FIELD_XLSX, XEO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void fieldCEOTest()  {
        String result = createJsonConfig(BEO_STATIC.FIELD_XLSX, "actions-csv", EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    /**
     * Models
     */
    @Test
    public void modelEOTest()  {
        String result = createJsonConfig(BEO_STATIC.MODEL_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.TEST);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void modelEOMain()  {
        String result = createJsonConfig(BEO_STATIC.MODEL_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void modelXEOMain()  {
        String result = createJsonConfig(BEO_STATIC.MODEL_XLSX, XEO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void modelCEOMain()  {
        String result = createJsonConfig(BEO_STATIC.MODEL_XLSX, "actions-csv", EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    /**
     * Users
     */
    @Test
    public void userEOTest()  {
        String result = createJsonConfig(BEO_STATIC.USER_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.TEST);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void userEOMain()  {
        String result = createJsonConfig(BEO_STATIC.USER_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    /**
     * Roles
     */
    @Test
    public void rolesEOTest()  {
        String result = createJsonConfig(BEO_STATIC.ROLE_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.TEST);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void roleEOMain()  {
        String result = createJsonConfig(BEO_STATIC.ROLE_XLSX, EO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

}
