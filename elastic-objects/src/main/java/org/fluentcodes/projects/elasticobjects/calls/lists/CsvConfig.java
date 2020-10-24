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

}
