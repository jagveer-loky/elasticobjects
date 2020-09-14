package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
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

    public ScsConfig(final EOConfigsCache provider, Map map)  {
        super(provider, map);
        this.fieldDelimiter = map.containsKey(FIELD_DELIMITER) ? (String) map.get(FIELD_DELIMITER) : ";";
        this.rowDelimiter = map.containsKey(ROW_DELIMITER) ? (String) map.get(ROW_DELIMITER) : "\n";
        this.listParams = map.containsKey(ListInterface.LIST_PARAMS) ? new ListParams((Map)map.get(ListInterface.LIST_PARAMS)) : new ListParams();
    }

    public void resolve()  {
        if (isResolved()) {
            return;
        }
        super.resolve();
    }

    public Object read(ListReadCall readCall) {
        return null;
    }

    public List readRaw(ListParams params) {
        super.resolve();
        String content = (String) super.read();
        if (content == null|| content.isEmpty()) {
            return new ArrayList<>();
        }
        String[] rows = content.split(rowDelimiter);
        List result = new ArrayList<>();

        if (params.hasRowHead(rows.length)) {
            String header = rows[params.getRowHead()];
            if (header!=null && !header.isEmpty()) {
                String[] fields = header.split(fieldDelimiter);
                if (!params.hasColKeys()) {
                    params.setColKeys(Arrays.asList(fields));
                }
            }
        }
        for (int i=0; i<rows.length;i++) {
            String row = rows[i];
            if (row == null || row.isEmpty()) {
                continue;
            }
            if (!params.isRowStart(i)) {
                continue;
            }
            if (!params.isRowEnd(i)) {
                return result;
            }
            if (row == null|| row.isEmpty()) {
                continue;
            }
            String[] fields = row.split(fieldDelimiter);
            List rowEntry = Arrays.asList(fields);
            addRowEntry(result, rowEntry, params);
        }
        return result;
    }



    public Object read(ScsReadCall readCall) {
        EO eo = new EoRoot(getConfigsCache());
        read(eo, readCall);
        return eo.get();
    }

    public ListParams getListParams() {
        return listParams;
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
