package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestProviderCalls;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created 10.6.2018
 */
public class ConfigurationCallTest {
    @Test
    public void fieldsDirect()  {
        final EO eoEmpty = TestProviderRootTest.createEo();
        final ConfigurationCall call = new ConfigurationCall();
        call.setFilterConfigName(M_FIELD_CONFIG);
        call.setFilterModule(EO_STATIC.MODULE_NAME);
        call.setFilterSubModule(MAIN);
        call.setFilterKey(".*");
        call.execute(eoEmpty);
        Assert.assertFalse(eoEmpty.isEmpty());
    }

    @Test
    public void fields()  {
        final EO eoFields = TestProviderCalls.createConfigCallEO(FIELD);
        Assert.assertFalse(eoFields.isEmpty());
        Assert.assertTrue(INFO_SIZE_FAILS + eoFields.getEo(FIELD).size(), eoFields.getEo(FIELD).size() > 100);
        Assert.assertEquals(F_DESCRIPTION, eoFields.getEo(FIELD).get(Path.ofs(F_DESCRIPTION, F_FIELD_KEY)));
    }

    @Test
    public void models()  {
        final EO eoModels = TestProviderCalls.createConfigCallEO(MODEL);
        Assert.assertFalse(eoModels.isEmpty());
        Assert.assertTrue(INFO_SIZE_FAILS + eoModels.getEo(MODEL).size(), eoModels.getEo(MODEL).size() > 80);
        Assert.assertEquals(M_STRING, eoModels.getEo(MODEL).get(Path.ofs(M_STRING, F_MODEL_KEY)));
    }

    @Test
    public void modelsWithModuleTestObjects()  {
        final EO eoModels = TestProviderCalls.createConfigCallEO(MODEL, F_FILTER_MODULE, TEO_STATIC.MODULE_NAME);
        Assert.assertFalse(eoModels.isEmpty());
        Assert.assertTrue(INFO_SIZE_FAILS + eoModels.getEo(MODEL).size(), eoModels.getEo(MODEL).size() == 2);
        Assert.assertEquals(M_BASIC_TEST, eoModels.getEo(MODEL).get(Path.ofs(M_BASIC_TEST, F_MODEL_KEY)));
    }

    @Test
    public void modelsWithFilterKeyMap()  {
        final EO eoModels = TestProviderCalls.createConfigCallEO(MODEL, F_FILTER_KEY, ".*Map");
        Assert.assertFalse(eoModels.isEmpty());
        Assert.assertTrue(INFO_SIZE_FAILS + eoModels.getEo(MODEL).size(), eoModels.getEo(MODEL).size() == 4);
        Assert.assertEquals(M_HASH_MAP, eoModels.getEo(MODEL).get(Path.ofs(M_HASH_MAP, F_MODEL_KEY)));
        Assert.assertNull(INFO_NULL_FAILS, eoModels.getEo(M_BASIC_TEST));
    }

    @Test
    public void fieldsWithPath()  {
        final EO eoFields = TestProviderCalls.createConfigCallEO(FIELD, F_PATH, F_PATH);
        Assert.assertFalse(eoFields.isEmpty());
        Assert.assertTrue(INFO_SIZE_FAILS + eoFields.getEo(F_PATH).size(), eoFields.getEo(F_PATH).size() > 100);
        Assert.assertEquals(F_DESCRIPTION, eoFields.getEo(F_PATH).get(Path.ofs(F_DESCRIPTION, F_FIELD_KEY)));
    }

    @Test
    public void dynamic_withConfigAttributeFields()  {
        final EO eoFields = TestProviderCalls.createConfigCallEO(GENERIC_CONFIG, A_CONFIG, FIELD);
        Assert.assertFalse(eoFields.isEmpty());
        Assert.assertTrue(INFO_SIZE_FAILS + eoFields.getEo(FIELD).size(), eoFields.getEo(FIELD).size() > 100);
        Assert.assertEquals(F_DESCRIPTION, eoFields.getEo(FIELD).get(Path.ofs(F_DESCRIPTION, F_FIELD_KEY)));
    }

}
