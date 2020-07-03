package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.calls.ListParams;

import java.util.ArrayList;
import java.util.List;

public abstract class ListIO implements ListIOInterface {
    ListConfig listConfig;

    protected ListIO(ListConfig listConfig) {
        this.listConfig = listConfig;
    }

    protected ListConfig getListConfig() {
        return listConfig;
    }

    /**
     * Reads a csv file with the help of th @{link CSVReader} to the current adapter.
     *
     * @param listParams
     * @return
     * @
     */

    public List read(ListParams listParams)  {
        listParams.merge(listConfig.getListParams());
        listParams.prepare();
        int lineCounter = -1;
        List result = new ArrayList();
        Object nextObject = readRow(listParams.getRowStart());
        while (nextObject != null) {
            lineCounter++;
            if (listParams.getRowEnd() > -1 && listParams.getRowEnd() < lineCounter) {
                break;
            }
            if (nextObject instanceof List) {
                List row = (List) nextObject;
                if (listParams.getFilter() == null || listParams.getFilter().filter(row)) {
                    result.add(row);
                }
            } else {
                result.add(nextObject);
            }
            nextObject = readRow();
        }
        close();
        return result;
    }
}
