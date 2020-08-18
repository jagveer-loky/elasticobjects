package org.fluentcodes.projects.elasticobjects.calls.csv;

import au.com.bytecode.opencsv.CSVReader;
import org.fluentcodes.projects.elasticobjects.CEO_STATIC;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 09.10.2016.
 */
public class CsvConfig extends ListConfig {
    private final String fileKey;
    private final String fieldDelimiter;
    private final String rowDelimiter;
    private FileConfig fileConfig;

    public CsvConfig(final EOConfigsCache configsCache, Builder builder)  {
        super(configsCache, builder);
        this.fileKey = builder.fileKey;
        this.fieldDelimiter = builder.fieldDelimiter;
        this.rowDelimiter = builder.rowDelimiter;
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

    public List<List<String>> read() {
        resolve();
        URL url = getFileConfig().getUrl();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new InputStreamReader(url.openStream()), getFieldDelimiter().charAt(0));
        } catch (IOException e) {
            throw new EoException(e);
        }
        List<List<String>> result = new ArrayList<>();
// https://stackoverflow.com/questions/10264054/assign-variable-in-java-while-loop-conditional
        try {
            String[] row = null;
            while ((row = reader.readNext()) !=null) {
                result.add(Arrays.asList(row));
            }
        }
        catch (IOException e) {
            throw new EoException(e);
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                reader = null;
                throw new EoException(e);
            }
            reader = null;
        }
        return result;
    }

    public void write(List rows)  {
        resolve();
        if (rows == null || rows.isEmpty()) {
            throw new EoException("Strange - no list values - nothing to write! Will return without doing anything.");
        }
        StringBuilder buffer = new StringBuilder();
        for (Object row : rows) {
            if (row == null) {
                buffer.append(getRowDelimiter());
                continue;
            }
            if (!(row instanceof List)) {
                buffer.append(getRowDelimiter());
                continue;
            }
            List rowList = (List) row;
            if (rowList.isEmpty()) {
                buffer.append(getRowDelimiter());
                continue;
            }
            for (int i = 0; i < rowList.size(); i++) {
                Object entry = rowList.get(i);
                if (entry == null) {
                    buffer.append(getFieldDelimiter());
                }
                buffer.append(entry.toString());
                if (i + 1 < rowList.size()) {
                    buffer.append(getFieldDelimiter());
                }
            }
            buffer.append(getRowDelimiter());
        }
        fileConfig.write(buffer.toString());
    }


    public static class Builder extends ListConfig.Builder {
        private String fileKey;
        private String fieldDelimiter;
        private String rowDelimiter;

        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values)  {
            fileKey = ScalarConverter.toString(values.get(F_FILE_KEY));
            fileKey = ScalarConverter.toString(values.get(F_FILE_KEY));
            rowDelimiter = ScalarConverter.toString(values.get(F_ROW_DELIMITER));
            fieldDelimiter = ScalarConverter.toString(values.get(F_FIELD_DELIMITER));
            if (fileKey == null || fileKey.isEmpty()) {
                fileKey = ScalarConverter.toString(values.get(NATURAL_ID));
            }
            if (fieldDelimiter == null || fieldDelimiter.isEmpty()) {
                fieldDelimiter = CON_SEMICOLON;
            }
            if (rowDelimiter == null || rowDelimiter.isEmpty()) {
                rowDelimiter = CON_NEWLINE;
            }
            super.prepare(configsCache, values);
        }

        public Config build(EOConfigsCache configsCache, Map<String, Object> values)  {
            prepare(configsCache, values);
            return new CsvConfig(configsCache, this);
        }
    }
}
