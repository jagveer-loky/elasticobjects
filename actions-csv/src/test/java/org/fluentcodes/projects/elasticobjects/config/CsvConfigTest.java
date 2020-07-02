package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.ListParams;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.CEO_STATIC.CONFIG_CSV_TEST;
import static org.fluentcodes.projects.elasticobjects.CEO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.H_LOCALHOST;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 11.10.2016.
 */
public class CsvConfigTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(CsvConfigTest.class);

    @Test
    public void fromSourceCsv() throws Exception {
        TestHelper.printStartMethod();
        CsvConfig config = (CsvConfig) TestEOProvider.EO_CONFIGS.find(CsvConfig.class, CSV_SOURCE_CSV);
        Assert.assertEquals(CSV_SOURCE_CSV, config.getCsvKey());
        Assert.assertEquals(S_ROW_DELIMITER, config.getRowDelimiter());
        Assert.assertEquals(S_FIELD_DELIMITER, config.getFieldDelimiter());
        Assert.assertEquals(FILE_SOURCE_CSV, config.getFileKey());
        Assert.assertEquals(CSV_SOURCE_CSV, config.getFileConfig().getFileName());
        Assert.assertEquals(H_LOCALHOST, config.getFileConfig().getHostConfig().getHostName());
        List list = config.createIO().read(new ListParams());
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, ((List) list.get(0)).size());
    }

    @Test
    public void fromTargetCsv() throws Exception {
        TestHelper.printStartMethod();
        final CsvConfig config = (CsvConfig) TestEOProvider.EO_CONFIGS.find(CsvConfig.class, CSV_TARGET_CSV);
        Assert.assertEquals(CSV_TARGET_CSV, config.getCsvKey());
        Assert.assertEquals(S_ROW_DELIMITER, config.getRowDelimiter());
        Assert.assertEquals(S_FIELD_DELIMITER, config.getFieldDelimiter());
        Assert.assertEquals(FILE_TARGET_CSV, config.getFileKey());
        Assert.assertEquals(CSV_TARGET_CSV, config.getFileConfig().getFileName());
        Assert.assertEquals(H_LOCALHOST, config.getFileConfig().getHostConfig().getHostName());
        List row = Arrays.asList(new Object[]{S_VALUE11, S_VALUE12});
        List rows = new ArrayList();
        rows.add(row);
        config.createIO().write(rows);

    }


    @Test
    public void fromCache() throws Exception {
        TestHelper.printStartMethod();
        final CsvConfig config = (CsvConfig) TestEOProvider.EO_CONFIGS.find(CsvConfig.class, S_STRING);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
        Assert.assertEquals(S_STRING, config.getCsvKey());
    }

    @Test
    public void readMapTest() throws Exception {
        Map map = TestConfig.readMapFromFile(CONFIG_CSV_TEST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
    }

    @Test
    public void readConfigTest() throws Exception {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_CSV_TEST, CsvConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
    }

}
