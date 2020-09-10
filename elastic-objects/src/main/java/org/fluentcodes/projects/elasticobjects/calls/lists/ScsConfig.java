package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Configuration for a simple character separated file.
 *
 * @author Werner Diwischek
 * @since 09.10.2017.
 */
public class ScsConfig extends FileConfig implements ListConfigInterface {
    public static final String FIELD_DELIMITER = "fieldDelimiter";
    public static final String ROW_DELIMITER = "rowDelimiter";
    private final String fieldDelimiter;
    private final String rowDelimiter;
    private final ListParams listParams;
    private final ListMapper listMapper;
    private FileConfig fileConfig;

    public ScsConfig(final EOConfigsCache provider, Map map)  {
        super(provider, map);
        this.fieldDelimiter = map.containsKey(FIELD_DELIMITER) ? (String) map.get(FIELD_DELIMITER) : ";";
        this.rowDelimiter = map.containsKey(ROW_DELIMITER) ? (String) map.get(ROW_DELIMITER) : "\n";
        this.listParams = map.containsKey(LIST_PARAMS) ? new ListParams((Map)map.get(LIST_PARAMS)) : new ListParams();
        this.listMapper = map.containsKey(LIST_MAPPER) ? new ListMapper((Map)map.get(LIST_MAPPER)) : new ListMapper();

    }

    public void resolve()  {
        if (isResolved()) {
            return;
        }
        super.resolve();
    }
    @Override
    public List<List<String>> read() {
        super.resolve();
        String content = (String) super.read();
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

    @Override
    public ListParams getListParams() {
        return listParams;
    }

    @Override
    public ListMapper getListMapper() {
        return listMapper;
    }
}
