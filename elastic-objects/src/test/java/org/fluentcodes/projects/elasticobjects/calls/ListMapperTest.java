package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.ListProvider;
import org.fluentcodes.projects.elasticobjects.test.MapProvider;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 22.4.2017
 */
public class ListMapperTest extends TestHelper {
  private static final Logger LOG = LogManager.getLogger(ListMapperTest.class);
  private static final Map<String, Object> LIST_ROW_MAPPER = createListMapperMap();

    @Test
  public void init() throws Exception {
    ListMapper rowMapper = new ListMapper(LIST_ROW_MAPPER);
    Assert.assertEquals(F_COL_KEYS,rowMapper.getColKeys().get(0));
    Assert.assertEquals(S_BOOLEAN,rowMapper.isDoMap());
    Assert.assertEquals(S_BOOLEAN,rowMapper.isIgnoreHeader());
      Assert.assertEquals(join(CON_COMMA, List.class.getSimpleName(),Map.class.getSimpleName()), rowMapper.getModelKeys());
      Assert.assertEquals(Path.DELIMITER + F_PATH_PATTERN,rowMapper.getPathPattern().getSerialized());
  }

  @Test
    public void checkList() throws Exception {
      List row = ListProvider.toList(S_STRING, S_INTEGER, S_BOOLEAN);
      ListMapper rowMapper = new ListMapper();
      EO adapter = TestObjectProvider.createEOFromJson();
      rowMapper.createRow(adapter,row);
      Assert.assertEquals(S_STRING, adapter.get(S0));
      Assert.assertEquals(S_INTEGER, adapter.get(S1));
      Assert.assertEquals(S_BOOLEAN, adapter.get(S2));
  }

    @Test
    public void checkListWithColKeys() throws Exception {
        List row = ListProvider.toList(S_STRING, S_INTEGER, S_BOOLEAN);
        Map map = MapProvider.toMap(F_COL_KEYS, join(CON_COMMA,S0,S1,S2));
        ListMapper rowMapper = new ListMapper(map);
        EO adapter = TestObjectProvider.createEOFromJson();
        rowMapper.createRow(adapter,row);
        Assert.assertEquals(S_STRING, adapter.get(S0));
        Assert.assertEquals(S_INTEGER, adapter.get(S1));
        Assert.assertEquals(S_BOOLEAN, adapter.get(S2));
    }

}

