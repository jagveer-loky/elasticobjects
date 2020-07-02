package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TestXlsxProvider;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.LogLevel;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.test.ListProviderJSON;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
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
public class XlsxCallWriteTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(XlsxCallWriteTest.class);

    @Ignore
    //TODO Some errors occured if tmp file does not exist (Workbook create but sheet is null)
    @Test
    public void withTarget() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = TestEOProvider.createEOBuilder()
                .setLogLevel(LogLevel.WARN)
                .map(ListProviderJSON.createJsonArray());

        Map attributes = new HashMap();

        final CallExecutor executor = TestXlsxProvider.createExecutorXlsxActionWrite(attributes, FILE_TARGET_XLSX);
        executor.execute(adapter);

        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + adapter.getLog(), adapter.getLog().isEmpty());
    }
}
