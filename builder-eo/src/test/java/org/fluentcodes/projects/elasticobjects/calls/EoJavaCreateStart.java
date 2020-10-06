package org.fluentcodes.projects.elasticobjects.calls;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.ASubObject;
import org.fluentcodes.projects.elasticobjects.assets.AnObject;
import org.fluentcodes.projects.elasticobjects.calls.configs.EoJavaCreate;
import org.fluentcodes.projects.elasticobjects.calls.configs.Moduls;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ConfigImpl;
import org.junit.Test;

public class EoJavaCreateStart {
    @Test
    public void naturalIdFileReadCall__execute__logEmpty() {
        EO eo = EoJavaCreate.EXECUTE(Moduls.EO.getName(), "main", FileReadCall.class.getSimpleName());
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void naturalIdCall__execute__logEmpty() {
        EO eo = EoJavaCreate.EXECUTE(Moduls.EO.getName(), "main", Call.class.getSimpleName(), true);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void __execute__logEmpty() {
        EO eo = EoJavaCreate.EXECUTE(Moduls.EO.getName(), "main", ConfigImpl.class.getSimpleName());
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void naturalIdAnObject__execute__logEmpty() {
        EO eo = EoJavaCreate.EXECUTE(Moduls.EO_TEST.getName(), "main", AnObject.class.getSimpleName());
        Assertions.assertThat(eo.getLog()).isEmpty();
    }
    @Test
    public void naturalIdASubObject__execute__logEmpty() {
        EO eo = EoJavaCreate.EXECUTE(Moduls.EO_TEST.getName(), "main", ASubObject.class.getSimpleName());
        Assertions.assertThat(eo.getLog()).isEmpty();
    }
}
