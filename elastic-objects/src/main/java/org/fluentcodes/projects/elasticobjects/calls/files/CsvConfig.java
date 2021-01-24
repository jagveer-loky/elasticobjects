package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.lists.ListProperties;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;

import java.util.Map;

/**
 * Created by Werner on 09.10.2016.
 */
public class CsvConfig extends FileConfig implements CsvConfigInterface, ListProperties {
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
