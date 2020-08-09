package org.fluentcodes.projects.elasticobjects.calls.json;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created 23.8.2020
 */
public class JsonCallWriteTest {

    @Test
    public void writeReadTmpJsonSuperAdmin_ok()  {
        final JsonCallWrite callWrite = new JsonCallWrite(FILE_TMP_JSON);
        EO eo = ProviderRootTest.createEo();
        eo.setRoles(R_SUPER_ADMIN);
        eo.set(S_VALUE11, S0,S_KEY1);
        eo.set(S_VALUE12, S0,S_KEY2);
        eo.set(S_VALUE21, S1,S_KEY1);
        eo.set(S_VALUE22, S1,S_KEY2);
        eo.setRoles(R_SUPER_ADMIN);
        String result = callWrite.execute(eo);
        final JsonCallRead callRead = new JsonCallRead(FILE_TMP_JSON);
        String readed = callRead.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo(readed);
    }
}
