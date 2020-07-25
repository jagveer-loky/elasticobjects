package org.fluentcodes.projects.elasticobjects.calls.files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.config.ModelConfig;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 23.8.2020.
 */
public class FileCallWriteTest {
    @Test
    public void testFindModelCall()  {
        ModelConfig model = TestProviderRootTest.EO_CONFIGS.findModel("FileCallWrite");
        Assertions.assertThat(model).isNotNull();
        model.resolve();
        FileCallWrite call =  (FileCallWrite)model.create();
        Assertions.assertThat(call).isNotNull();
    }
}
