package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 23.8.2020.
 */
public class FileWriteCallTest {
    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(FileWriteCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(FileWriteCall.class);
    }
}
