package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.R_ANONYM;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.R_GUEST;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL2;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * @author Werner Diwischek
 * @since 7.10.2020
 */
public class DirectoryListReadCallTest {
    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(DirectoryListReadCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DirectoryListReadCall.class);
    }

    @Test
    public void dir_xpect_filter_AnObjectTest__read__notEmpty() {
        DirectoryListReadCall call = new DirectoryListReadCall();
        List<String> result = call.listFiles("Xpect","AnObjectTest", false);
        Assertions.assertThat(result.get(0)).contains("AnObjectTest");
    }

    @Test
    public void configKey_XPECT_TEST__read__notEmpty() {
        DirectoryListReadCall call = new DirectoryListReadCall("XPECT_TEST");
        List<String> result = (List<String>)call.execute(ProviderRootTestScope.createEo());
        Assertions.assertThat(result.get(0)).contains("AnObjectTest");
    }
}
