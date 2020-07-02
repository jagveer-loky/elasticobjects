package org.fluentcodes.projects.elasticobjects.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates an actions and calls a method on the actions with adapter as argument.
 * <ul>
 * <li>Creates the actions instance var from actionClass and the cacheKey from the config.</li>
 * <li>set the  actions method defined in the actionMethod.</li>
 * <li>Sets the attribute values on asset.</li>
 * </ul>
 * <p>.</p>
 *
 * @author Werner Diwischek
 * @version 0.01
 */
public class ExecutorValues extends ExecutorImpl implements Executor {
    private static final Logger LOG = LogManager.getLogger(ExecutorValues.class);

    /**
     * Creates an actions depending on the values add.
     * https://stackoverflow.com/questions/2467544/invoking-a-static-method-using-reflection
     *
     * @throws Exception on Exception
     */
    private List values;
    private String mapPath;

    public ExecutorValues(Map attributes) throws Exception {
        super(attributes, ExecutorItem.TYPES.value);
    }

    public ExecutorValues(final Object... values) throws Exception {
        super(EO_STATIC.toMap(values), ExecutorItem.TYPES.value);
    }

    public ExecutorValues(final Class valueClass, final String method, final String... args) throws Exception {
        super(valueClass, method, args);
        values = new ArrayList();
        for (int i=0; i<args.length;i++) {
            values.add(null);
        }
    }

    public void mapAttributes(Map attributes) throws Exception {
        super.mapAttributes(attributes);
        setMapPath(attributes.get(EO_STATIC.F_MAP_PATH));
        this.initValues(attributes);
    }

    private void initValues(Map attributes) {
        this.values = new ArrayList();
        for (int i = 0; i < getExecutorItem().getArgsLength(); i++) {
            values.add(attributes.get(getExecutorItem().getArgs(i)));
        }
    }

    protected boolean hasMapPath() {
        return mapPath != null && !mapPath.isEmpty();
    }

    public String getMapPath() {
        return mapPath;
    }

    public void setMapPath(final Object entry) {
        if (hasMapPath()) {
            return;
        }
        if (entry == null || ((String) entry).isEmpty()) {
            return;
        }
        this.mapPath = ScalarConverter.toString(entry);
    }

    public String execute(final EO adapter) {
        return execute(adapter, new HashMap<>());
    }

    public String execute(final EO eo, Map<String, String> attributes) {
        EO adapter = eo;
        final String focusPath = (String) getAttribute(EO_STATIC.F_PATH);
        if (focusPath != null) { // change focus on eo.
            try {
                adapter = eo.getChild(focusPath);
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
            if (adapter == null) {
                final String message = "No eo entry for " + EO_STATIC.F_PATH + " = '" + focusPath + "'.";
                eo.warn(message);
                return message;
            }
        }
        List<String> pathList = getPathList(adapter);
        StringBuilder builder = new StringBuilder();
        //if (hasPrefix()) {
        //    builder.append(getPrefix());
        //>
        for (String path : pathList) {

            EO nextAdapter = null;
            try {
                nextAdapter = adapter.getChild(path);
            } catch (Exception e) {
                adapter.error(e.getMessage());
                e.printStackTrace();
                continue;
            }
            List loopValues = new ArrayList();
            try {
                loopValues.addAll(values);
                for (int i = 0; i < getExecutorItem().getArgsLength(); i++) {
                    String arg = getExecutorItem().getArgs(i);
                    if (arg.equals("adapter")||arg.equals("eo")) {
                        loopValues.set(i, nextAdapter);
                    } else if (loopValues.get(i) != null) {  // from attributes
                        continue;
                    } else if (arg.startsWith("'")) {  // a string value
                        String value = arg.replaceAll("^'", "");
                        loopValues.set(i, value.replaceAll("'$", ""));
                    } else if (nextAdapter.get(arg) != null) {  // find from adapter
                        loopValues.set(i, nextAdapter.get(arg));
                    }
                }
            } catch (Exception e) {
                adapter.error(e.getMessage());
                continue;
            }
            try {
                Object[] myValues = loopValues.toArray();
                //https://yourmitra.wordpress.com/2008/09/26/using-java-reflection-to-invoke-a-method-with-array-parameters/
                Object result = getExecutorItem().invoke(myValues);
                if (!hasMapPath() || mapPath.equals("/")) {
                    builder.append(result);
                } else {
                    nextAdapter.add(mapPath).set(result);
                }
            } catch (Exception e) {
                StringBuilder error = new StringBuilder();
                error.append(getExecutorItem().getMethodName());
                error.append(": Exception with values");
                for (Object entry : loopValues) {
                    error.append(entry);
                    error.append(", ");
                }
                LOG.error(error);
                adapter.error(error.toString());
                builder.append(error);
                continue;
            }

        }
        return builder.toString();
    }
}
