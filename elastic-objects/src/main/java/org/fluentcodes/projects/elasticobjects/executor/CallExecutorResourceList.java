package org.fluentcodes.projects.elasticobjects.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.ResourceCall;
import org.fluentcodes.projects.elasticobjects.calls.ListMapper;

/**
 * @author Werner Diwischek
 * @version 0.01
 *
 */
public class CallExecutorResourceList extends CallExecutorResource {
    private static final Logger LOG = LogManager.getLogger(CallExecutorResourceList.class);
    private ListMapper mapper;
    public CallExecutorResourceList() {
        super();
    }

    public CallExecutorResourceList(ResourceCall call) {
        super();
        setCall(call);
    }

    @Override
    public Object transform(Object object) {
        if (mapper == null) {
            return object;
        }
        return object;
    }

}
