package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.HostBean;
import org.fluentcodes.projects.elasticobjects.calls.files.FileBean;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps.CONFIG_MAPS;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class ModelsTest {
    @Test
    public void deriveChildModels_HostBean_jsonMap__HostBean() {
        final Models models = ProviderConfigMaps.createModels();
        final PathElement pathElement = new PathElement("anyKey", HostBean.class);
        Models childModels = models
                .deriveChildModels(pathElement, "{}");
        Assert.assertEquals(HostBean.class, childModels.getModelClass());
    }

    @Test
    public void deriveChildModels_HostBean_Map__HostBean() {
        final Models models = ProviderConfigMaps.createModels();
        final PathElement pathElement = new PathElement("anyKey", HostBean.class);
        Models childModels = models
                .deriveChildModels(pathElement, new HashMap<>());
        Assert.assertEquals(HostBean.class, childModels.getModelClass());
    }

    @Test
    public void deriveChildModels_Map_HostBean__Map() {
        final Models models = ProviderConfigMaps.createModels();
        final PathElement pathElement = new PathElement("anyKey", Map.class);
        Models childModels = models
                .deriveChildModels(pathElement, new HostBean());
        Assert.assertEquals(Map.class, childModels.getModelClass());
    }

    @Test
    public void deriveChildModels_HostBean_HostBean__HostBean() {
        final Models models = ProviderConfigMaps.createModels();
        final PathElement pathElement = new PathElement("anyKey", HostBean.class);
        Models childModels = models
                .deriveChildModels(pathElement, new HostBean());
        Assert.assertEquals(HostBean.class, childModels.getModelClass());
    }

    @Test
    public void deriveChildModels_HostBean_FileBean__EoException() {
        final Models models = ProviderConfigMaps.createModels();
        final PathElement pathElement = new PathElement("anyKey", HostBean.class);

        Assertions.assertThatThrownBy(()->{models.deriveChildModels(pathElement, new FileBean());})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void deriveChildModels_HostBean_ArrayList__EoException() {
        final Models models = ProviderConfigMaps.createModels();
        final PathElement pathElement = new PathElement("anyKey", HostBean.class);

        Assertions.assertThatThrownBy(()->{models.deriveChildModels(pathElement, new ArrayList());})
                .isInstanceOf(EoException.class);
    }
}
