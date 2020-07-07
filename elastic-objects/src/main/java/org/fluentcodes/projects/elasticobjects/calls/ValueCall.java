package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.EoException;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.ValueConfig;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.Executor;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by werner.diwischek on 06.02.18.
 */
public class ValueCall extends Call {

    public static final String VALUE_SET = "ValueCall.set";
    public static final String VALUE_MAP = "ValueCall.map";
    public static final String EMPTY = "empty";
    public static final String SET(final String... parameters) {
        StringBuilder builder = new StringBuilder(VALUE_SET);
        builder.append("(");
        for (String parameter:parameters) {
            builder.append(parameter);
            builder.append(",");
        }
        return builder.toString().replaceAll(",$",")");
    }
    public static final String MAP(final String... parameters) {
        StringBuilder builder = new StringBuilder(VALUE_MAP);
        builder.append("(");
        for (String parameter:parameters) {
            builder.append(parameter);
            builder.append(",");
        }
        return builder.toString().replaceAll(",$",")");
    }

    private Object value;
    private ExecutorItem execute;

    public ValueCall(EOConfigsCache provider, String configKey)  {
        super(provider, configKey);
        this.value = getValueConfig().getValue();
        this.execute = getValueConfig().getExecute();
    }

    public static CallExecutor createSetExecutor(final String key, final Map attributes) {
        attributes.put(Executor.EXECUTE, SET(key));
        return new CallExecutor(attributes);
    }

    public static CallExecutor createSetExecutor(final String callKey, final Object... values)  {
        return createSetExecutor(callKey, EO_STATIC.toMap(values));
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

    private static String createSet(final String key) {
        return ValueCall.class.getSimpleName() + ".set(" + key + ")";
    }



    public EO set(final EO adapter)  {
        return set(adapter, new HashMap());
    }

    public EO set(final EO eo, Map attributes)  {
        if (eo == null) {
            throw new EoException("Actions don't create new Adapters");
        }
        mapAttributes(attributes);
        mergeConfig();
        if (execute != null) {
            this.value = execute.invoke(eo, attributes);
        }
        final String path = getMergePath();
        eo.setPathValue(path, value);
        return eo;
    }

    public EO map(final EO adapter)  {
        return map(adapter, new HashMap());
    }

    public EO map(final EO adapter, Map attributes)  {
        mergeConfig();
        if (value == null) {
            adapter.error("Could not resolve valueString ");
            return adapter;
        }
        final String path = getMergePath();
        adapter.setPathValue(path, value);

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
