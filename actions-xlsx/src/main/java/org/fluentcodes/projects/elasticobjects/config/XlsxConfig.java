package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

/**
 * Created by Werner on 30.10.2016.
 */
public class XlsxConfig extends ListConfig {

    private final String xlsxKey;
    private final String fileKey;
    private final String sheetName;
    private FileConfig fileCache;

    protected XlsxConfig(final EOConfigsCache provider, Builder bean) {
        super(provider, bean);

        this.xlsxKey = bean.xlsxKey;
        this.fileKey = bean.fileKey;
        this.sheetName = bean.sheetName;
        if (bean.isExpanded()) {
            //TODO this.fileCache = new FileConfig(provider, bean);
        }
    }
//</call>

    public String getKey() {
        return xlsxKey;
    }

    /**
     * A key for the sheet.
     */
    public String getXlsxKey() {
        return this.xlsxKey;
    }

    /**
     * A key for file objects.
     */
    public String getFileKey() {
        return this.fileKey;
    }

    /**
     * The field for fileConfig e.g. defined in {@link FileConfig}
     */
    public FileConfig getFileConfig()  {
        if (this.fileCache == null) {
            if (this.getConfigsCache() == null) {
                throw new Exception("Config could not be initialized with a null provider for 'fileCache' - 'fileKey''!");
            }
            this.fileCache = (FileConfig) getConfigsCache().find(FileConfig.class, fileKey);
        }
        return this.fileCache;
    }

    /**
     * A name for the sheet selected.
     */
    public String getSheetName() {
        return this.sheetName;
    }

    public Workbook readWorkbook()  {
        URL url = getFileConfig().getUrl();
        if (url == null) {
            throw new Exception("Could not load url from " + getFileConfig().getFileKey());
        }
        InputStream inp = url.openStream();

        Workbook wb = null;
        return WorkbookFactory.create(inp);
    }


//</call>

    public Sheet getSheet()  {
        return getSheet(readWorkbook());
    }

    public boolean hasNoSheetName() {
        return getSheetName() == null || getSheetName().isEmpty();
    }

    protected Sheet getSheet(final Workbook wb) {
        if (hasNoSheetName()) {
            return wb.getSheetAt(0);
        } else {
            return wb.getSheet(getSheetName());
        }
    }

    public XlsxIO createIO()  {
        return new XlsxIO(this);
    }

    public enum KEYS {
        xlsxKey, fileKey, sheetName
    }

    public static class Builder extends ListConfig.Builder {
        //<call keep="JAVA" templateKey="BeanInstanceVars.tpl">

        private String xlsxKey;
        private String fileKey;
        private String sheetName;
//</call>

        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values)  {
            xlsxKey = ScalarConverter.toString(values.get(KEYS.xlsxKey.name()));
            if (xlsxKey == null) {
                throw new Exception("No xlsx key defined - No build process possible");
            }
            fileKey = ScalarConverter.toString(values.get(KEYS.fileKey.name()));
            sheetName = ScalarConverter.toString(values.get(KEYS.sheetName.name()));
            if (sheetName == null || sheetName.isEmpty()) {
                if (xlsxKey.contains(":")) {
                    sheetName = xlsxKey.replaceAll(".+:", "");
                }
            }
            if (fileKey == null || fileKey.isEmpty()) {
                if (xlsxKey.contains(":")) {
                    fileKey = xlsxKey.replaceAll(":.+", "");
                } else {
                    fileKey = xlsxKey;
                }
            }
            super.prepare(configsCache, values);
        }

        public XlsxConfig build(EOConfigsCache configsCache, Map<String, Object> values)  {
            prepare(configsCache, values);
            return new XlsxConfig(configsCache, this);
        }
    }

}
