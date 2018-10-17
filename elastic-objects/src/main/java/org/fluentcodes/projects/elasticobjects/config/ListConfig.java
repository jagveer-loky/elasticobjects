package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.calls.ListMapper;
import org.fluentcodes.projects.elasticobjects.calls.ListParams;
import org.fluentcodes.projects.elasticobjects.condition.Or;

import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 30.10.2016.
 */
public abstract class ListConfig extends ConfigIO implements ListConfigInterface {
    private final ListParams listParams;
    private final ListMapper listMapper;
    private boolean resolved = false;

    public ListConfig(final EOConfigsCache configsCache, final Builder builder) {
        super(configsCache, builder);
        this.listParams = builder.listParams;
        this.listMapper = builder.listMapper;
    }

    public ListParams getListParams() {
        return listParams;
    }

    public ListMapper getListMapper() {
        return listMapper;
    }

    public Integer getRowHead() {
        return listParams.getRowHead();
    }

    public boolean hasRowHead() {
        return listParams.hasRowHead();
    }

    public Integer getRowStart() {
        return listParams.getRowStart();
    }

    public Integer getRowEnd() {
        return listParams.getRowEnd();
    }

    public Integer getLength() {
        return listParams.getLength();
    }

    public Or getOr() {
        return listParams.getFilter();
    }

    public boolean hasColKeys() {
        return this.listMapper.hasColKeys();
    }

    public List<String> getColKeys() {
        return this.listMapper.getColKeys();
    }

    public void setColKeys(Object object) {
        this.listMapper.setColKeys(object);
    }

    public void resolve() throws Exception {
        if (isResolved()) {
            return;
        }
        super.resolve();
        if (hasColKeys()) {
            return;
        }
        List<String> header = ((ListIOInterface)createIO()).readHead(listParams.getRowHead());
        setColKeys(header);
    }

    public static class Builder extends ConfigIO.Builder {
        private ListParams listParams;
        private ListMapper listMapper;

        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values) throws Exception {
            super.prepare(configsCache, values);
            this.listParams = new ListParams((Map) values.get("listParams"));
            this.listMapper = new ListMapper((Map) values.get("listMapper"));
        }
    }
}
