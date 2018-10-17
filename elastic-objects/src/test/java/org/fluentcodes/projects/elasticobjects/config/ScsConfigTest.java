package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.ListParams;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

import org.fluentcodes.projects.elasticobjects.test.ListProvider;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Werner on 11.10.2016.
 */
public class ScsConfigTest extends TestHelper {
  private static final Logger LOG = LogManager.getLogger(ScsConfigTest.class);

  @Test
  public void fromSourceCsv() throws Exception {
    TestHelper.printStartMethod();
    final ScsConfig config = (ScsConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(ScsConfig.class, CS_SOURCE_CSV);
    Assert.assertEquals(CS_SOURCE_CSV, config.getScsKey());
    Assert.assertEquals(S_ROW_DELIMITER, config.getRowDelimiter());
    Assert.assertEquals(S_FIELD_DELIMITER, config.getFieldDelimiter());
    Assert.assertEquals(CS_SOURCE_CSV, config.getFileKey());
    Assert.assertEquals(CS_SOURCE_CSV, config.getFileConfig().getFileName());
    Assert.assertEquals(H_LOCALHOST, config.getFileConfig().getHostConfig().getHostName());
    List list = config.createIO().read(new ListParams());
    Assert.assertEquals(2, list.size());
    Assert.assertEquals(2 , ((List)list.get(0)).size());
  }

  @Test
  public void fromTargetCsv() throws Exception {
    TestHelper.printStartMethod();
    final ScsConfig config = (ScsConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(ScsConfig.class, CS_TARGET_CSV);
    Assert.assertEquals(CS_TARGET_CSV, config.getScsKey());
    Assert.assertEquals(S_ROW_DELIMITER, config.getRowDelimiter());
    Assert.assertEquals(S_FIELD_DELIMITER, config.getFieldDelimiter());
    Assert.assertEquals(CS_TARGET_CSV, config.getFileKey());
    Assert.assertEquals(CS_TARGET_CSV, config.getFileConfig().getFileName());
    Assert.assertEquals(H_LOCALHOST, config.getFileConfig().getHostConfig().getHostName());
    List rows = new ArrayList();
    rows.add(ListProvider.toList(S_VALUE11, S_VALUE12));
    config.createIO().write(rows);
  }


  @Test
  public void fromTest() throws Exception {
    TestHelper.printStartMethod();
    final ScsConfig cache = (ScsConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(ScsConfig.class, SC_TEST);
    Assert.assertEquals(F_DESCRIPTION, cache.getDescription());

    Assert.assertEquals(F_FILE_NAME, cache.getFileConfig().getFileName());
  }

}
