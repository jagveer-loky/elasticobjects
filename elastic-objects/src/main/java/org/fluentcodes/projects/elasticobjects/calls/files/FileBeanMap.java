package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.models.ConfigBeanMap;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Map;

/**
 * Created by Werner on 21.1.2021.
 */

public class FileBeanMap extends ConfigBeanMap<FileBean> {
    public FileBeanMap(final Scope scope)  {
        super(scope, FileConfig.class);
    }

    @Override
    protected FileBean createBean(final String naturalId, final Map valueMap) {
        return new FileBean(naturalId, valueMap);
    }
}
