package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.files.FileWriteCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.util.List;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class CsvSimpleWriteCall extends ListWriteCall  {
    public CsvSimpleWriteCall()  {
        super();
    }

    protected CsvConfig getCsvConfig() {
        if (getConfig() instanceof CsvConfig) {
            return (CsvConfig) getConfig();
        }
        throw new EoException("Could not cast to 'CsvConfig': " + getConfig().getClass().getSimpleName());
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return CsvConfig.class;
    }

    @Override
    public void write(EO eo, List rows)  {
        if (rows == null || rows.isEmpty()) {
            throw new EoException("Strange - no list values - nothing to write! Will return without doing anything.");
        }
        StringBuilder buffer = new StringBuilder();
        for (Object row : rows) {
            if (row == null) {
                buffer.append(getCsvConfig().getRowDelimiter());
                continue;
            }
            if (!(row instanceof List)) {
                buffer.append(getCsvConfig().getRowDelimiter());
                continue;
            }
            List rowList = (List) row;
            if (rowList.isEmpty()) {
                buffer.append(getCsvConfig().getRowDelimiter());
                continue;
            }
            for (int i = 0; i < rowList.size(); i++) {
                Object entry = rowList.get(i);
                if (entry == null) {
                    buffer.append(getCsvConfig().getFieldDelimiter());
                }
                buffer.append(entry.toString());
                if (i + 1 < rowList.size()) {
                    buffer.append(getCsvConfig().getFieldDelimiter());
                }
            }
            buffer.append(getCsvConfig().getRowDelimiter());
        }
        FileWriteCall call = new FileWriteCall(getConfigKey(), buffer.toString());
        call.execute(eo);
    }



}
