package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.PermissionRole;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.PermissionInterface.ROLE_PERMISSIONS;
import static org.fluentcodes.projects.elasticobjects.calls.files.FileInterface.FILE_NAME;
import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigInterface.MODULE;

public class FileBeanTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return FileBean.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }


    @Test
    public void getSetFileName() {
        assertSetGet(FILE_NAME, "test");
    }

    @Test
    public void getNaturalId() {
        assertSetGet(NATURAL_ID, "test");
    }

    @Test
    public void getSetModule() {
        assertSetGet(MODULE, "test");
    }

    @Test
    public void getSetPermissionRole() {
        PermissionRole value = new PermissionRole();
        FileBean fileBean = (FileBean) assertSetGet(ROLE_PERMISSIONS, value);
        Assertions.assertThat(fileBean.getRolePermissions()).isEqualTo(value);
    }

}
