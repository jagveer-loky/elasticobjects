package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Ignore;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.ID;
import static org.fluentcodes.projects.elasticobjects.models.ModelBean.FIELD_BEANS;

public class BuilderJsonTest {
    public static String BUILDER_JSON_TEST = "builderTest.json";
    public static EO builderEo = createBuilder();
    public static EO createBuilder() {
        EO eo = ProviderConfigMaps.createEo();
        FileReadCall call = new FileReadCall(BUILDER_JSON_TEST);
        call.setTargetPath(PathElement.SAME);
        call.execute(eo);
        return eo;
    }

    @Ignore
    @Test
    public void check_AnObject_fieldBeans_myString__parent_parent__isModelBean() {
        EO anObjectEo = builderEo.getEo(AnObject.class.getSimpleName());
        Assertions.assertThat(anObjectEo.getModelClass()).isEqualTo(ModelBean.class);
        EO idEo = anObjectEo.getEo(FIELD_BEANS, ID);
        Assertions.assertThat(idEo.getModelClass()).isEqualTo(FieldBean.class);
        Assertions.assertThat(idEo.hasParent()).isTrue();
        EO modelEo = idEo.getParent().getParent();
        Assertions.assertThat(modelEo.getModelClass()).isEqualTo(ModelBean.class);
    }
 }
