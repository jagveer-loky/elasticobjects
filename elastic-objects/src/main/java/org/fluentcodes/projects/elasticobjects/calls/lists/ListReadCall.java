package org.fluentcodes.projects.elasticobjects.calls.lists;


import org.fluentcodes.projects.elasticobjects.calls.PermissionType;

import java.util.*;

/**
 * Base for list type calls.
 * @author Werner Diwischek
 * @since 18.12.17.
 */

public class ListReadCall extends ListCall {
    public ListReadCall() {
        super(PermissionType.READ);
    }

    public Object transform(List toFilter) {
        List filteredList = new ArrayList();
        if (hasRowStart() || hasRowEnd()) {
            Integer start = getRowStart();
            Integer end = getRowEnd();

            if (end == null || end > toFilter.size()) {
                end = toFilter.size();
            }
            if (start == null) {
                start = 0;
            }

            filteredList.addAll(toFilter
                    .subList(start, end));
        }
        else {
            filteredList.addAll(toFilter);
        }

        if (!hasRowHead()) {
            return filteredList;
        }

        if (!hasColKeys()) {
            setColKeys((List)toFilter.get(getRowHead()));
        }

        if (getMapKey() == null) {
            List<Map<String,Object>> result = new ArrayList<>();
            for (Object row : filteredList) {
                result.add(createMapFromRow((List)row));
            }
            return result;
        }
        else {
            Map<String, Map<String,Object>> result = new HashMap<>();
            int counter = 0;
            for (Object row : filteredList) {
                Map<String, Object> rowMap = createMapFromRow((List)result);
                Object mapKey = rowMap.get(getMapKey());
                if (mapKey == null) {
                    mapKey = Integer.valueOf(counter);
                }
                result.put(mapKey.toString(), rowMap);
            }
            return result;
        }
    }

    private Map<String, Object> createMapFromRow(List row) {
        Map<String, Object> rowMap = new LinkedHashMap<>();
        for (int i = 0; i<row.size(); i++) {
            if (getColKeys().size()<i) {
                continue;
            }
            if (row.get(i) == null) {
                continue;
            }
            if (getColKeys().get(i) == null) {
                continue;
            }
            String key = getColKeys().get(i);
            rowMap.put(key, row.get(i));
        }
        return rowMap;
    }
}
