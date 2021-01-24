package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigMap;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Map;

/**
 * Created by Werner on 22.1.2021.
 */
public class FileConfigMap extends ConfigConfigMap {
    public FileConfigMap(Scope scope)  {
        super(scope, FileConfig.class);
    }

    @Override
    protected Map<String, ConfigConfigInterface> initMap() {
        return new FileBeanMap(getScope()).createConfigMap();
    }
}
