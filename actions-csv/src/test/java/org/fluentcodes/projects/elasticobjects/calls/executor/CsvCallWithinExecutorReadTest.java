package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TestCsvProvider;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;


import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.CEO_STATIC_TEST.CSV_SOURCE_CSV;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 08.10.2016.
 */
public class CsvCallWithinExecutorReadTest {
    private static final Logger LOG = LogManager.getLogger(CsvCallWithinExecutorReadTest.class);
    private static final String SAMPLE_MAP_PATH = "$[key1]";
    private static final String A = "A";
    private static final String B = "B";
    private static final String SAMPLE_MAP_PATH_A = "$[A]";

    @Test
    public void direct()  {
        EO adapter = TestCsvProvider.executeRead(new HashMap(), CSV_SOURCE_CSV);

        Assert.assertEquals(S_VALUE11, adapter.get(S_PATH0_KEY1));
        Assert.assertEquals(S_VALUE12, adapter.get(S_PATH0_KEY2));
    }


    @Test
    public void withMapPath()  {
        final Map attributes = new HashMap();
        attributes.put(F_PATH, S_PATH1);
        attributes.put(F_MAP_PATH, SAMPLE_MAP_PATH);

        final EO adapter = TestCsvProvider.executeRead(attributes, CSV_SOURCE_CSV);

        Assert.assertEquals(S_VALUE11, adapter.get(S_PATH1, S_VALUE11, S_KEY1));
        Assert.assertEquals(S_VALUE12, adapter.get(toPath(S_PATH1, S_VALUE11, S_KEY2)));
        Assert.assertEquals(S_VALUE21, adapter.get(toPath(S_PATH1, S_VALUE21, S_KEY1)));
        Assert.assertEquals(S_VALUE22, adapter.get(toPath(S_PATH1, S_VALUE21, S_KEY2)));
        //AssertEO.compare(adapter);
    }

    @Test
    public void withColKeys()  {
        final Map attributes = new HashMap();
        attributes.put(F_PATH, S_PATH1);
        attributes.put(F_MAP_PATH, SAMPLE_MAP_PATH_A);
        attributes.put(F_COL_KEYS, join(CON_COMMA, A, B));

        final EO adapter = TestCsvProvider.executeRead(attributes, CSV_SOURCE_CSV);

        Assert.assertEquals(S_VALUE11, adapter.get(toPath(S_PATH1, S_VALUE11, A)));
        Assert.assertEquals(S_VALUE12, adapter.get(toPath(S_PATH1, S_VALUE11, B)));
        Assert.assertEquals(S_VALUE21, adapter.get(toPath(S_PATH1, S_VALUE21, A)));
        Assert.assertEquals(S_VALUE22, adapter.get(toPath(S_PATH1, S_VALUE21, B)));
        //AssertEO.compare(adapter);
    }
}
