package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;

/**
 * A simple character separated file.
 * @author Werner Diwischek
 * @since 09.10.2017.
 */
public class ScsConfig extends ListConfig {
    //<call keep="JAVA" templateKey="CacheInstanceVars.tpl" }
    private final String fileKey;
    private final String scsKey;
    private final String fieldDelimiter;
    private final String rowDelimiter;

    //</call>
    private FileConfig fileConfig;

    public ScsConfig(final EOConfigsCache provider, Builder builder) throws Exception {
        super(provider, builder);

        //<call keep="JAVA" templateKey="CacheSetter.tpl" }
        this.fileKey = builder.fileKey;
        this.scsKey = builder.scsKey;
        this.fieldDelimiter = builder.fieldDelimiter;
        this.rowDelimiter = builder.rowDelimiter;
        //readHead();
    }

    //<call keep="JAVA" templateKey="CacheGetter.tpl" }

    public String getKey() {
        return this.scsKey;
    }

    /**
     * Defines the processing parameters for a csv content stored in a final config object delivered by the provider.
     */
    public String getScsKey() {
        return this.scsKey;
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

    @Override
    public ListIOInterface createIO() throws Exception {
        return new ScsIO(this);
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

//</call>


    public static class Builder extends ListConfig.Builder {
        //<call keep="JAVA" templateKey="BeanInstanceVars.tpl" }

        private String scsKey;
        private String fileKey;
        private String fieldDelimiter;
        private String rowDelimiter;
//</call>

        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values) throws Exception {
            scsKey = ScalarConverter.toString(values.get(EO_STATIC.F_SCS_KEY));
            if (scsKey == null) {
                throw new Exception("No list key defined - No build process possible");
            }
            fileKey = ScalarConverter.toString(values.get(EO_STATIC.F_FILE_KEY));
            rowDelimiter = ScalarConverter.toString(values.get(EO_STATIC.F_ROW_DELIMITER));
            fieldDelimiter = ScalarConverter.toString(values.get(EO_STATIC.F_FIELD_DELIMITER));
            if (fileKey == null || fileKey.isEmpty()) {
                fileKey = scsKey;
            }
            if (fieldDelimiter == null || fieldDelimiter.isEmpty()) {
                fieldDelimiter = ";";
            }
            if (rowDelimiter == null || rowDelimiter.isEmpty()) {
                rowDelimiter = "\n";
            }
            super.prepare(configsCache, values);
        }

        public Config build(EOConfigsCache configsCache, Map<String, Object> values) throws Exception {
            prepare(configsCache, values);
            return new ScsConfig(configsCache, this);
        }
    }
}
