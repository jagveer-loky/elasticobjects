package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.config.ModelConfig;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_CONTENT;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 11.10.2016.
 */
public class FileCallTest {
    private static final Logger LOG = LogManager.getLogger(FileCallTest.class);

    @Test
    public void readModel()  {
        ModelConfig fileCallConfig = TestProviderRootTest.EO_CONFIGS.findModel("FileCall");
        fileCallConfig.resolve();
    }

    @Test
    public void readSourceCachedTxt()  {
        final FileCallRead call = new FileCallRead().setConfigKey(FILE_SOURCE_CACHED_TXT);
        final String content = call.execute();
        Assert.assertEquals(S_STRING, content);
    }

    @Test
    /**
     * Gets the local:tmp:source.txt entry from the AssetProviders
     */
    public void readSourceTxt()  {
        final FileCallRead call = new FileCallRead().setConfigKey(FILE_SOURCE_TXT);
        final String content = call.execute();
        Assert.assertEquals(S_STRING, content);
    }

    @Test
    public void readSourceCsvGuest_ok()  {
        final FileCallRead call = new FileCallRead().setConfigKey(FILE_SOURCE_TXT);
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_GUEST);
        call.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eo.get(F_CONTENT));
    }

    @Test
    public void readSourceCsvAnonym_hasLog()  {
        final FileCallRead call = new FileCallRead().setConfigKey(FILE_SOURCE_TXT);
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_ANONYM);
        call.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assert.assertNull(INFO_NULL_FAILS, eo.get(F_CONTENT));
    }

}
