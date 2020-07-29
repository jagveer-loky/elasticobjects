package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;

import org.fluentcodes.projects.elasticobjects.calls.json.JsonCallRead;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created 12.6.2018
 */
public class ResourcesJsonCallReadTest {
    @Test
    public void readFileSourceJson()  {
        final EO eoEmpty = TestProviderRootTest.createEo();
        final JsonCallRead call = new JsonCallRead(FILE_SOURCE_JSON);
        call.execute(eoEmpty);
        Assert.assertEquals(S_VALUE11, eoEmpty.getEo(S0).get(S_KEY1));
        //AssertEO.compare(eoEmpty);
    }
}
