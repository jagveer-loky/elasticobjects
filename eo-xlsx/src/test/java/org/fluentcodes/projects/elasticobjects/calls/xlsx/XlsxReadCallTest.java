package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 08.10.2016.
 */
public class XlsxReadCallTest {
    private static final String LIST_SIMPLE_XLSX = "ListSimple.xlsx:test";

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(XlsxReadCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(XlsxReadCall.class);
    }

    @Test
    public void resolveModelConfig()  {
        ConfigModelChecks.resolve(XlsxConfig.class);
    }

    @Test
    public void call_ListSimpleXlsx__execute__listReturned()  {
        final Call call = new XlsxReadCall()
                .setConfigKey(LIST_SIMPLE_XLSX);
        EO eo = ProviderRootTestScope.createEo();
        List value = (List)call.execute(eo);
        Assertions.assertThat(value).isNotEmpty();
        Assertions.assertThat(value.size()).isEqualTo(2);
        Map firstRow = (Map) value.get(0);
        Assertions.assertThat(firstRow.size()).isEqualTo(2);
        Assertions.assertThat(firstRow.get("key1")).isEqualTo("value11");
    }

    @Test
    public void givenEoWithListSimpleXlx_whenExecute_thenParameterSet()  {
        final Call call = new XlsxReadCall()
                .setConfigKey(LIST_SIMPLE_XLSX);

        EO eo = ProviderRootTestScope.createEoWithClasses(List.class);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.size()).isEqualTo(2);
        Map firstRow = (Map) eo.get("0");
        Assertions.assertThat(firstRow.size()).isEqualTo(2);
        Assertions.assertThat(firstRow.get("key1")).isEqualTo("value11");
    }

    /*
    @Test
    public void readSourceDirect()  {
        
        XlsxReadCall action = new XlsxReadCall(ProviderRootTestScope.EO_CONFIGS, X_SOURCE_XLSX_TEST);

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
        
        XlsxReadCall action = new XlsxReadCall(ProviderRootTestScope.EO_CONFIGS, X_SOURCE_XLSX_TEST);
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
        XlsxReadCall action = new XlsxReadCall(ProviderRootTestScope.EO_CONFIGS, FILE_TARGET_XLSX);
        action.write(adapter);
    }
    */
}
