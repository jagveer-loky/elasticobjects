package org.fluentcodes.projects.elasticobjects.calls.lists;


import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.calls.Permissions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.paths.PathPattern;

import java.util.*;

/**
 * Base for list type calls.
 * @author Werner Diwischek
 * @since 18.12.17.
 */

public class ListCallRead extends CallResource {
    private ListParams listParams;
    private ListMapper listMapper;

    public ListCallRead() {
        super(Permissions.READ);
        listParams = new ListParams();
        listMapper = new ListMapper();
    }

    protected ListConfig getListConfig() {
        return (ListConfig) getConfig();
    }

    public void mapAttributes(final Map attributes) {
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

    public boolean hasRowStart() {
        return listParams.hasRowStart();
    }
    public Integer getRowStart() {
        return listParams.getRowStart();
    }

    public ListCallRead setRowStart(Integer rowStart) {
        listParams.setRowStart(rowStart);
        return this;
    }
    public Integer getRowEnd() {
        return listParams.getRowStart();
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

    @Override
    public Object execute(final EO eo)  {
        ListIOInterface io = (ListIOInterface) getListConfig().createIO();
        getListConfig().resolve();
        List result = io.read(getListParams());
        return result;
    }
}
