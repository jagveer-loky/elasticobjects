package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.lists.ListProperties;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

import java.util.Map;

/**
 * Created by Werner on 09.10.2016.
 */
public class CsvConfig extends FileConfig implements CsvConfigInterface, ListProperties {

    public CsvConfig(ConfigBean configBean, final ConfigMaps configMaps) {
        this((FileBean)configBean, configMaps);
    }
    public CsvConfig(FileBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
    }
}
