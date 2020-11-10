package org.fluentcodes.projects.elasticobjects.calls.csv;

import au.com.bytecode.opencsv.CSVReader;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class CsvReadCall extends CsvSimpleReadCall {
    public CsvReadCall()  {
        super();
    }

    public CsvReadCall(final String configKey)  {
        super(configKey);
    }

    @Override
    public List readRaw(final EO eo) {
        CsvConfig config = (CsvConfig) init(PermissionType.READ, eo);
        getListParams().merge(config.getProperties());
        URL url = config.findUrl();
        //System.out.println("CSV " + url.toString());
        CSVReader reader = null;
        try {
            reader = new CSVReader(new InputStreamReader(url.openStream()), config.getFieldDelimiter().charAt(0));
        } catch (IOException e) {
            throw new EoException(e);
        }
        List result = new ArrayList<>();
// https://stackoverflow.com/questions/10264054/assign-variable-in-java-while-loop-conditional
        try {
            String[] row = null;
            int i = 0;
            while ((row = reader.readNext()) !=null) {
                i++;
                if (row == null || row.length == 0) {
                    continue;
                }
                if (getListParams().isRowHead(i)) {
                    if (!getListParams().hasColKeys()) {
                        getListParams().setColKeys(Arrays.asList(row));
                    }
                    continue;
                }
                if (!getListParams().isRowStart(i)) {
                    continue;
                }
                if (!getListParams().isRowEnd(i)) {
                    return result;
                }
                List rowEntry = Arrays.asList(row);
                getListParams().addRowEntry(eo.getConfigsCache(), result, rowEntry);
            }
        }
        catch (IOException e) {
            throw new EoException(e);
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                throw new EoException(e);
            }
        }
        return result;
    }

}
