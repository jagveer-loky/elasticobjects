package org.fluentcodes.projects.elasticobjects.calls.lists;


import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.ResourceCall;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

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

    @Override
    public void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>4) {
            throw new EoException("Short form should have form '<configKey>[,<targetPath>][,<condition>][,<keepCall>]' with max length 3 but has size " + array.length + ": '" + values + "'." );
        }
        if (array.length>0) {
            setConfigKey(array[0]);
        }
        if (array.length>1) {
            setTargetPath( array[1]);
        }
        if (array.length>2) {
            setCondition( array[2]);
        }
        if (array.length>3) {
            setKeepCall(KeepCalls.valueOf(array[3]));
        }
    }
}
