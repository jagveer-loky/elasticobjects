package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TestCsvProvider;
import org.fluentcodes.projects.elasticobjects.calls.ExecutorCall;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ListProviderJSON;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestEOProvider;

import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.CEO_STATIC_TEST.CSV_TARGET_CSV;

/**
 * Created by Werner on 08.10.2016.
 */
public class CsvCallWithExecutorWriteTest {
    private static final Logger LOG = LogManager.getLogger(CsvCallWithExecutorWriteTest.class);

    @Test
    public void direct()  {
        
        final EO adapter = TestEOProvider.createEOBuilder()
                .map(ListProviderJSON.createJsonArray());
        final ExecutorCall executor = TestCsvProvider.createCallExecutorWrite(CSV_TARGET_CSV);
        executor.execute(adapter);
        //TODO //AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, executor);
    }

}
