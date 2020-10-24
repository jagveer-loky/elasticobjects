package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_MAP;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_STRING;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 04.11.2016.
 */
public class ConfigDivTest {

    private static final Logger LOG = LogManager.getLogger(ConfigDivTest.class);

    @Test
    public void changeLocalFieldKeys()  {
        ModelConfigObject model = (ModelConfigObject)ProviderRootTestScope.EO_CONFIGS.findModel(Config.class);
        Map map = model.getProperties();
        Assertions
                .assertThatThrownBy(
                        () -> {
                            model.getLocalFieldKeys().add("newValue");
                        })
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void testMap()  {
        
        ModelConfigInterface mapModel = ProviderRootTestScope.EO_CONFIGS.findModel(M_MAP);
        Assert.assertEquals(M_MAP, mapModel.getModelKey());
        Map map = (Map) mapModel.create();
        Assert.assertEquals(LinkedHashMap.class, map.getClass());

        mapModel.set(S_TEST_STRING, map, S_STRING);
        Assert.assertEquals(S_STRING, map.get(S_TEST_STRING));
        Assert.assertEquals(S_STRING, mapModel.get(S_TEST_STRING, map));
        Assert.assertTrue(mapModel.isMap());
    }

    @Test
    public void scalarModel__setKeyValue__exception()  {
        ModelConfigInterface scalarModel = ProviderRootTestScope.EO_CONFIGS.findModel(M_STRING);
        Assert.assertEquals(M_STRING, scalarModel.getModelKey());
        Assert.assertTrue(scalarModel.isScalar());

        String scalar = (String) scalarModel.create();
        Assert.assertNull(scalar);
        Assertions.assertThatThrownBy(()->{scalarModel.set(S_TEST_STRING, scalar, S_STRING);})
                .isInstanceOf(EoException.class);
        Assertions.assertThatThrownBy(()->{scalarModel.get(S_TEST_STRING, scalar);})
                .isInstanceOf(EoException.class);
    }

}
