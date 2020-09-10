package org.fluentcodes.projects.elasticobjects.calls.lists;


import org.fluentcodes.projects.elasticobjects.calls.condition.And;
import org.fluentcodes.projects.elasticobjects.calls.condition.Eq;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserTemplate;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;

/**
 * A bean class for containing the following values:
 * <ul>
 * <li>rowStart: .</li>
 * <li>rowEnd: .</li>
 * <li>length: ??</li>
 * </ul>
 * Created by werner.diwischek on 03.12.16.
 */
public class ListParams {
    public static final String ROW_HEAD = "rowHead";
    public static final String ROW_START = "rowStart";
    public static final String LENGTH = "length";
    public static final String ROW_END = "rowEnd";
    public static final String FILTER = "filter";
    private Integer rowStart;
    private Integer rowEnd;
    private Integer length;
    private Integer rowHead;
    private Or filter;
    private String filterRaw;
    private String mapKey;
    private List<String> colKeys;
    private Map<String, Integer> colKeysMap;

    public ListParams() {

    }

    public ListParams(Map attributes) {

        mapAttributes(attributes);
    }

    public void mapAttributes(Map attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return;
        }
        setRowHead(attributes.get(ROW_HEAD));
        setRowStart(attributes.get(ROW_START));
        setLength(attributes.get(LENGTH));
        setRowEnd(attributes.get(ROW_END));
        this.filterRaw = ScalarConverter.toString(attributes.get(FILTER));
    }

    /**
     * Sets the values for the parameter with local values if not set.
     *
     * @param listParamsDefault
     */
    public void merge(ListParams listParamsDefault) {
        this.setRowHead(listParamsDefault.getRowHead());
        this.setRowStart(listParamsDefault.getRowStart());
        this.setLength(listParamsDefault.getLength());
        this.setRowEnd(listParamsDefault.getRowEnd());
        listParamsDefault.prepare();
    }

    public Object filter(List toFilter) {
        Integer start = getRowStart();
        Integer end = getRowEnd();
        if (!hasRowStart() && !hasRowEnd()) {
            return toFilter;
        }
        if (end == null || end>toFilter.size()) {
            end = toFilter.size();
        }
        if (start == null) {
            start = 0;
        }
        List filteredList = new ArrayList();
        filteredList.addAll(toFilter
                .subList(start, end));
        if (!hasRowHead()) {
            return filteredList;
        }

        if (colKeys == null|| colKeys.isEmpty()) {
            colKeys = (List) toFilter.get(getRowHead());
        }
        this.colKeysMap = new HashMap<>();
        for (int i = 0; i < this.colKeys.size(); i++) {
            this.colKeysMap.put(this.colKeys.get(i), i);
        }
        if (mapKey == null) {
            List<Map<String,Object>> result = new ArrayList<>();
            for (Object row : filteredList) {
                result.add(createMapFromRow((List)result));
            }
            return result;
        }
        else {
            Map<String, Map<String,Object>> result = new HashMap<>();
            int counter = 0;
            for (Object row : filteredList) {
                Map<String, Object> rowMap = createMapFromRow((List)result);
                Object mapKey = rowMap.get(this.mapKey);
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
            if (colKeys.size()<i) {
                continue;
            }
            if (row.get(i) == null) {
                continue;
            }
            if (colKeys.get(i) == null) {
                continue;
            }
            String key = colKeys.get(i);
            rowMap.put(colKeys.get(i), row.get(i));
        }
        return rowMap;
    }

    public void prepare(EO adapter, Map attributes) {
        if (hasFilterRaw()) {
            setFilter(new ParserTemplate(filterRaw).parse(adapter));
        }
    }

    public boolean isEmpty() {
        return rowEnd == null && length == null && (filter == null || filter.isEmpty());
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public Or getFilter() {
        return filter;
    }

    public void setFilter(Object filter) {
        if (filter == null) {
            return;
        }
        if (filter instanceof Or) {
            this.filter = (Or) filter;
            return;
        }
        if (!(filter instanceof String)) {
            return;
        }
        this.filter = new Or((String) filter);
    }

    public boolean filterRow(List rowList) {
        if (filter == null) {
            return true;
        }
        if (filter.isEmpty()) {
            return true;
        }
        return filter.filter(rowList);
    }

    public ListParams addAnd(EO adapter)  {
        if (adapter == null) {
            return this;
        }
        if (this.filter == null) {
            this.filter = new Or();
        }
        And and = new And(adapter);
        this.filter.addAnd(and);
        return this;
    }

    public ListParams addAnd(And value) {
        if (value == null) {
            return this;
        }
        if (filter == null) {
            this.filter = new Or();
        }
        this.filter.addAnd(value);
        return this;
    }

    public ListParams addAnd(String key, Object value) {
        if (key == null) {
            return this;
        }
        if (value == null) {
            return this;
        }
        if (filter == null) {
            this.filter = new Or();
        }
        And and = new And();
        and.addCondition(new Eq(key, value));
        this.filter.addAnd(and);
        return this;
    }

    private boolean hasFilterRaw() {
        return filterRaw != null && !filterRaw.isEmpty();
    }

    public void setFilterRaw(Object filter) {
        if (filter == null) {
            return;
        }
        if (!(filter instanceof String)) {
            return;
        }
        this.filterRaw = (String) filter;
    }

    protected ListParams checkRowStart() {
        if (rowStart == null) {
            rowStart = rowHead + 1;
            return this;
        }
        if (rowStart <= rowHead) {
            rowStart = rowHead + 1;
            return this;
        }
        return this;
    }

    public Integer getRowHead() {
        return rowHead;
    }

    public ListParams setRowHead(Object rowHead) {
        if (rowHead == null) {
            rowHead = -1;
        }
        if (this.rowHead != null && this.rowHead > -1) {
            return this;
        }
        Integer value = null;
        try {
            value = ScalarConverter.toInt(rowHead);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (value == null) {
            return this;
        }
        this.rowHead = value;
        return this;
    }

    public boolean hasRowHead() {
        return rowHead != null && rowHead > -1;
    }

    public boolean hasRowStart() {
        return rowStart != null && rowStart > -1;
    }

    public Integer getRowStart() {
        return rowStart;
    }

    public ListParams setRowStart(Object entry) {
        if (entry == null) {
            return this;
        }
        if (hasRowStart()) {
            return this;
        }
        Integer value = null;
        try {
            value = ScalarConverter.toInt(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (value == null) {
            return this;
        }
        this.rowStart = value;
        return this;
    }
    public boolean hasRowEnd() {
        return rowEnd!=null && rowEnd>-1;
    }

    public Integer getRowEnd() {
        return rowEnd;
    }

    public ListParams setRowEnd(Object entry) {
        if (entry == null) {
            return this;
        }
        if (this.rowEnd != null && this.rowEnd > -1) {
            return this;
        }
        Integer value = null;
        try {
            value = ScalarConverter.toInt(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (value == null) {
            return this;
        }
        this.rowEnd = value;
        return this;
    }

    public void prepareStartEnd(int rowMax)  {
        prepare();
        if (rowMax < rowEnd || rowEnd.equals(-1)) {
            rowEnd = rowMax;
            length = rowEnd - rowStart;
            return;
        }
    }

    public Integer getLength() {
        return length;
    }

    public ListParams setLength(Object entry) {
        if (entry == null) {
            return this;
        }
        if (hasLength()) {
            return this;
        }
        Integer value = null;
        try {
            value = ScalarConverter.toInt(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (value == null) {
            return this;
        }
        this.length = value;
        return this;
    }

    public boolean hasLength() {
        return this.length != null && this.length > 0;
    }

    public void prepare() {
        if (rowHead == null) {
            rowHead = -1;
        }
        if (rowStart == null || rowStart <= rowHead) {
            rowStart = rowHead + 1;
        }
        if (length == null) {
            length = -1;
        }
        if (length > 0) { // length superficial to rowEnd
            rowEnd = rowStart + length;
        }
        if (rowEnd == null) {
            rowEnd = -1;
        }
    }
    public List <String> getColKeys() {
        return colKeys;
    }

    public ListParams setColKeys(List <String> colKeys) {
        this.colKeys = colKeys;
        return this;
    }

}
