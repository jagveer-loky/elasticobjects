package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Werner on 08.10.2016.
 */
public class XlsxReadCallWriteTest {
    private static final Logger LOG = LogManager.getLogger(XlsxReadCallWriteTest.class);

    /*
    @Ignore
    //TODO Some errors occured if tmp file does not exist (Workbook create but sheet is null)
    @Test
    public void withTarget()  {
        
        final EO adapter = TestEOProvider.createEOBuilder()
                .setLogLevel(LogLevel.WARN)
                .map(ListProviderJSON.createJsonArray());

        Map attributes = new HashMap();

        final ExecutorCall executor = TestXlsxProvider.createExecutorXlsxActionWrite(attributes, FILE_TARGET_XLSX);
        executor.execute(adapter);

        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + adapter.getLog(), adapter.getLog().isEmpty());
    }
    */

}
