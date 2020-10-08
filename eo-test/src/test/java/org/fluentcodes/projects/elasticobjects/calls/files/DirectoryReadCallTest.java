package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 7.10.2020.
 */
public class DirectoryReadCallTest {
    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(DirectoryListReadCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DirectoryListReadCall.class);
    }

}
