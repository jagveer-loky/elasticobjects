package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TestXlsxProvider;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_PATH;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_PATH0_KEY1;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_VALUE11;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC_TEST.X_SOURCE_XLSX_TEST;

/**
 * Created by Werner on 08.10.2016.
 */
public class XlsxCallReadTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(XlsxCallReadTest.class);

    @Test
    public void serialize()  {
        TestHelper.printStartMethod();
        Map attributes = new HashMap();
        attributes.put(F_PATH, F_PATH);
        TestXlsxProvider.executeXlsxActionRead(attributes, X_SOURCE_XLSX_TEST);
    }

    @Test
    public void withNoPath()  {
        EO adapter = TestXlsxProvider.executeXlsxActionRead(new HashMap(), X_SOURCE_XLSX_TEST);
        Assert.assertEquals(S_VALUE11, adapter.get(S_PATH0_KEY1));
    }


}
