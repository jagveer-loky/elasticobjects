package org.fluentcodes.projects.elasticobjects.calls.lists;


import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_NATURAL_ID;

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
    public static final String FILTER_RAW = "filterRaw";
    public static final String COL_KEYS = "colKeys";

    private Integer rowStart;
    private Integer rowEnd;
    private Integer length;
    private Integer rowHead;
    private Or or;
    private String filter;
    private List<String> colKeys;
    private Map<String, Integer> colKeysMap;

    public ListParams() {
    }
    public ListParams(boolean dbFlag) {
        if (dbFlag) {
            rowHead = 0;
            rowStart = 0;
            rowEnd = 200;
        }
    }

    public void merge(Map<String, Object> properties) {
        if (properties == null) {
            prepare();
            return;
        }
        this.mergeRowHead(properties.get(ROW_HEAD));
        this.mergeRowStart(properties.get(ROW_START));
        this.mergeLength(properties.get(LENGTH));
        this.mergeRowEnd(properties.get(ROW_END));
        this.mergeFilter(properties.get(FILTER_RAW));
        prepare();
    }

    public void initDb() {
        if (!hasRowHead()) {
            setRowHead(0);
        }
        if (!hasRowStart()) {
            setRowStart(0);
        }
        if (!hasRowEnd()) {
            setRowEnd(100);
        }
    }

    public boolean filter(EO toFilter) {
        if (!hasFilter()) {
            return true;
        }
        resolve();
        return or.filter(toFilter);
    }

    public Map<String, Object> createMapFromRow(List row) {
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

    public void resolve() {
        if (or != null) {
            return;
        }
        if (hasFilter()) {
            this.or = new Or(filter);
        }
    }

    public boolean isEmpty() {
        return rowEnd == null && length == null && (or == null || or.isEmpty());
    }

    public boolean hasFilter() {
        return getFilter()!=null && ! getFilter().isEmpty();
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
    public String getFilter() {
        return filter;
    }

    private ListParams mergeFilter(Object filter) {
        if (filter == null) {
            return this;
        }
        if (hasFilter()) {
            return this;
        }
        try {
            this.filter = ScalarConverter.toString(filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
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

    public ListParams setRowHead(Integer rowHead) {
        this.rowHead = rowHead;
        return this;
    }

    private ListParams mergeRowHead(Object rowHead) {
        if (rowHead == null) {
            return this;
        }
        if (this.rowHead != null) {
            return this;
        }
        try {
            this.rowHead = ScalarConverter.toInt(rowHead);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    public boolean hasRowHead() {
        return rowHead != null && rowHead > -1;
    }

    public boolean hasRowStart() {
        return rowStart != null && rowStart > -1;
    }

    public boolean hasRowHead(Integer rowCounter) {
        if (!hasRowHead()) {
            return false;
        }
        if (getRowHead()<0) {
            return false;
        }
        return getRowHead() < rowCounter;
    }

    public boolean isRowHead(Integer rowCounter) {
        if (!hasRowHead()) {
            return false;
        }
        if (getRowHead()<0) {
            return false;
        }
        return getRowHead() == rowCounter;
    }

    public Integer getRowStart() {
        return rowStart;
    }

    public ListParams setRowStart(Integer entry) {
        this.rowStart = entry;
        return this;
    }

    private ListParams mergeRowStart(Object entry) {
        if (entry == null) {
            return this;
        }
        if (hasRowStart()) {
            return this;
        }
        try {
            this.rowStart = ScalarConverter.toInt(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public boolean isRowStart(Integer rowCounter) {
        if (!hasRowStart()) {
            return true;
        }
        return getRowStart() <= rowCounter;
    }

    public boolean hasRowEnd() {
        return rowEnd!=null && rowEnd>-1;
    }

    public Integer getRowEnd() {
        return rowEnd;
    }
    public ListParams setRowEnd(Integer entry) {
        this.rowEnd = entry;
        return this;
    }

    private ListParams mergeRowEnd(Object entry) {
        if (entry == null) {
            return this;
        }
        if (this.rowEnd != null && this.rowEnd > -1) {
            return this;
        }
        try {
            this.rowEnd = ScalarConverter.toInt(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public boolean isRowEnd(Integer rowCounter) {
        if (!hasRowEnd()) {
            return true;
        }
        return rowCounter<getRowEnd();
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

    public ListParams setLength(Integer entry) {
        this.length = entry;
        return this;
    }

    private ListParams mergeLength(Object entry) {
        if (entry == null) {
            return this;
        }
        if (hasLength()) {
            return this;
        }
        try {
            this.length = ScalarConverter.toInt(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public boolean hasLength() {
        return this.length != null && this.length > 0;
    }

    public void prepare() {
        if (rowHead == null) {
            return;
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

    public ListParams setColKeys(List<String> colKeys) {
        this.colKeys = colKeys;
        return this;
    }
    public ListParams setColKeys(ArrayList<String> colKeys) {
        this.colKeys = colKeys;
        return this;
    }

    private ListParams mergeColKeys(Object colKeys) {
        if (colKeys == null) {
            return this;
        }
        if (colKeys instanceof String) {
            setColKeys(Arrays.asList(((String)colKeys).split(",")));
            return this;
        }
        throw new EoException("Could not map colkeys");
    }

    public boolean hasColKeys() {
        return colKeys!=null && !colKeys.isEmpty();
    }

    public void addRowEntry(ConfigMaps configsCache, List result, List rowEntry) {
        if (hasColKeys()) {
            Map<String,Object> rowMap = createMapFromRow(rowEntry);
            if (filter(EoRoot.ofValue(configsCache, rowMap))) {
                result.add(rowMap);
            }
            else {
                System.out.println("Skipped " + rowMap.get(F_NATURAL_ID));
            }
        }
        else {
            if (filter(EoRoot.ofValue(configsCache, rowEntry))) {
                result.add(rowEntry);
            }
        }
    }

}
