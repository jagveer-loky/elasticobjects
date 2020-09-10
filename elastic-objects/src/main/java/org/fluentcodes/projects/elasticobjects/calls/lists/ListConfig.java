package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.calls.ConfigResourcesImpl;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 30.10.2016.
 */
public abstract class ListConfig implements ListConfigInterface{

    private final ListParams listParams;
    private final ListMapper listMapper;

    public ListConfig(final EOConfigsCache configsCache, final Builder builder) {
        this.listParams = builder.listParams;
        this.listMapper = builder.listMapper;
    }

    public ListConfig(Map map) {
        this.listParams = new ListParams((Map)map.get(LIST_PARAMS));
        this.listMapper = new ListMapper((Map)map.get(LIST_MAPPER));
    }

    public ListParams getListParams() {
        return listParams;
    }

    public ListMapper getListMapper() {
        return listMapper;
    }

    public void resolve()  {
        if (hasColKeys()) {
            return;
        }
    }

    public static class Builder extends ConfigResourcesImpl.Builder {
        private ListParams listParams;
        private ListMapper listMapper;

        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            super.prepare(configsCache, values);
            this.listParams = new ListParams((Map) values.get("listParams"));
            this.listMapper = new ListMapper((Map) values.get("listMapper"));
        }
    }
}
