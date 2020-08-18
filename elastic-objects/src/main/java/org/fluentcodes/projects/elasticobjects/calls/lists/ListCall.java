package org.fluentcodes.projects.elasticobjects.calls.lists;


import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base for list type calls.
 * @author Werner Diwischek
 * @since 18.12.17.
 */

public class ListCall extends CallResource {
    private Integer rowStart;
    private Integer rowEnd;
    private Integer length;
    private Integer rowHead;
    private Or filter;
    private String filterRaw;
    private String mapKey;
    private List<String> colKeys;
    private Map<String, Integer> colKeysMap;

    public ListCall(PermissionType permissionType) {
        super(permissionType);
    }

    public ListCall(final PermissionType permissionType, final String configKey) {
        super(permissionType, configKey);
    }

    public boolean hasRowStart() {
        return rowStart != null && rowStart > -1;
    }

    public Integer getRowStart() {
        return rowStart;
    }

    public ListCall setRowStart(Integer rowStart) {
        this.rowStart = rowStart;
        return this;
    }

    public boolean hasRowEnd() {
        return rowEnd != null && rowEnd > -1;
    }

    public Integer getRowEnd() {
        return rowEnd;
    }

    public ListCall setRowEnd(Integer rowEnd) {
        this.rowEnd = rowEnd;
        return this;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public boolean hasRowHead() {
        return rowHead!=null && rowHead>-1;
    }

    public Integer getRowHead() {
        return rowHead;
    }

    public ListCall setRowHead(Integer rowHead) {
        this.rowHead = rowHead;
        return this;
    }

    public Or getFilter() {
        return filter;
    }

    public void setFilter(Or filter) {
        this.filter = filter;
    }

    public String getFilterRaw() {
        return filterRaw;
    }

    public void setFilterRaw(String filterRaw) {
        this.filterRaw = filterRaw;
    }

    public boolean hasMapKey() {
        return mapKey !=null && !mapKey.isEmpty();
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public boolean hasColKeys() {
        return colKeys != null && !colKeys.isEmpty();
    }

    public List<String> getColKeys() {
        return colKeys;
    }

    public void setColKeys(List<String> colKeys) {
        this.colKeys = colKeys;
        if (hasColKeys()) {
            this.colKeysMap = new HashMap<>();
            for (int i = 0; i < getColKeys().size(); i++) {
                this.colKeysMap.put(getColKeys().get(i), i);
            }
        }
    }

    public Object execute(EO eo) {
        resolve(eo.getConfigsCache());
        hasPermissions(eo.getRoles());
        return null;
    }

    @Override
    public CallResource resolve(EOConfigsCache cache) {
        super.resolve(cache);
        ListConfig config = (ListConfig)getConfig();
        if (config.hasRowHead() && rowHead == null) {
            rowHead = config.getRowHead();
        }
        if (config.hasRowStart() && rowStart == null) {
            rowStart = config.getRowStart();
        }
        if (config.hasRowEnd() && rowEnd == null) {
            rowEnd = config.getRowEnd();
        }
        if (config.hasColKeys() && colKeys == null) {
            setColKeys(config.getColKeys());
        }
        if (hasRowHead() && !hasRowStart()) {
            rowStart = rowHead + 1;
        }
        return this;
    }
}
