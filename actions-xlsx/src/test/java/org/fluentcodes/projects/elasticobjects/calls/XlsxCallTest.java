package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ListProviderJSON;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC_TEST.*;

/**
 * Created by Werner on 08.10.2016.
 */
public class XlsxCallTest {
    private static final Logger LOG = LogManager.getLogger(XlsxCallTest.class);

    @Test
    public void readSourceDirect()  {
        
        XlsxCall action = new XlsxCall(ProviderRootTest.EO_CONFIGS, X_SOURCE_XLSX_TEST);

        Assert.assertEquals(X_SOURCE_XLSX_TEST, action.getXlsxConfig().getXlsxKey());
        Assert.assertEquals(FILE_SOURCE_XLSX, action.getFileCache().getFileKey());
        Assert.assertEquals(S_STRING, action.getXlsxConfig().getSheetName());

        Assert.assertNull(INFO_NULL_FAILS + F_ROW_START, action.getRowStart());

        Assert.assertEquals(FILE_SOURCE_XLSX, action.getFileCache().getFileKey());
        Assert.assertEquals(FILE_SOURCE_XLSX, action.getFileCache().getFileName());
        Assert.assertEquals(CONFIG_PATH_TEST_SIMPLE, action.getFileCache().getFilePath());

        Assert.assertEquals(H_LOCALHOST, action.getHostCache().getHostKey());

        Assert.assertNotNull(INFO_NOT_NULL_FAILS, action.getXlsxConfig().getDescription());

        EO adapter = TestEOProvider.createEmptyMap();
        action.read(adapter);
        Assert.assertEquals(S_VALUE11, adapter.get(S_PATH0_KEY1));
        //TODO colKey dont serialize?!
        //AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, call);
    }

    @Test
    public void readSourceOr()  {
        
        XlsxCall action = new XlsxCall(ProviderRootTest.EO_CONFIGS, X_SOURCE_XLSX_TEST);
        action.setFilter(toEq(S0, S_VALUE21));

        EO adapter = TestEOProvider.createEmptyMap();
        action.read(adapter);
        Assert.assertEquals(S_VALUE21, adapter.get(S_PATH0_KEY1));
        //TODO colKey dont serialize?!
        //AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, call);
    }

    @Ignore
    //TODO Some errors occured if tmp file does not exist (Workbook create but sheet is null)
    @Test
    public void writeDirect()  {
        
        EO adapter = TestEOProvider.createEOBuilder()
                .map(ListProviderJSON.createJsonArray());
        XlsxCall action = new XlsxCall(ProviderRootTest.EO_CONFIGS, FILE_TARGET_XLSX);
        action.write(adapter);
    }
}
