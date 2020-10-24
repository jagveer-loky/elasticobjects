package org.fluentcodes.projects.elasticobjects.calls.lists;


import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.ResourceCall;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;

/**
 * Base for list type calls.
 * @author Werner Diwischek
 * @since 18.12.17.
 */

public class ListReadCall extends ResourceCall implements ListInterface {
    private ListParams listParams;
    public ListReadCall() {
        super(PermissionType.READ);
        this.listParams = new ListParams();
    }

    public ListReadCall(final String configKey) {
        super(PermissionType.READ, configKey);
        this.listParams = new ListParams();
    }

    @Override
    public ListParams getListParams() {
        return listParams;
    }

    public void setListParams(ListParams listParams) {
        this.listParams = listParams;
    }
    public boolean hasListParams() {
        return listParams!=null;
    }

    @Override
    public ListReadCall resolve(EO cache) {
        super.resolve(cache);
        if (listParams==null) {
            listParams = new ListParams();
        }
        if (getTargetPath() == null) {
            setTargetPath("(List)" + PathElement.SAME);
        }
        return this;
    }

    @Override
    public Object execute(EO eo) {
        return null;
    }
}
