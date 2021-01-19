package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.calls.PermissionRole;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.PermissionConfigInterface.ROLE_PERMISSIONS;
import static org.fluentcodes.projects.elasticobjects.calls.files.FileConfigInterface.FILE_NAME;
import static org.fluentcodes.projects.elasticobjects.domain.BaseConfigInterface.NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface.MODULE;

public class FileBeanTest {

    @Test
    public void TEST__setFileNameTest__getFileNameTest()  {
        FileBean object = (FileBean) ModelConfigChecks.createSetGet(FileBean.class.getSimpleName(), FILE_NAME, "test");
        Assertions.assertThat(object.getFileName()).isEqualTo("test");
    }

    // setter getter from BaseBean
    @Test
    public void TEST__setNaturalIdTest__getNaturalIdTest()  {
        FileBean object = (FileBean) ModelConfigChecks.createSetGet(FileBean.class.getSimpleName(), NATURAL_ID, "test");
        Assertions.assertThat(object.getNaturalId()).isEqualTo("test");
    }

    // setter getter from ConfigBean
    @Test
    public void TEST__setModuleTest__getModuleTest()  {
        FileBean object = (FileBean) ModelConfigChecks.createSetGet(FileBean.class.getSimpleName(), MODULE, "test");
        Assertions.assertThat(object.getModule()).isEqualTo("test");
    }

    @Test
    public void TEST__setPermission__getPermissions()  {
        PermissionRole value = new PermissionRole();
        FileBean object = (FileBean) ModelConfigChecks.createSetGet(FileBean.class.getSimpleName(), ROLE_PERMISSIONS, value);
        Assertions.assertThat(object.getRolePermissions()).isEqualTo(value);
    }

}
