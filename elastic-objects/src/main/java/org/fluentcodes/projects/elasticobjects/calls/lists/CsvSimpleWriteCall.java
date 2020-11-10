package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.files.FileWriteCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.util.List;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class CsvSimpleWriteCall extends FileWriteCall implements ListInterface  {
    ListParams listParams;
    public CsvSimpleWriteCall()  {
        super();
        listParams = new ListParams();
    }

    @Override
    public ListParams getListParams() {
        return listParams;
    }

    @Override
    public String execute(EO eo) {
        return write(eo);
    }

    public String write(EO eo)  {
        CsvConfig config = (CsvConfig) init(PermissionType.READ, eo);
        getListParams().merge(config.getProperties());
        List rows = (List)eo.get();
        if (rows == null || rows.isEmpty()) {
            throw new EoException("Strange - no list values - nothing to write! Will return without doing anything.");
        }
        StringBuilder buffer = new StringBuilder();
        for (Object row : rows) {
            if (row == null) {
                buffer.append(config.getRowDelimiter());
                continue;
            }
            if (!(row instanceof List)) {
                buffer.append(config.getRowDelimiter());
                continue;
            }
            List rowList = (List) row;
            if (rowList.isEmpty()) {
                buffer.append(config.getRowDelimiter());
                continue;
            }
            for (int i = 0; i < rowList.size(); i++) {
                Object entry = rowList.get(i);
                if (entry == null) {
                    buffer.append(config.getFieldDelimiter());
                }
                buffer.append(entry.toString());
                if (i + 1 < rowList.size()) {
                    buffer.append(config.getFieldDelimiter());
                }
            }
            buffer.append(config.getRowDelimiter());
        }
        setContent(buffer.toString());
        return super.execute(eo);
    }



}
