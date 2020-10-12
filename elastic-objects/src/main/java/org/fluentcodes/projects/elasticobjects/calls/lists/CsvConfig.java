package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 09.10.2016.
 */
public class CsvConfig extends FileConfig {
    public static final String FIELD_DELIMITER = "fieldDelimiter";
    public static final String ROW_DELIMITER = "rowDelimiter";
    private final String fieldDelimiter;
    private final String rowDelimiter;

    public CsvConfig(final EOConfigsCache configsCache, Map map)  {
        super(configsCache, map);
        this.fieldDelimiter = map.containsKey(FIELD_DELIMITER) ? (String) map.get(FIELD_DELIMITER) : ";";
        this.rowDelimiter = map.containsKey(ROW_DELIMITER) ? (String) map.get(ROW_DELIMITER) : "\n";
    }

    public List readRaw(ListParams params) {
        throw new EoException("Deprecated");
    }

    public Object read(CsvSimpleReadCall readCall) {
        throw new EoException("Deprecated");
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
        super.write(buffer.toString());
    }
}
