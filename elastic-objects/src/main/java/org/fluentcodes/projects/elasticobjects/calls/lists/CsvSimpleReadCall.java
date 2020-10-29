package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class CsvSimpleReadCall extends ListReadCall {

    public CsvSimpleReadCall()  {
        super();
    }
    public CsvSimpleReadCall(final String configKey)  {
        super(configKey);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }

    @Override
    public Object execute(EO eo) {
        init(eo);
        getListParams().merge(getConfig().getProperties());
        return mapEo(eo, readRaw(eo));
    }

    protected CsvConfig getCsvConfig() {
        if (getConfig() instanceof CsvConfig) {
            return (CsvConfig) getConfig();
        }
        throw new EoException("Could not cast to 'CsvConfig': " + getConfig().getClass().getSimpleName());
    }

    public List readRaw(final EO eo) {
        String content = (String) new FileReadCall(getConfigKey()).execute(eo);
        if (content == null|| content.isEmpty()) {
            return new ArrayList<>();
        }
        String[] rows = content.split(getCsvConfig().getRowDelimiter());
        List result = new ArrayList<>();

        if (getListParams().hasRowHead(rows.length)) {
            String header = rows[getListParams().getRowHead()];
            if (header!=null && !header.isEmpty()) {
                String[] fields = header.split(getCsvConfig().getFieldDelimiter());
                if (!getListParams().hasColKeys()) {
                    getListParams().setColKeys(Arrays.asList(fields));
                }
            }
        }
        for (int i=0; i<rows.length;i++) {
            String row = rows[i];
            if (row == null || row.isEmpty()) {
                continue;
            }
            if (!getListParams().isRowStart(i)) {
                continue;
            }
            if (!getListParams().isRowEnd(i)) {
                return result;
            }
            if (row == null|| row.isEmpty()) {
                continue;
            }
            String[] fields = row.split(getCsvConfig().getFieldDelimiter());
            List rowEntry = Arrays.asList(fields);
            getListParams().addRowEntry(eo.getConfigsCache(), result, rowEntry);
        }
        return result;
    }





}
