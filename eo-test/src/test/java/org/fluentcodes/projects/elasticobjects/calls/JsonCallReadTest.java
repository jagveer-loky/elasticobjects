package org.fluentcodes.projects.elasticobjects.calls;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;

import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created 12.6.2018
 */
public class JsonCallReadTest {
    @Test
    public void readModuleConfigJson()  {
        final EO eoEmpty = TestProviderRootTest.createEo();
        final JsonCallRead call = new JsonCallRead(J_MODULE_CONFIG_JSON);
        call.execute();
        Assert.assertEquals(MODULE_SHORT, eoEmpty.getEo(MODULE).get(F_SHORT));
        //AssertEO.compare(eoEmpty);
    }

    @Test
    public void readTargetJsonGuest_hasLog()  {
        final JsonCallRead call = new JsonCallRead(FILE_TMP_JSON);
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_GUEST);
        call.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

    @Test
    public void writeReadTmpJsonSuperAdmin_ok()  {
        final JsonCallWrite call = new JsonCallWrite(FILE_TMP_JSON);
        EO eoWrite = TestProviderRootTest.createEo();
        eoWrite.setPathValue(S0 + Path.DELIMITER + S_KEY1,S_VALUE11);
        eoWrite.setPathValue(S0 + Path.DELIMITER + S_KEY2,S_VALUE12);
        eoWrite.setPathValue(S1 + Path.DELIMITER + S_KEY1,S_VALUE21);
        eoWrite.setPathValue(S1 + Path.DELIMITER + S_KEY2,S_VALUE22);
        eoWrite.setRoles(R_SUPER_ADMIN);
        call.execute(eoWrite);
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_SUPER_ADMIN);
        call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, eo.getEo(S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eo.getEo(S0).get(S_KEY1));
    }


    @Test
    public void readSourceJsonGuest_ok()  {
        final JsonCallRead call = new JsonCallRead(FILE_SOURCE_JSON);
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_GUEST);
        call.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, eo.getEo(S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eo.getEo(S0).get(S_KEY1));
    }

    @Test
    public void readSourceJsonAnonym_hasLog()  {
        final JsonCallRead call = new JsonCallRead(FILE_SOURCE_JSON);
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_ANONYM);
        call.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }
}
