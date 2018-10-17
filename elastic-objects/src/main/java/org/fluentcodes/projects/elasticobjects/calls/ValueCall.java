package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.ValueConfig;
import org.fluentcodes.projects.elasticobjects.eo.EO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by werner.diwischek on 06.02.18.
 */
public class ValueCall extends Call {
    private Object value;

    public ValueCall(EOConfigsCache provider, String cacheKey) throws Exception {
        super(provider, cacheKey);
        this.value = getValueConfig().getValue();
    }

    public void mapAttributes(Map attributes) {
        super.mapAttributes(attributes);
        setValue(attributes.get(EO_STATIC.F_VALUE));
    }

    public void mergeConfig() {
        setValue(getValueConfig().getValue());
        super.mergeConfig();
    }


    private ValueConfig getValueConfig() {
        return (ValueConfig) getConfig();
    }

    public EO set(final EO adapter) throws Exception {
        return set(adapter, new HashMap());
    }

    public EO set(final EO adapter, Map attributes) throws Exception {
        if (adapter == null) {
            throw new Exception("Actions don't create new Adapters");
        }
        mapAttributes(attributes);
        mergeConfig();
        if (value == null) {
            adapter.error("Could not resolve valueString ");
            return adapter;
        }
        final String path = getMergePath();
        adapter.add(path).set(value);
        return adapter;
    }

    public EO map(final EO adapter) throws Exception {
        return map(adapter, new HashMap());
    }

    public EO map(final EO adapter, Map attributes) throws Exception {
        mergeConfig();
        if (value == null) {
            adapter.error("Could not resolve valueString ");
            return adapter;
        }
        final String path = getMergePath();
        adapter.add(path).map(value);

        return adapter;
    }


    public Object getValue() {
        return value;
    }

    public ValueCall setValue(Object entry) {
        if (entry == null) {
            return this;
        }
        if (this.value != null) {
            return this;
        }
        this.value = entry;
        return this;
    }
}
