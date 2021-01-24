package org.fluentcodes.projects.elasticobjects.calls.csv;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.calls.files.CsvConfig;
import org.junit.Test;

/**
 * Created by Werner on 08.10.2016.
 */
public class CsvWriteCallTest {

    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(CsvWriteCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(CsvWriteCall.class);
    }

    @Test
    public void resolveModel()  {
        ModelConfigChecks.resolve(CsvConfig.class);
    }

}
