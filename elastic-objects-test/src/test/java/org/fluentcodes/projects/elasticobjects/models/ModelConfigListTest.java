package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Werner on 9.7.2017.
 */
public class ModelConfigListTest {

    @Test
    public void DEV_findModel_List__create__getClass_ArrayList()  {
        ModelConfigList listModel = (ModelConfigList) ProviderRootDevScope.EO_CONFIGS.findModel(List.class);
        Assertions.assertThat(listModel.getModelClass()).isEqualTo(List.class);
        List list = (List) listModel.create();
        Assertions.assertThat(list.getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(listModel.isList()).isTrue();
        Assertions.assertThat(listModel.isScalar()).isFalse();
    }

    @Test
    public void DEV_findModel_ArrayList__create__isList()  {
        ModelConfigList listModel = (ModelConfigList) ProviderRootDevScope.EO_CONFIGS.findModel(ArrayList.class);
        Assertions.assertThat(listModel.getModelClass()).isEqualTo(ArrayList.class);
        List list = (List) listModel.create();
        Assertions.assertThat(list.getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(listModel.isList()).isTrue();
        Assertions.assertThat(listModel.isScalar()).isFalse();
    }

    @Test
    public void TEST_findModel_ArrayList__create__isList()  {
        ModelConfigList listModel = (ModelConfigList) ProviderRootTestScope.EO_CONFIGS.findModel(ArrayList.class);
        Assertions.assertThat(listModel.getModelClass()).isEqualTo(ArrayList.class);
        List list = (List) listModel.create();
        Assertions.assertThat(list.getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(listModel.isList()).isTrue();
        Assertions.assertThat(listModel.isScalar()).isFalse();
    }

}
