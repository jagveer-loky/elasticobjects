package org.fluentcodes.projects.elasticobjects.executor.statics;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.calls.ValueCall;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorValues;
import org.fluentcodes.projects.elasticobjects.executor.ValueParamsHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_MAP_PATH;

public class ValuesEO extends ValueParamsHelper {
    public static final String CLASS_NAME = ValuesEO.class.getSimpleName();

    public static final String M_GET_CONFIGURATION_KEYS = "getConfigurationKeys";
    public static final String M_GET_CONFIGURATION = "getConfiguration";
    public static final String M_GET_CONFIGURATION_LIST = "getConfigurationList";

    public static final String GET_CONFIGURATION_KEYS = CLASS_NAME  + "." + M_GET_CONFIGURATION_KEYS;
    public static final String GET_CONFIGURATION = CLASS_NAME  + "." + M_GET_CONFIGURATION;
    public static final String GET_CONFIGURATION_LIST = CLASS_NAME  + "." + M_GET_CONFIGURATION_LIST;

    public static final String CONFIG_TYPE = "configType";
    public static final String CONFIG_KEY = "configKey";
    public static final String CONFIG_FILTER = "configFilter";

    public static final String EXECUTE_GET_CONFIGURATION_KEYS = GET_CONFIGURATION_KEYS + "(eo," + CONFIG_TYPE + ")";
    public static final String EXECUTE_GET_CONFIGURATION = GET_CONFIGURATION + "(eo," + CONFIG_TYPE + "," + CONFIG_KEY + ")";
    public static final String EXECUTE_GET_CONFIGURATION_LIST = GET_CONFIGURATION + "(eo," + CONFIG_TYPE + "," + CONFIG_FILTER + ")";

    public static final String EXECUTE_VALUE_CALL_GET_CONFIGURATION_LIST = ValueCall.SET(GET_CONFIGURATION_KEYS);
    public static final String EXECUTE_VALUE_CALL_GET_CONFIGURATION = ValueCall.SET( GET_CONFIGURATION );

    public static final String CONFIG_TYPE_TEMPLATE(final String type) {
        return " configType=\"" + type + "\" ";
    }

    public static final String SELECT_CONFIG_TEMPLATE(final String configKey) {
        return " " + CONFIG_TYPE + "=\"" + configKey + "\" ";
    }


    public static List<String> getConfigurationKeys(Object[] values) throws Exception {
        EO eo = getEO(0, values);
        String configName = null;
        try {
            configName = getString(1, values);
        }
        catch (Exception e) {
            return eo.getConfigsCache().getConfigClassesAsStringList();
        }
        List<String> configKeys = new ArrayList<>(eo.getConfigsCache().getConfigNames(configName));
        try {
            final String configFilter = getString(2, values);
            return configKeys
                    .stream()
                    .filter(x->x.matches(configFilter))
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            return configKeys;
        }

    }

    public static Object getConfiguration(Object[] values) throws Exception {
        EO eo = getEO(0, values);
        if (eo == null) {
            throw new Exception("No EO object defined");
        }
        if (values.length < 3) {
            throw new Exception("Parameters smaller than 3 " + values.length);
        }
        String configType = getString(1, values);
        if (configType == null) {
            throw new Exception("No config type defined");
        }
        String configKey = getString(2, values);
        if (configType == null) {
            throw new Exception("No config key defined");
        }
        return eo.getConfigsCache().getConfigMap(configType).get(configKey);
    }

    public static List getConfigurationList(Object[] values) throws Exception {
        EO eo = getEO(0, values);
        if (eo == null) {
            throw new Exception("No EO object defined");
        }
        if (values.length < 3) {
            throw new Exception("Parameters smaller than 3 " + values.length);
        }
        String configType = getString(1, values);
        if (configType == null) {
            throw new Exception("No config type defined");
        }
        final String configFilter = getString(2, values);
        Map map = eo.getConfigsCache().getConfigMap(configType);
        return (List) map
                .keySet()
                .stream()
                .filter(x->((String)x).matches(configFilter))
                .map(x->map.get(x))
                .collect(Collectors.toList());
    }

    static final CallExecutor createGetConfigurationList(String... values) throws Exception {
        switch(values.length) {
            case(0):
                return ValueCall.createSetExecutor(GET_CONFIGURATION_LIST, values);
            case(1):
                return ValueCall.createSetExecutor(GET_CONFIGURATION_LIST, CONFIG_TYPE,values[0]);
            case(2):
                return ValueCall.createSetExecutor(GET_CONFIGURATION_LIST, CONFIG_TYPE,values[0], CONFIG_FILTER, values[1]);
            default:
                throw new Exception("Length is '" + values.length + "' and longer than 2.");
        }
    }

    static final CallExecutor createGetConfigurationKeys(String... values) throws Exception {
        switch(values.length) {
            case(0):
                return ValueCall.createSetExecutor(GET_CONFIGURATION_KEYS, values);
            case(1):
                return ValueCall.createSetExecutor(GET_CONFIGURATION_KEYS, CONFIG_TYPE,values[0]);
            case(2):
                return ValueCall.createSetExecutor(GET_CONFIGURATION_KEYS, CONFIG_TYPE,values[0], CONFIG_FILTER, values[1]);
            default:
                throw new Exception("Length is '" + values.length + "' and longer than 2.");
        }
    }

    public static final CallExecutor createCallGetConfiguration(final String configType, final String configKey) throws Exception {
        return ValueCall.createSetExecutor(GET_CONFIGURATION, CONFIG_TYPE, configType, CONFIG_KEY, configKey);
    }

    public static final ExecutorValues createsExecutorGetConfigurationList(final Object... values) throws Exception {
        ExecutorValues executorValues = new ExecutorValues(ValuesEO.class, M_GET_CONFIGURATION_LIST, new String[]{"eo", CONFIG_TYPE, CONFIG_FILTER});
        executorValues.mapAttributes(EO_STATIC.toMap(values));
        return executorValues;
    }

    public static final ExecutorValues createsExecutorGetConfigurationKeys(final Object... values) throws Exception {
        ExecutorValues executorValues = new ExecutorValues(ValuesEO.class, M_GET_CONFIGURATION_KEYS, new String[]{"eo", CONFIG_TYPE});
        executorValues.mapAttributes(EO_STATIC.toMap(values));
        return executorValues;
    }

    public static final ExecutorValues createsExecutorGetConfiguration(final Object... values) throws Exception {
        ExecutorValues executorValues = new ExecutorValues(ValuesEO.class, M_GET_CONFIGURATION, new String[]{"eo", CONFIG_TYPE, CONFIG_KEY});
        Map attributes = EO_STATIC.toMap(values);
        attributes.put(F_MAP_PATH, ".");
        executorValues.mapAttributes(attributes);
        return executorValues;}
}
