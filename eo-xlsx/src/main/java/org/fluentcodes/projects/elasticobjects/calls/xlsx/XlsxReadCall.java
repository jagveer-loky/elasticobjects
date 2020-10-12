package org.fluentcodes.projects.elasticobjects.calls.xlsx;


import org.apache.poi.ss.usermodel.Sheet;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads and writes Excelsheets.
 * Created by werner.diwischek on 18.12.17.
 */
public class XlsxReadCall extends ListReadCall {

    public XlsxReadCall()  {
        super();
    }

    public XlsxReadCall(final String configKey)  {
        super(configKey);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }

    public Object execute(EO eo) {
        init(eo);
        getListParams().merge(getConfig().getProperties());
        return read(eo, readRaw(eo));
    }

    protected XlsxConfig getXlsxConfig() {
        if (getConfig() instanceof XlsxConfig) {
            return (XlsxConfig) getConfig();
        }
        throw new EoException("Could not cast to 'CsvConfig': " + getConfig().getClass().getSimpleName());
    }

    public List readRaw(final EO eo) {
        List result = new ArrayList<>();
        Sheet sheet = getXlsxConfig().getSheet();
        if (sheet == null) {
            throw new EoException("The sheet for '" + getNaturalId() + "' is null. Perhaps the sheet name '" + getXlsxConfig().getSheetName() + "' is undefined.");
        }
        List rowEntry;
        int i = -1;
        while((rowEntry = getXlsxConfig().getRowAsList(sheet.getRow(i+1))) != null) {
            i++;
            if (getListParams().isRowHead(i)) {
                if (!getListParams().hasColKeys()) {
                    getListParams().setColKeys(rowEntry);
                }
                continue;
            }
            if (!getListParams().isRowStart(i)) {
                continue;
            }
            if (!getListParams().isRowEnd(i)) {
                return result;
            }
            try {
                addRowEntry(eo.getConfigsCache(), result, rowEntry, getListParams());
            }
            catch (Exception e) {
                throw new EoInternalException("Problem with row " + i + ": " + rowEntry + "", e);
            }
        }

        try {
            sheet.getWorkbook().close();
        } catch (IOException e) {
            throw new EoException(e);
        }
        return result;
    }
}
