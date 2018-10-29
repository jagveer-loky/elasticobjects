package org.fluentcodes.projects.elasticobjects.calls;


import org.fluentcodes.projects.elasticobjects.condition.And;
import org.fluentcodes.projects.elasticobjects.condition.Eq;
import org.fluentcodes.projects.elasticobjects.condition.Or;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.utils.ReplaceUtil;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

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
    private Integer rowStart;
    private Integer rowEnd;
    private Integer length;
    private Integer rowHead;
    private Or filter;
    private String filterRaw;

    public ListParams() {

    }

    public ListParams(Map attributes) {
        mapAttributes(attributes);
    }

    public void mapAttributes(Map attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return;
        }
        setRowHead(attributes.get(F_ROW_HEAD));
        setRowStart(attributes.get(F_ROW_START));
        setLength(attributes.get(F_LENGTH));
        setRowEnd(attributes.get(F_ROW_END));
        this.filterRaw = ScalarConverter.toString(attributes.get(F_FILTER));
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

    public void prepare(EO adapter, Map attributes) {
        if (hasFilterRaw()) {
            setFilter(ReplaceUtil.replace(filterRaw, adapter, attributes));
        }
    }

    public boolean isEmpty() {
        return rowEnd == null && length == null && (filter == null || filter.isEmpty());
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

    public ListParams addAnd(EO adapter) throws Exception {
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

    protected ListParams setRowHead(Object rowHead) {
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

    public void prepareStartEnd(int rowMax) throws Exception {
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
}
