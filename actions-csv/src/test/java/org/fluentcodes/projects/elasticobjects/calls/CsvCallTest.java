package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.ListProviderJSON;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.CEO_STATIC_TEST.CSV_SOURCE_CSV;
import static org.fluentcodes.projects.elasticobjects.CEO_STATIC_TEST.CSV_TARGET_CSV;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * These Tests will checks all CsvActions.
 * Created by Werner on 11.10.2016.
 */
public class CsvCallTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(CsvCallTest.class);

    private CsvCall createActionWithSourceCsv() throws Exception {
        CsvCall action = new CsvCall(TestEOProvider.EO_CONFIGS, CSV_SOURCE_CSV);
        return action;
    }

    @Test
    public void withSourceCsv() throws Exception {
        TestHelper.printStartMethod();
        CsvCall action = createActionWithSourceCsv();
        Assert.assertEquals(CSV_SOURCE_CSV, action.getCsvConfig().getFileConfig().getFileName());
        EO adapter = TestEOProvider.createEmptyMap();
        action.read(adapter);
        Assert.assertEquals(S_VALUE11, adapter.get(S_PATH0_KEY1));
        Assert.assertEquals(S_VALUE12, adapter.get(S_PATH0_KEY2));
        Assert.assertEquals(S_VALUE21, adapter.get(S_PATH1_KEY1));
        Assert.assertEquals(S_VALUE22, adapter.get(S_PATH1_KEY2));
        //AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, call);
    }

    @Test
    public void withSourceCsv_IgnoreHeader() throws Exception {
        CsvCall action = createActionWithSourceCsv();
        action.setIgnoreHeader(true);
        EO adapter = action.read(TestEOProvider.createEmptyMap());
        Assert.assertEquals(S_VALUE11, adapter.get(S_PATH00));
        AssertEO.compare(adapter);
    }

    @Test
    public void withSourceCsv_IgnoreHeaderSetRowStartTo2() throws Exception {
        CsvCall action = createActionWithSourceCsv();
        action.setIgnoreHeader(true);
        action.setRowStart(2);
        EO adapter = action.read(TestEOProvider.createEmptyMap());
        Assert.assertEquals(S_VALUE21, adapter.get(S_PATH00));
        AssertEO.compare(adapter);
    }

    @Test
    public void withSourceCsv_RemoveRowHeadSetRowEndTo2() throws Exception {
        CsvCall action = createActionWithSourceCsv();
        action.setIgnoreHeader(true);
        action.setRowStart(1);
        action.setRowEnd(2);
        EO adapter = TestEOProvider.createEmptyMap();
        adapter = action.read(adapter);
        Assert.assertEquals(S_VALUE11, adapter.get(S_PATH00));
        AssertEO.compare(adapter);
    }

    @Test
    public void withTargetCsv() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = TestEOProvider.createEOBuilder()
                .map(ListProviderJSON.createRow());
        CsvCall action = new CsvCall(TestEOProvider.EO_CONFIGS, CSV_TARGET_CSV);
        action.write(adapter);
        Assert.assertEquals(CSV_TARGET_CSV, action.getCsvConfig().getFileConfig().getFileName());

    }

}
