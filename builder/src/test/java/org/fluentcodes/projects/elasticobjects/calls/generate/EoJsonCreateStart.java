package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.junit.Test;

public class EoJsonCreateStart {

    @Test
    public void __execute__true() {
        EoJsonCreate.EXECUTE(FileConfig.class.getSimpleName(), Moduls.SP.getName());
    }
}
