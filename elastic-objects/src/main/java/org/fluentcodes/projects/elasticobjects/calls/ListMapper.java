package org.fluentcodes.projects.elasticobjects.calls;


import org.fluentcodes.projects.elasticobjects.EoException;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.ModelConfig;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.eo.Models;
import org.fluentcodes.projects.elasticobjects.paths.PathPattern;
import org.fluentcodes.projects.elasticobjects.utils.ReplaceUtil;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * A bean class for containing the following values:
 * <ul>
 * <li>modelKey: The object where values are mapped to via ModelInterface object.</li>
 * <li>colKeys:</li>
 * <li>colKeysMap:</li>
 * </ul>
 * Created by werner.diwischek on 03.12.16.
 */
public class ListMapper {
    EOConfigsCache provider;
    private List<String> colKeys;
    private Map<String, Integer> colKeysMap;
    private Models models;
    private String modelKeys;
    private Boolean ignoreHeader;
    private String mapPath;
    private PathPattern pathPattern;
    private Boolean doMap = true;

    public ListMapper() {

    }

    public ListMapper(Map attributes)  {
        mapAttributes(attributes);
    }

    public void mapAttributes(Map attributes)  {
        if (attributes == null) {
            return;
        }
        setColKeys(attributes.get(F_COL_KEYS));
        setMapPath(attributes.get(F_MAP_PATH));
        setDoMap(attributes.get(F_DO_MAP));
        setIgnoreHeader(attributes.get(F_IGNORE_HEADER));
        setPathPattern(attributes.get(F_PATH_PATTERN));
        setModelKeys(attributes.get(F_MODEL_KEYS));
    }

    public boolean hasPathPattern() {
        return pathPattern != null && !pathPattern.isEmpty();
    }

    public PathPattern addPathPattern(String path) {
        if (this.pathPattern == null) {
            this.pathPattern = new PathPattern(path);
        } else {
            this.pathPattern.addPath(path);
        }
        return pathPattern;
    }

    public PathPattern getPathPattern() {
        return pathPattern;
    }

    public void setPathPattern(Object entry) {
        if (this.pathPattern != null) {
            return;
        }
        if (entry == null) {
            return;
        }
        if (entry instanceof PathPattern) {
            this.pathPattern = (PathPattern) entry;
        }
        if (entry instanceof String) {
            this.pathPattern = new PathPattern((String) entry);
        }

    }

    public boolean hasIgnoreHeader() {
        return ignoreHeader != null;
    }

    public boolean isIgnoreHeader() {
        return hasIgnoreHeader() && ignoreHeader;
    }

    public ListMapper setIgnoreHeader(Object entry) {
        if (hasIgnoreHeader()) {
            return this;
        }
        if (entry == null) {
            return this;
        }
        this.ignoreHeader = ScalarConverter.toBoolean(entry);
        return this;
    }

    public boolean hasMapPath() {
        return mapPath != null && !mapPath.isEmpty();
    }

    public String getMapPath() {
        return mapPath;
    }

    public ListMapper setMapPath(final Object entry) {
        if (mapPath != null && !mapPath.isEmpty()) {
            return this;
        }
        if (entry == null) {
            return this;
        }
        if (!(entry instanceof String)) {
            return this;
        }
        this.mapPath = (String) entry;
        return this;
    }

    public boolean hasDoMap() {
        return doMap != null;
    }

    public boolean isDoMap() {
        return hasDoMap() && doMap;
    }

    public ListMapper setDoMap(Object entry) {
        if (entry == null) {
            return this;
        }
        if (hasDoMap()) {
            return this;
        }
        this.doMap = ScalarConverter.toBoolean(entry);
        return this;

    }

    public boolean hasModelKeys() {
        return modelKeys != null && !modelKeys.isEmpty();
    }

    public String getModelKeys() {
        return modelKeys;
    }

    public ListMapper setModelKeys(Object modelKeys) {
        if (hasModelKeys()) {
            return this;
        }
        if (modelKeys == null) {
            return this;
        }
        if (modelKeys instanceof Models) {
            this.modelKeys = this.modelKeys.toString();
            return this;
        }
        this.modelKeys = ScalarConverter.toString(modelKeys);
        return this;

    }

    public EOConfigsCache getProvider() {
        return provider;
    }

    public void setProvider(EOConfigsCache provider) {
        this.provider = provider;
    }

    public boolean hasColKeys() {
        return colKeys != null && !colKeys.isEmpty();
    }

    public List<String> getColKeys() {
        return colKeys;
    }

    public ListMapper setColKeys(Object object) {
        if (hasColKeys()) {
            return this;
        }
        if (object == null) {
            return this;
        }
        if (object instanceof String) {
            if (((String) object).isEmpty()) {
                return this;
            }
            this.colKeys = Arrays.asList(((String) object).split(","));
        } else if (object instanceof List) {
            if (((List) object).isEmpty()) {
                return this;
            }
            this.colKeys = new ArrayList<>();
            for (int i = 0; i < ((List) object).size(); i++) {
                colKeys.add(ScalarConverter.toString(((List) object).get(i)));
            }
        } else if (object instanceof Object[]) {
            if (((Object[]) object).length == 0) {
                return this;
            }
            this.colKeys = new ArrayList<>();
            for (int i = 0; i < ((Object[]) object).length; i++) {
                colKeys.add(ScalarConverter.toString(((Object[]) object)[i]));
            }
        }
        if (!hasColKeys()) {
            return this;
        }

        this.colKeysMap = new HashMap<>();
        for (int i = 0; i < this.colKeys.size(); i++) {
            this.colKeysMap.put(this.colKeys.get(i), i);
        }
        return this;
    }

