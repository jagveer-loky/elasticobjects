package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.configs.EoJsonCreate;
import org.fluentcodes.projects.elasticobjects.calls.configs.Moduls;
import org.fluentcodes.projects.elasticobjects.calls.db.DbSqlConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.junit.Test;

public class EoJsonCreateStart {

    @Test
    public void call_HostConfig__execute__true() {
        EoJsonCreate.EXECUTE(HostConfig.class.getSimpleName(), Moduls.EO.getName());
    }

    @Test
    public void call_FileConfig__execute__true() {
        EoJsonCreate.EXECUTE(FileConfig.class.getSimpleName(), Moduls.BUILDER.getName());
    }

    @Test
    public void call_DbSqlConfig__execute__true() {
        EoJsonCreate.EXECUTE(DbSqlConfig.class.getSimpleName(), Moduls.EO_TEST.getName());
    }

    @Test
    public void call_ModelConfig__execute__true() {
        EoJsonCreate.EXECUTE(ModelConfig.class.getSimpleName(), Moduls.EO.getName());
    }

    @Test
    public void call_FieldConfig__execute__true() {
        EoJsonCreate.EXECUTE(FieldConfig.class.getSimpleName(), Moduls.EO.getName());
    }
}
