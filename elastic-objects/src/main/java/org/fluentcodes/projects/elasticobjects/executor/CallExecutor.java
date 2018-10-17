package org.fluentcodes.projects.elasticobjects.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.JSONSerializationType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
public class CallExecutor extends ExecutorImpl implements Executor {
    public static final String ACTION = "call";
    private static final Set<String> ignoreSerializeKeys = createIgnoreSerializeKeys();
    private static final Logger LOG = LogManager.getLogger(CallExecutor.class);

    public CallExecutor(Map attributes) throws Exception {
        super(attributes, ExecutorItem.TYPES.call);
    }

    private final static Set<String> createIgnoreSerializeKeys() {
        Set<String> controlConstants = new HashSet<>();
        //controlConstants.add(A_EXECUTE);
        //controlConstants.add(F_TEMPLATE_KEY);
        //controlConstants.add(F_INSERT);
        return controlConstants;
    }

    protected static final boolean hasAction(final Map attributes) {
        return attributes.get(ACTION) != null || attributes.get(ACTION) != null;
    }

    @Override
    public void mapAttributes(final Map attributes) throws Exception {
        super.mapAttributes(attributes);
    }


    public final String getAction() {
        return getExecutorItem().getClassName() + "." + getExecutorItem().getMethodName() + "(" + getCacheKey() + ")";
    }

    public String getCacheKey() {
        return getExecutorItem().getArgs(0);
    }

    /**
     * Creates an actions depending on the values add.
     *
     * @throws Exception on Exception
     */
    public Object execute(EO adapter) {
        return execute(adapter, new HashMap<>());
    }

    public Object execute(EO eo, Map<String, String> externalAttributes) {
        if (eo == null) {
            String error = "adapter is null!";
            return error;
        }
        EOConfigsCache configsCache = eo.getConfigsCache();
        StringBuilder result = new StringBuilder();

        // call instanciation will be prepared.
        Call call = null;
        try {
            call = (Call) getExecutorItem()
                    .getExecutorClass()
                    .getConstructor(EOConfigsCache.class, String.class)
                    .newInstance(configsCache, getCacheKey());
            call.mapAttributes(getAttributes());
        } catch (Exception e) {
            e.printStackTrace();
            eo.error(e.getMessage());
            return "";
        }
        Long startTime = System.currentTimeMillis();
        try {
            Object resultObject = getExecutorItem().getMethod().invoke(call, eo, externalAttributes);
            if (resultObject instanceof String) {
                result.append(resultObject);
            }
            Long duration = System.currentTimeMillis() - startTime;
            eo.info("Successfully executed within " + duration.toString() + " ms.");
        } catch (Exception e) {
            String error = "Problem executing for '" + this.getAction() + "':" + e.getMessage();
            e.printStackTrace();
            eo.error(error);
            return error;
        }
        return result.toString();
    }


    /**
     * Serialize to JSON.
     * Sets the intent to 0 (without indent and new lines) and the serialiationType to {@link JSONSerializationType#EO}.
     *
     * @return the object as a JSON String
     */

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Object key : getAttributes().keySet()) {
            if (ignoreSerializeKeys.contains(key)) {
                continue;
            }
            builder.append(" ");
            builder.append(key);
            builder.append("=\"");
            builder.append(getAttribute((String) key));
            builder.append("\"");
        }
        builder.append("}");
        return builder.toString();
    }
}
