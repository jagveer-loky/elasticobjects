package org.fluentcodes.projects.elasticobjects.calls.csv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvConfig;
import org.junit.Test;

/**
 * Created by Werner on 08.10.2016.
 */
public class CsvWriteCallTest {

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(CsvWriteCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(CsvWriteCall.class);
    }

    @Test
    public void resolveModel()  {
        ConfigModelChecks.resolve(CsvConfig.class);
    }

}
