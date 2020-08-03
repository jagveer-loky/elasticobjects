package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;

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
public class CallExecutorResource extends CallExecutorImpl {
    private static final Logger LOG = LogManager.getLogger(CallExecutorResource.class);
    public CallExecutorResource() {
        super();
    }

    /**
     * Creates an actions depending on the values add.
     *
     * @ on Exception
     */
    public Object execute(EO eo, Call call) {
        /*getCall.resolve(eo.getConfigsCache());
        if (!getCall().hasPermissions(eo.getRoles())) {
            eo.warn("No permission execute " + getCall().getClass().getSimpleName() + " " + getCall().getConfigKey());
            return null;
        }*/
        return super.execute(eo, call);
    }

}
