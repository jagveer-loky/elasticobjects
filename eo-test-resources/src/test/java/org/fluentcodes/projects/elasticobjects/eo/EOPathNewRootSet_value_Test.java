package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EOPathNewRootSet_value_Test {
    private static final Logger LOG = LogManager.getLogger(EOPathNewRootSet_value_Test.class);

    @Test
    public void mapInteger_ok()  {
        EO eoMapInteger = EOTest.set_ok(F_UNTYPED_MAP, MapProvider.createInteger(), LinkedHashMap.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER, eoMapInteger.get(F_TEST_INTEGER));
    }

    @Test
    public void listWithPath2()  {
        EO adapter = TestEOProvider.createEmptyMap();
        List<String> list = ListProvider.toList(S_STRING);
        adapter.add(S_PATH2)
                .set(list);
        Assert.assertEquals(S_STRING, adapter.get(toPath(S_PATH2, S0)));
    }
}

