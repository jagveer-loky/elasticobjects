package org.fluentcodes.projects.elasticobjects.calls;

import java.util.Map;

/**
 * Created by Werner on 11.12.2020.
 */
public class HostBean extends PermissionBean implements Host {
    public HostBean() {
        super();
        defaultConfigModelKey();
    }

    public HostBean(final Map<String, Object> map) {
        super();
        defaultConfigModelKey();
    }

    protected void merge(final Map values) {
        super.merge(values);
    }

    @Override
    public void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(HostConfig.class.getSimpleName());
    }

    @Override
    public String toString() {
        return getNaturalId() + " -> " + getUrl();
    }
}
