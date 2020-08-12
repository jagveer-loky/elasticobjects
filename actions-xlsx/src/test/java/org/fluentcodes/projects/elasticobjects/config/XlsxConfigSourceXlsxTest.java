package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_PATH_TEST_SIMPLE;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.H_LOCALHOST;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC_TEST.FILE_SOURCE_XLSX;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC_TEST.X_SOURCE_XLSX_TEST;

/**
 * Created by Werner on 04.11.2016.
 */
public class XlsxConfigSourceXlsxTest {

    @Test
    public void checkSource()  {
        
        XlsxConfig config = (XlsxConfig) ProviderRootTestScope.EO_CONFIGS.find(XlsxConfig.class, X_SOURCE_XLSX_TEST);
        Assert.assertEquals(X_SOURCE_XLSX_TEST, config.getXlsxKey());
        Assert.assertEquals(FILE_SOURCE_XLSX, config.getFileKey());
        Assert.assertEquals(S_STRING, config.getSheetName());

        FileConfig fileCache = config.getFileConfig();
        Assert.assertEquals(FILE_SOURCE_XLSX, fileCache.getFileKey());
        Assert.assertEquals(FILE_SOURCE_XLSX, fileCache.getFileName());
        Assert.assertEquals(CONFIG_PATH_TEST_SIMPLE, fileCache.getFilePath());

        HostConfig hostCache = fileCache.getHostConfig();
        Assert.assertEquals(H_LOCALHOST, hostCache.getHostKey());
    }

    @Test
    public void read()  {
        XlsxConfig config = (XlsxConfig) ProviderRootTestScope.EO_CONFIGS.find(XlsxConfig.class, X_SOURCE_XLSX_TEST);
        //List<List> result = config.createIO().read(new ListParams());
        //AssertEO.compare(TRootTestProvider.EO_CONFIGS, result);
    }

}
