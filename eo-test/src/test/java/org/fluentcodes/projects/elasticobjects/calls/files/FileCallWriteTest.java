package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 23.8.2020.
 */
public class FileCallWriteTest {
    @Test
    public void testFindModelCall()  {
        ModelConfig model = ProviderRootTest.EO_CONFIGS.findModel("FileCallWrite");
        Assertions.assertThat(model).isNotNull();
        model.resolve();
        FileCallWrite call =  (FileCallWrite)model.create();
        Assertions.assertThat(call).isNotNull();
    }
}
