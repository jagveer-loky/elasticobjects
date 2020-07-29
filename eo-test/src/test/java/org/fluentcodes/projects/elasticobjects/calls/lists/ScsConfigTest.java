package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.config.FieldConfig;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.SC_TEST;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 11.10.2016.
 */
public class ScsConfigTest {
    private static final Logger LOG = LogManager.getLogger(ScsConfigTest.class);

    @Test
    public void assertScsConfigTest()  {
        ModelInterface model = TestProviderRootTest.findModel(ScsConfig.class);
        Assertions.assertThatThrownBy(() -> {model.create();})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("config has no empty constructor");
        FieldConfig field = model.getFieldConfig(F_DESCRIPTION);
        Assert.assertEquals(String.class, field.getModelClass());
    }

    @Test
    public void testResolveAllConfigs()  {
        Set<String> keys = TestProviderRootTest.EO_CONFIGS.getConfigKeys(ScsConfig.class);
        Assertions.assertThat(keys).isNotEmpty();
        for (String key: keys) {
            LOG.info("Try to resolve " + key);
            Assertions.assertThat(key).isNotEmpty();
            ScsConfig config = (ScsConfig) TestProviderRootTest.EO_CONFIGS.find(ScsConfig.class, key);
            Assertions.assertThat(config).isNotNull();
            config.resolve();
        }
    }

    @Test
    public void fromSourceCsv()  {
        
        final ScsConfig config = (ScsConfig) TestProviderRootTest.EO_CONFIGS.find(ScsConfig.class, CS_SOURCE_CSV);
        Assert.assertEquals(CS_SOURCE_CSV, config.getScsKey());
        Assert.assertEquals(S_ROW_DELIMITER, config.getRowDelimiter());
        Assert.assertEquals(S_FIELD_DELIMITER, config.getFieldDelimiter());
        Assert.assertEquals(CS_SOURCE_CSV, config.getFileKey());
        Assert.assertEquals(CS_SOURCE_CSV_NAME, config.getFileConfig().getFileName());
        Assert.assertEquals(H_LOCALHOST, config.getFileConfig().getHostConfig().getHostName());
        List list = config.createIO().read(new ListParams());
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, ((List) list.get(0)).size());
        final String serialized = config.serialize();
        Assert.assertNotNull(serialized);
        //AssertString.compare(serialized);
    }

    @Test
    public void fromTargetCsv()  {
        
        final ScsConfig config = (ScsConfig) TestProviderRootTest.EO_CONFIGS.find(ScsConfig.class, CS_TARGET_CSV);
        Assert.assertEquals(CS_TARGET_CSV, config.getScsKey());
        Assert.assertEquals(S_ROW_DELIMITER, config.getRowDelimiter());
        Assert.assertEquals(S_FIELD_DELIMITER, config.getFieldDelimiter());
        Assert.assertEquals(CS_TARGET_CSV, config.getFileKey());
        Assert.assertEquals(CS_TARGET_CSV, config.getFileConfig().getFileName());
        Assert.assertEquals(H_LOCALHOST, config.getFileConfig().getHostConfig().getHostName());
        List rows = new ArrayList();
        rows.add(List.of(S_VALUE11, S_VALUE12));
        config.createIO().write(rows);
    }


    @Test
    public void fromTest()  {
        
        final ScsConfig cache = (ScsConfig) TestProviderRootTest.EO_CONFIGS.find(ScsConfig.class, SC_TEST);
        Assert.assertEquals(F_DESCRIPTION, cache.getDescription());

        Assert.assertEquals(F_FILE_NAME, cache.getFileConfig().getFileName());
    }

}
