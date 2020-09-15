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
    public void createList_create_isArrayList()  {
        ModelConfigList listModel = (ModelConfigList) ProviderRootDevScope.EO_CONFIGS.findModel(List.class);
        Assertions.assertThat(listModel.getModelClass()).isEqualTo(List.class);
        List list = (List) listModel.create();
        Assertions.assertThat(list.getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(listModel.isCreate()).isTrue();
        Assertions.assertThat(listModel.isScalar()).isFalse();
    }

    @Test
    public void createArrayList_createArrayListObjec()  {
        ModelConfigList listModel = (ModelConfigList) ProviderRootDevScope.EO_CONFIGS.findModel(ArrayList.class);
        Assertions.assertThat(listModel.getModelClass()).isEqualTo(ArrayList.class);
        List list = (List) listModel.create();
        Assertions.assertThat(list.getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(listModel.isCreate()).isTrue();
        Assertions.assertThat(listModel.isScalar()).isFalse();
    }

    @Test
    public void createArrayListInScopeTest_createArrayListObject()  {
        ModelConfigList listModel = (ModelConfigList) ProviderRootTestScope.EO_CONFIGS.findModel(ArrayList.class);
        Assertions.assertThat(listModel.getModelClass()).isEqualTo(ArrayList.class);
        List list = (List) listModel.create();
        Assertions.assertThat(list.getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(listModel.isCreate()).isTrue();
        Assertions.assertThat(listModel.isScalar()).isFalse();
    }

}
