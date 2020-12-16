package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.calls.files.FileBean;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.Map;

/**
 * Created by Werner on 09.10.2016.
 */
public class CsvConfig extends FileConfig implements CsvProperties, ListProperties {
    public CsvConfig(Map map) {
        this(new FileBean(map));
    }
    public CsvConfig(ConfigBean configBean) {
        this((FileBean)configBean);
    }
    public CsvConfig(FileBean bean) {
        super(bean);
    }
}
