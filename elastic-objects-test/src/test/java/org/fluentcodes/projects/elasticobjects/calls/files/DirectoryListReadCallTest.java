package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.List;

/**
 * @author Werner Diwischek
 * @since 7.10.2020
 */
public class DirectoryListReadCallTest {
    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(DirectoryListReadCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(DirectoryListReadCall.class);
    }

    @Test
    public void dir_xpect_filter_AnObjectTest__read__notEmpty() {
        DirectoryListReadCall call = new DirectoryListReadCall();
        List<String> result = call.listFiles("Xpect","AnObjectTest", false);
        Assertions.assertThat(result.get(0)).isEqualTo("AnObjectTest");
    }

    @Test
    public void fileConfigKey_XPECT_TEST__read__notEmpty() {
        DirectoryListReadCall call = new DirectoryListReadCall("XPECT_TEST");
        List<String> result = (List<String>)call.execute(ProviderRootTestScope.createEo());
        Assertions.assertThat(result.get(0)).contains("compareModelConfig.json");
    }
}
