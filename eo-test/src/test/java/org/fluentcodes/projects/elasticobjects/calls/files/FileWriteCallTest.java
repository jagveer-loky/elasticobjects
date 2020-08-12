package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 23.8.2020.
 */
public class FileWriteCallTest {
    @Test
    public void givenModelClass_whenCreate_thenNoException()  {
        ConfigModelChecks.create(FileWriteCall.class);
    }

    @Test
    public void whenCompareConfigurations_thenXpected()  {
        ConfigModelChecks.compare(FileWriteCall.class);
    }
}
