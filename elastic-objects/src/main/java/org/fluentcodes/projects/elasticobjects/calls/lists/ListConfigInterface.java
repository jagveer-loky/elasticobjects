package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserEoReplace;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.util.List;
import java.util.Map;

public interface ListConfigInterface extends Config {
    ListParams getListParams();

    List readRaw(ListParams params);

    default String read(EO eo, ListReadCall readCall) {
        ListParams params = readCall.getListParams();
        params.merge(getListParams());
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
                String target = new ParserEoReplace(targetPath).parse(new EoRoot(getConfigsCache(), row));
                eo.set(row, target);
            }
            else {
                eo.set(row, targetPath, Integer.valueOf(i).toString());
            }
        }
        if (targetPath.equals("_template")) {
            return "TODO _template";
        }
        return "";
    }

    default void addRowEntry(List result, List rowEntry, ListParams params) {
        if (params.hasColKeys()) {
            Map<String,Object> rowMap = params.createMapFromRow(rowEntry);
            if (!params.hasFilter() || params.getFilter().filter(new EoRoot(getConfigsCache(), rowMap))) {
                result.add(rowMap);
            }
        }
        else {
            if (!params.hasFilter() || params.getFilter().filter(new EoRoot(getConfigsCache(), rowEntry))) {
                result.add(rowEntry);
            }
        }
    }
}
