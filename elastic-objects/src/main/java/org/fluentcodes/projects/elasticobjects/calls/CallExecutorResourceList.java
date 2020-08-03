package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListCallRead;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListMapper;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;

/**
 * @author Werner Diwischek
 * @version 0.01
 *
 */
public class CallExecutorResourceList extends CallExecutorResource {
    private static final Logger LOG = LogManager.getLogger(CallExecutorResourceList.class);
    private final ListCallRead callEo;
    public CallExecutorResourceList(final EO callEo) {
        super();
        this.callEo = (ListCallRead)callEo;
    }

    @Override
    public Object transform(Object object) {
        if (object == null ) {
            return object;
        }
        if (!(object instanceof List)) {
            throw new EoException("Result is not instance of List");
        }
        return object;
    }
}
