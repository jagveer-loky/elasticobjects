package org.fluentcodes.projects.elasticobjects.calls.json;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileCallRead;
import org.fluentcodes.projects.elasticobjects.config.ModelConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.J_MODULE_CONFIG_JSON;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created 23.8.2020
 */
public class JsonCallWriteTest {

    @Test
    public void writeReadTmpJsonSuperAdmin_ok()  {
        final JsonCallWrite call = new JsonCallWrite(FILE_TMP_JSON);
        EO eoWrite = TestProviderRootTest.createEo();
        eoWrite.set(S_VALUE11, S0,S_KEY1);
        eoWrite.set(S_VALUE12, S0,S_KEY2);
        eoWrite.set(S_VALUE21, S1,S_KEY1);
        eoWrite.set(S_VALUE22, S1,S_KEY2);
        eoWrite.setRoles(R_SUPER_ADMIN);
        call.execute(eoWrite);
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_SUPER_ADMIN);
        call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, eo.getEo(S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eo.getEo(S0).get(S_KEY1));
    }
}
