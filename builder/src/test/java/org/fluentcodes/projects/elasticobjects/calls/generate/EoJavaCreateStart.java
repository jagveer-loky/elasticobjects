package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.domain.test.ASubObject;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.junit.Test;

public class EoJavaCreateStart {

    @Test
    public void __execute__logEmpty() {
        EO eo = EoJavaCreate.EXECUTE(Moduls.EO_TEST.getName(), "main", ASubObject.class.getSimpleName());
        Assertions.assertThat(eo.getLog()).isEmpty();
    }
}
