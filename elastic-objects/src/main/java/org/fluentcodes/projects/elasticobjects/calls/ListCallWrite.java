package org.fluentcodes.projects.elasticobjects.calls;


import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.condition.Or;
import org.fluentcodes.projects.elasticobjects.config.ListConfig;
import org.fluentcodes.projects.elasticobjects.config.ListIOInterface;
import org.fluentcodes.projects.elasticobjects.config.Permissions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.paths.PathPattern;

import java.util.*;

/**
 * Write call for list type calls.
 * @author Werner Diwischek
 * @since 10.7.2020.
 */

public class ListCallWrite extends ResourceCall<Boolean> {
    private ListParams listParams;
    private ListMapper listMapper;

    public ListCallWrite() {
        super(Permissions.WRITE);
    }

    protected ListConfig getListConfig() {
        return (ListConfig) getConfig();
    }

    public void mapAttributes(final Map attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return;
        }
        try {
            super.mapAttributes(attributes);
            listParams.mapAttributes(attributes);
            listMapper.mapAttributes(attributes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ListMapper getListMapper() {
        return listMapper;
    }

    public ListParams getListParams() {
        return listParams;
    }

    public String getModelKeys() {
        return listMapper.getModelKeys();
    }

    public void setModelKeys(Object modelKeys) {
        listMapper.setModelKeys(modelKeys);
    }

    public boolean isDoMap() {
        return listMapper.isDoMap();
    }

    public void setDoMap(Boolean doMap) {
        listMapper.setDoMap(doMap);
    }

    public String getMapPath() {
        return listMapper.getMapPath();
    }

    public void setMapPath(String mapPath) {
        listMapper.setMapPath(mapPath);
    }

    protected boolean hasMapPath() {
        return !(getMapPath() == null) && !getMapPath().isEmpty();
    }

    //<call keep="JAVA" templateKey="ActionListSetter.tpl">
    public boolean isIgnoreHeader() {
        return listMapper.isIgnoreHeader();
    }

    public void setIgnoreHeader(boolean ignoreHeader) {
        listMapper.setIgnoreHeader(ignoreHeader);
    }

    public Integer getRowStart() {
        return listParams.getRowStart();
    }

    public void setRowStart(Integer rowStart) {
        listParams.setRowStart(rowStart);
    }

    public Integer getRowEnd() {
        return listParams.getRowEnd();
    }

    public void setRowEnd(Integer rowEnd) {
        listParams.setRowEnd(rowEnd);
    }

    public Integer getLength() {
        return listParams.getLength();
    }

    public void setLength(Integer rowEnd) {
        listParams.setRowStart(rowEnd);
    }

    public void addAnd(String key, Object value) {
        if (getFilter() == null) {
            setFilter(key + " eq " + value.toString());
        }
        //TODO if exists
    }

    public Or getFilter() {
        return listParams.getFilter();
    }

    public void setFilter(Or or) {
        this.listParams.setFilter(or);
    }

    public void setFilter(String orAsString) {
        this.listParams.setFilter(orAsString);
    }

    public PathPattern getPathPattern() {
        return listMapper.getPathPattern();
    }

    public String getPathPatternAsString() {
        return listMapper.getPathPattern().getSerialized();
    }

    public void setPathPatternAsString(String pathPattern) {
        listMapper.setPathPattern(new PathPattern(pathPattern));
    }

    public String getColKeysAsString() {
        return String.join(",", listMapper.getColKeys());
    }

    public void setColKeysAsString(String colKeys) {
        listMapper.setColKeys(Arrays.asList(colKeys.split(",")));
    }

    public Boolean execute(final EO eo){
        if (eo.isEmpty()) {
            throw new EoException("Empty adapter -- nothing to write for " + eo.getPath());
        }
        List toWrite = toList(eo, null);
        try {
            ((ListIOInterface) getListConfig().createIO()).write(toWrite);
        } catch (Exception e) {
            throw new EoException(e);
        }
        return true;
    }

    public List toList(EO adapter, Map externalAttributes) {
        List toWrite = new ArrayList();
        if (adapter.isEmpty()) {
            adapter.warn("Empty adapter -- nothing to write for " + adapter.getPath());
            return toWrite;
        }
        List<String> keys = null;
        try {
            keys = new ArrayList<>(adapter.keys());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (keys == null || keys.isEmpty()) {
            return toWrite;
        }
        EO firstChild = null;
        try {
            firstChild = adapter.getEo(keys.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            return toWrite;
        }
        if (firstChild == null || firstChild.isEmpty()) {
            return toWrite;
        }

        List<String> colKeys = null;
        try {
            colKeys = new ArrayList<>(firstChild.keys());
        } catch (Exception e) {
            e.printStackTrace();
            return toWrite;
        }
        listParams.prepare();
        int rowHead = getListParams().getRowHead();
        if (rowHead > -1) {
            for (int i = 0; i < rowHead; i++) {
                toWrite.add(new ArrayList<>());
            }
            toWrite.add(colKeys);
        }
        for (String key : keys) {
            EO child = null;
            try {
                child = adapter.getEo(key);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            if (child == null) {
                continue;
            }
            List row = new ArrayList();
            if (child.isEmpty()) {
                toWrite.add(row);
                continue;
            }
            for (String colKey : colKeys) {
                try {
                    row.add(child.get(colKey));
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
            toWrite.add(row);
        }
        return toWrite;
    }

}
