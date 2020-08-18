package org.fluentcodes.projects.elasticobjects.calls.csv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListWriteCall;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class CsvWriteCall extends ListWriteCall {
    private static final Logger LOG = LogManager.getLogger(CsvWriteCall.class);

    public CsvWriteCall()  {
        super();
    }

    public CsvConfig getCsvConfig() {
        return ((CsvConfig) getConfig());
    }
}
