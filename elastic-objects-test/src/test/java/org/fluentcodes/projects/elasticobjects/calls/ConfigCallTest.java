package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created 10.6.2018
 */
public class ConfigCallTest {
    @Test
    public void fieldsDirect()  {
        final EO eoEmpty = TestEOProvider.create();
        final ConfigCall call = new ConfigCall();
        call.setFilterConfigName(M_FIELD_CONFIG);
        call.setFilterModule(EO_STATIC.MODULE_NAME);
        call.setFilterSubModule(MAIN);
        call.setFilterKey(".*");
        call.set(eoEmpty);
        Assert.assertFalse(eoEmpty.isEmpty());
    }

    @Test
    public void fields()  {
        final EO eoFields = TestCallsProvider.createConfigCallEO(FIELD);
        Assert.assertFalse(eoFields.isEmpty());
        Assert.assertTrue(INFO_SIZE_FAILS + eoFields.getChild(FIELD).size(), eoFields.getChild(FIELD).size() > 100);
        Assert.assertEquals(F_DESCRIPTION, eoFields.getChild(FIELD).get(toPath(F_DESCRIPTION, F_FIELD_KEY)));
    }

    @Test
    public void models()  {
        final EO eoModels = TestCallsProvider.createConfigCallEO(MODEL);
        Assert.assertFalse(eoModels.isEmpty());
        Assert.assertTrue(INFO_SIZE_FAILS + eoModels.getChild(MODEL).size(), eoModels.getChild(MODEL).size() > 80);
        Assert.assertEquals(M_STRING, eoModels.getChild(MODEL).get(toPath(M_STRING, F_MODEL_KEY)));
    }

    @Test
    public void modelsWithModuleTestObjects()  {
        final EO eoModels = TestCallsProvider.createConfigCallEO(MODEL, F_FILTER_MODULE, TEO_STATIC.MODULE_NAME);
        Assert.assertFalse(eoModels.isEmpty());
        Assert.assertTrue(INFO_SIZE_FAILS + eoModels.getChild(MODEL).size(), eoModels.getChild(MODEL).size() == 2);
        Assert.assertEquals(M_BASIC_TEST, eoModels.getChild(MODEL).get(toPath(M_BASIC_TEST, F_MODEL_KEY)));
    }

    @Test
    public void modelsWithFilterKeyMap()  {
        final EO eoModels = TestCallsProvider.createConfigCallEO(MODEL, F_FILTER_KEY, ".*Map");
        Assert.assertFalse(eoModels.isEmpty());
        Assert.assertTrue(INFO_SIZE_FAILS + eoModels.getChild(MODEL).size(), eoModels.getChild(MODEL).size() == 4);
        Assert.assertEquals(M_HASH_MAP, eoModels.getChild(MODEL).get(toPath(M_HASH_MAP, F_MODEL_KEY)));
        Assert.assertNull(INFO_NULL_FAILS, eoModels.getChild(M_BASIC_TEST));
    }

    @Test
    public void fieldsWithPath()  {
        final EO eoFields = TestCallsProvider.createConfigCallEO(FIELD, F_PATH, F_PATH);
        Assert.assertFalse(eoFields.isEmpty());
        Assert.assertTrue(INFO_SIZE_FAILS + eoFields.getChild(F_PATH).size(), eoFields.getChild(F_PATH).size() > 100);
        Assert.assertEquals(F_DESCRIPTION, eoFields.getChild(F_PATH).get(toPath(F_DESCRIPTION, F_FIELD_KEY)));
    }

    @Test
    public void dynamic_withConfigAttributeFields()  {
        final EO eoFields = TestCallsProvider.createConfigCallEO(GENERIC_CONFIG, A_CONFIG, FIELD);
        Assert.assertFalse(eoFields.isEmpty());
        Assert.assertTrue(INFO_SIZE_FAILS + eoFields.getChild(FIELD).size(), eoFields.getChild(FIELD).size() > 100);
        Assert.assertEquals(F_DESCRIPTION, eoFields.getChild(FIELD).get(toPath(F_DESCRIPTION, F_FIELD_KEY)));
    }

}
