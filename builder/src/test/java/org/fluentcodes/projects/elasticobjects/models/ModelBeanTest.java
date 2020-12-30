package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface.SHAPE_TYPE;

public class ModelBeanTest {
    @Test
    public void set_ShapeTypes_LIST__getShapeType__LIST() {
        ModelBean modelBean = new ModelBean();
        modelBean.setShapeType(ShapeTypes.LIST);
        Assertions.assertThat(modelBean.getShapeType()).isEqualTo(ShapeTypes.LIST);
     }

    @Test
    public void set_ShapeTypes_LIST__properties_get_ShapeType__LIST() {
        ModelBean modelBean = new ModelBean();
        modelBean.setShapeType(ShapeTypes.LIST);
        Assertions.assertThat(modelBean.getProperties().get(SHAPE_TYPE)).isEqualTo(ShapeTypes.LIST);
    }

    @Test
    public void eo_set_ShapeTypes_LIST__get_ShapeType__LIST() {
        EO eo = ProviderRootTestScope.createEo(new ModelBean());
        eo.set(ShapeTypes.LIST, SHAPE_TYPE);
        Assertions.assertThat((String)eo.get(SHAPE_TYPE)).isEqualTo(ShapeTypes.LIST);
    }
}
