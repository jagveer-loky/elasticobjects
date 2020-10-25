package org.fluentcodes.projects.elasticobjects.calls;


import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by werner.diwischek on 22.10.2020.
 */
public abstract class ResourceWriteCall extends ResourceCall {
    public ResourceWriteCall() {
        super(PermissionType.READ);
    }

    public ResourceWriteCall(final String configKey) {
        super(PermissionType.WRITE);
        setConfigKey(configKey);
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
            setSourcePath( array[1]);
        }
        if (array.length>2) {
            setCondition( array[2]);
        }
        if (array.length>3) {
            setKeepCall(KeepCalls.valueOf(array[3]));
        }
    }
}