    protected Map<String, Integer> getColKeysMap() {
        return colKeysMap;
    }

    public boolean hasHeader() {
        return colKeys != null && !colKeys.isEmpty();
    }

    private void resolve(EO adapter, ListMapper configMapper)  {
        if (configMapper != null) {
            mergeConfigRowMapper(configMapper);
        }
        resolve(adapter.getConfigsCache());
        if (!hasMapPath()) {
            if (adapter.isList()) {
                return;
            }
            if (adapter.isEmpty()) {
                adapter.add()
                        .setModels(List.class)
                        .build();
            } else {
                if (adapter.isMap()) {
                    return;
                }
                throw new EoException("Not empty adapter and not list!");
            }
        } else {
            if (adapter.isMap() || adapter.isObject()) {
                return;
            }
            if (adapter.isEmpty()) {
                adapter.add()
                        .setModels(Map.class)
                        .build();
            } else {
                if (adapter.isList()) {
                    return;
                }
                throw new EoException("Not empty adapter and not object or map!");
            }
        }
    }

    private void mergeConfigRowMapper(ListMapper configMapper) {
        if (configMapper == null) {
            return;
        }
        setColKeys(configMapper.colKeys);
        setModelKeys(configMapper.getModelKeys());
        setPathPattern(configMapper.getPathPattern());
        setMapPath(configMapper.getMapPath());
    }

    private void resolve(EOConfigsCache cache) {
        if (models != null) {
            return;
        }
        if (modelKeys != null && !modelKeys.isEmpty()) {
            try {
                this.models = new Models(cache, modelKeys);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (hasMapPath()) {
            this.modelKeys = Map.class.getSimpleName() + ",";
        } else {
            this.modelKeys = List.class.getSimpleName() + ",";
        }
        if (!hasHeader() || isIgnoreHeader()) {
            this.modelKeys += List.class.getSimpleName();
        } else {
            this.modelKeys += Map.class.getSimpleName();
        }
        try {
            this.models = new Models(cache, modelKeys);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addList(EO adapter, List list)  {
        addList(adapter, list, null);
    }

    public void addList(EO adapter, List list, ListMapper configMapper)  {
        resolve(adapter, configMapper);
        int counter = -1;
        Map<String, String> attributes = new LinkedHashMap<>();
        for (Object row : list) {
            counter++;
            String position = new Integer(counter).toString();
            attributes.put("position", position);

            if (!hasMapPath()) {
                if (!(row instanceof List) || (!hasColKeys() || isIgnoreHeader()) && adapter.isEmpty()) {
                    try {
                        adapter.add(position).set(row);
                    } catch (Exception e) {
                        e.printStackTrace();
                        adapter.error(e.getMessage());
                    }
                    continue;
                }
                try {
                    addRow(adapter, position, (List) row);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            } else {
                EO rowAdapter = null;
                try {
                    rowAdapter = createRowAdapter(adapter.getConfigsCache());
                } catch (Exception e) {
                    e.printStackTrace();
                    adapter.error(e.getMessage());
                    continue;
                }
                try {
                    createRow(rowAdapter, (List) row);
                } catch (Exception e) {
                    e.printStackTrace();
                    adapter.error(e.getMessage());
                    continue;
                }
                String subPath = ReplaceUtil.replace(mapPath, rowAdapter, attributes);
                try {
                    adapter
                            .add(subPath)
                            .setPathPattern(pathPattern)
                            .set(rowAdapter.get());
                } catch (Exception e) {
                    e.printStackTrace();
                    adapter.error(e.getMessage());
                    continue;
                }
            }
        }
    }

    private EO createRowAdapter(EOConfigsCache cache)  {
        return new EOBuilder(cache)
                .setModels(models.getChildModel())
                .build();
    }

    private EO createRowAdapter(EO adapter, String position)  {
        adapter.add(position)
                .setModels(models.getChildModel())
                .build();
        return adapter.getChild(position);
    }

    public EO addRow(EO adapter, String position, List row)  {
        EO targetAdapter = createRowAdapter(adapter, position);
        return createRow(targetAdapter, row);
    }

    public EO createRow(EO adapter, List row)  {
        resolve(adapter.getConfigsCache());
        //String rowKey = new Integer(adapter.size()).getSerialized();
        ModelConfig model = (ModelConfig) models.getChildModel();
        for (int i = 0; i < row.size(); i++) {
            if (row.get(i) == null) {
                continue;
            }
            final Object value = row.get(i);
            String key = new Integer(i).toString();
            if (!isIgnoreHeader() && hasColKeys()) {
                if (i < colKeys.size()) {
                    key = colKeys.get(i);
                }
            }
            if (key.isEmpty()) {
                continue;
            }

            if (value instanceof String && "".equals(value)) {
                if (model.isObject() && model.getFieldClass(key) != String.class) {
                    continue;
                }
            }
            if (doMap) {
                adapter
                        .add(key)
                        .map(value);
            } else {
                adapter
                        .add(key)
                        .set(value);
            }
        }
        return adapter;
    }
}
