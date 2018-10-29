package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.CEO_STATIC;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 09.10.2016.
 */
public class CsvConfig extends ListConfig {
    //<call keep="JAVA" templateKey="CacheInstanceVars.tpl" }
    private final String fileKey;
    private final String csvKey;
    private final String fieldDelimiter;
    private final String rowDelimiter;
    //</call>
    private FileConfig fileConfig;

    public CsvConfig(final EOConfigsCache configsCache, Builder builder) throws Exception {
        super(configsCache, builder);

        //<call keep="JAVA" templateKey="CacheSetter.tpl" }
        this.fileKey = builder.fileKey;
        this.csvKey = builder.csvKey;
        this.fieldDelimiter = builder.fieldDelimiter;
        this.rowDelimiter = builder.rowDelimiter;
        //readHead();
    }

    @Override
    public CsvIO createIO() throws Exception {
        return new CsvIO(this);
    }

    //<call keep="JAVA" templateKey="CacheGetter.tpl" }

    public String getKey() {
        return this.csvKey;
    }

    /**
     * Defines the processing parameters for a csv content stored in a final config object delivered by the provider.
     */
    public String getCsvKey() {
        return this.csvKey;
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
    public FileConfig getFileConfig() throws Exception {
        if (this.fileConfig == null) {
            if (this.getConfigsCache() == null) {
                throw new Exception("Config could not be initialized with a null provider for 'fileCache' - 'fileKey''!");
            }
            this.fileConfig = (FileConfig) getConfigsCache().find(FileConfig.class, fileKey);
        }
        return this.fileConfig;
    }

    /**
     * Defines the processing parameters for a csv content stored in a final config object delivered by the provider.
     */
    public String getFieldDelimiter() {
        return this.fieldDelimiter;
    }

    /**
     * Defines the processing parameters for a csv content stored in a final config object delivered by the provider.
     */
    public String getRowDelimiter() {
        return this.rowDelimiter;
    }

    public enum KEYS {
        fileKey, csvKey, fieldDelimiter, rowDelimiter
    }

//</call>

    public static class Builder extends ListConfig.Builder {
        //<call keep="JAVA" templateKey="BeanInstanceVars.tpl" }

        private String csvKey;
        private String fileKey;
        private String fieldDelimiter;
        private String rowDelimiter;
//</call>

        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values) throws Exception {
            csvKey = ScalarConverter.toString(values.get(CEO_STATIC.F_CSV_KEY));
            if (csvKey == null) {
                throw new Exception("No csv key defined - No build process possible");
            }
            fileKey = ScalarConverter.toString(values.get(F_FILE_KEY));
            rowDelimiter = ScalarConverter.toString(values.get(F_ROW_DELIMITER));
            fieldDelimiter = ScalarConverter.toString(values.get(F_FIELD_DELIMITER));
            if (fileKey == null || fileKey.isEmpty()) {
                fileKey = csvKey;
            }
            if (fieldDelimiter == null || fieldDelimiter.isEmpty()) {
                fieldDelimiter = CON_SEMICOLON;
            }
            if (rowDelimiter == null || rowDelimiter.isEmpty()) {
                rowDelimiter = CON_NEWLINE;
            }
            super.prepare(configsCache, values);
        }

        public Config build(EOConfigsCache configsCache, Map<String, Object> values) throws Exception {
            prepare(configsCache, values);
            return new CsvConfig(configsCache, this);
        }
    }
}
