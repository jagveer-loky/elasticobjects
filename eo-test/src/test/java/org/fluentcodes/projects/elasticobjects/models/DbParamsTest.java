package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfig;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;


public class DbParamsTest {
    @Test
    public void initDbParams() {
        DBParams dbParams = new DBParams(ProviderConfig.createDbParams());
        Assert.assertEquals(F_TABLE, dbParams.getTable());
        Assert.assertEquals(F_ID_KEY, dbParams.getIdKey());
        Assert.assertTrue(dbParams.isHibernateAnnotations());
        Assert.assertEquals(F_NATURAL_KEYS, dbParams.getNaturalKeys().get(0));
    }

}
