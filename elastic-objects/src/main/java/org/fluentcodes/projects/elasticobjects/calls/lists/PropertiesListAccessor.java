package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserEoReplace;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.PropertiesConfigAccessor;

import java.util.List;
import java.util.Map;

public interface PropertiesListAccessor extends PropertiesConfigAccessor {
    List readRaw(ListParams params);

    default String read(EO eo, ListReadCall readCall) {
        ListParams params = readCall.getListParams();
        params.merge(getProperties());
        List filteredResult = readRaw(params);
        if (filteredResult.isEmpty())  {
            return "";
        }
        String targetPath = readCall.getTargetPath();
        boolean isMapped = targetPath.contains("eo->");
        if (!isMapped) {
            eo.setEmpty(targetPath);
        }
        for (int i = 0; i< filteredResult.size(); i++) {
            Object row = filteredResult.get(i);
            if (isMapped) {
                String target = Parser.replace(targetPath,new EoRoot(eo.getConfigsCache(), row));
                eo.set(row, target);
            }
            else {
                eo.set(row, targetPath, Integer.valueOf(i).toString());
            }
        }
        if (targetPath.equals(Call.TARGET_AS_STRING)) {
            return "TODO asString";
        }
        return "";
    }

    default void addRowEntry(EOConfigsCache configsCache, List result, List rowEntry, ListParams params) {
        if (params.hasColKeys()) {
            Map<String,Object> rowMap = params.createMapFromRow(rowEntry);
            if (params.filter(new EoRoot(configsCache, rowMap))) {
                result.add(rowMap);
            }
            else {
                System.out.println("Skipped " + rowMap.get(NATURAL_ID));
            }
        }
        else {
            if (params.filter(new EoRoot(configsCache, rowEntry))) {
                result.add(rowEntry);
            }
        }
    }
}
