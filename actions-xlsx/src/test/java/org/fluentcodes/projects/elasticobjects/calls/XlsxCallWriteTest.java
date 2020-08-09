package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TestXlsxProvider;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ListProviderJSON;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestEOProvider;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_LOG_EMPTY_FAILS;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC_TEST.FILE_TARGET_XLSX;

/**
 * Created by Werner on 08.10.2016.
 */
public class XlsxCallWriteTest {
    private static final Logger LOG = LogManager.getLogger(XlsxCallWriteTest.class);

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
}
