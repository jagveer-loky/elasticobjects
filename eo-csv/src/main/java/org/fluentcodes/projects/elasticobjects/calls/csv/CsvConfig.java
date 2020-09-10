package org.fluentcodes.projects.elasticobjects.calls.csv;

import au.com.bytecode.opencsv.CSVReader;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.*;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ScsConfig.FIELD_DELIMITER;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ScsConfig.ROW_DELIMITER;

/**
 * Created by Werner on 09.10.2016.
 */
public class CsvConfig extends FileConfig implements ListConfigInterface {
    private final String fieldDelimiter;
    private final String rowDelimiter;
    private final ListParams listParams;
    private final ListMapper listMapper;

    public CsvConfig(final EOConfigsCache configsCache, Map map)  {
        super(configsCache, map);
        this.fieldDelimiter = map.containsKey(FIELD_DELIMITER) ? (String) map.get(FIELD_DELIMITER) : ";";
        this.rowDelimiter = map.containsKey(ROW_DELIMITER) ? (String) map.get(ROW_DELIMITER) : "\n";
        this.listParams = map.containsKey(LIST_PARAMS) ? new ListParams((Map)map.get(LIST_PARAMS)) : new ListParams();
        this.listMapper = map.containsKey(LIST_MAPPER) ? new ListMapper((Map)map.get(LIST_MAPPER)) : new ListMapper();
    }

    @Override
    public ListParams getListParams() {
        return listParams;
    }

    @Override
    public ListMapper getListMapper() {
        return listMapper;
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
        URL url = findUrl();
        //System.out.println("CSV " + url.toString());
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
        super.write(buffer.toString());
    }
}
