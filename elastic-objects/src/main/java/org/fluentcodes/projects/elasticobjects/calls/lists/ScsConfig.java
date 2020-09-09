package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A simple character separated file.
 *
 * @author Werner Diwischek
 * @since 09.10.2017.
 */
public class ScsConfig extends ListConfig {
    private final String fileKey;
    private final String fieldDelimiter;
    private final String rowDelimiter;
    private FileConfig fileConfig;

    public ScsConfig(final EOConfigsCache provider, Builder builder)  {
        super(provider, builder);
        this.fileKey = builder.fileKey;
        this.fieldDelimiter = builder.fieldDelimiter;
        this.rowDelimiter = builder.rowDelimiter;
    }

    public void resolve()  {
        if (isResolved()) {
            return;
        }
        super.resolve();
        if (!hasFileKey()) {
            throw new EoException("No fileKey defined for SCS " + getKey());
        }
        fileConfig = getConfigsCache().findFile(fileKey);
    }

    public List<List<String>> read() {
        resolve();
        fileConfig.resolve();
        String content = (String) fileConfig.read();
        if (content == null|| content.isEmpty()) {
            return new ArrayList<>();
        }
        String[] rows = content.split(rowDelimiter);
        List<List<String>> result = new ArrayList<>();
        for (String row:rows) {
            if (row == null|| row.isEmpty()) {
                continue;
            }
            String[] fields = row.split(fieldDelimiter);
            result.add(Arrays.asList(fields));
        }
        return result;
    }

    public boolean hasFileKey() {
        return this.fileKey!=null && !this.fileKey.isEmpty();
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
        if (this.fileConfig == null) {
            if (this.getConfigsCache() == null) {
                throw new EoException("Config could not be initialized with a null provider for 'fileCache' - 'fileKey''!");
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




    public static class Builder extends ListConfig.Builder {
        private String fileKey;
        private String fieldDelimiter;
        private String rowDelimiter;

        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values)  {
            fileKey = ScalarConverter.toString(values.get(EO_STATIC.F_FILE_KEY));
            rowDelimiter = ScalarConverter.toString(values.get(EO_STATIC.F_ROW_DELIMITER));
            fieldDelimiter = ScalarConverter.toString(values.get(EO_STATIC.F_FIELD_DELIMITER));
            if (fileKey == null || fileKey.isEmpty()) {
                fileKey = ScalarConverter.toString(values.get(NATURAL_ID));
            }
            if (fieldDelimiter == null || fieldDelimiter.isEmpty()) {
                fieldDelimiter = ";";
            }
            if (rowDelimiter == null || rowDelimiter.isEmpty()) {
                rowDelimiter = "\n";
            }
            super.prepare(configsCache, values);
        }

        public Config build(EOConfigsCache configsCache, Map<String, Object> values)  {
            prepare(configsCache, values);
            return new ScsConfig(configsCache, this);
        }
    }
}
