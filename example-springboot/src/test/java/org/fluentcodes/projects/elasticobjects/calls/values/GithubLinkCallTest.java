package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

/**
 * Created by werner.diwischek on 11.03.18.
 */
public class GithubLinkCallTest {

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(GithubLinkCall.class);
    }

    @Test
    public void configKey_CallImpl__execute__$()  {
        GithubLinkCall call = new GithubLinkCall("CallImpl");
        EO eo = ProviderRootTestScope.createEo();
        String result = call.execute(eo);
    }

    @Test
    public void FileConfig_configKey_AnObjectCsv__execute__$()  {
        GithubLinkCall call = new GithubLinkCall("AnObject.csv", "FileConfig");
        EO eo = ProviderRootTestScope.createEo();
        String result = call.execute(eo);
    }

}
