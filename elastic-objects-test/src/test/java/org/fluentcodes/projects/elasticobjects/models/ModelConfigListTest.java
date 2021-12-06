package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev.CONFIG_MAPS_DEV;

/**
 * Created by Werner on 9.7.2017.
 */
public class ModelConfigListTest {

    @Test
    public void DEV_findModel_List__create__getClass_ArrayList()  {
        ModelConfigList listModel = (ModelConfigList) CONFIG_MAPS_DEV.findModel(List.class);
        Assertions.assertThat(listModel.getModelClass()).isEqualTo(List.class);
        List list = (List) listModel.create();
        Assertions.assertThat(list.getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(listModel.isList()).isTrue();
        Assertions.assertThat(listModel.isScalar()).isFalse();
    }

    @Test
    public void DEV_findModel_ArrayList__create__isList()  {
        ModelConfigList listModel = (ModelConfigList) ProviderConfigMapsDev.CONFIG_MAPS_DEV.findModel(ArrayList.class);
        Assertions.assertThat(listModel.getModelClass()).isEqualTo(ArrayList.class);
        List list = (List) listModel.create();
        Assertions.assertThat(list.getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(listModel.isList()).isTrue();
        Assertions.assertThat(listModel.isScalar()).isFalse();
    }

    @Test
    public void TEST_findModel_ArrayList__create__isList()  {
        ModelConfigList listModel = (ModelConfigList) ProviderConfigMaps.CONFIG_MAPS.findModel(ArrayList.class);
        Assertions.assertThat(listModel.getModelClass()).isEqualTo(ArrayList.class);
        List list = (List) listModel.create();
        Assertions.assertThat(list.getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(listModel.isList()).isTrue();
        Assertions.assertThat(listModel.isScalar()).isFalse();
    }

}
