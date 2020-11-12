package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.Map;

/**
 * Created by Werner on 09.10.2016.
 */
public class CsvConfig extends FileConfig implements CsvProperties, ListProperties {
    public CsvConfig(final EOConfigsCache configsCache, Map map)  {
        super(configsCache, map);
    }
}
