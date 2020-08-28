package org.fluentcodes.projects.elasticobjects.external;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.calls.xlsx.XlsxReadCall;
import org.junit.Test;


public class XlsxTest {
    @Test
    public void create() {
        XlsxReadCall xlsxReadCall = new XlsxReadCall ();
        Assertions.assertThat(xlsxReadCall).isNotNull();
    }
    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(XlsxReadCall.class);
    }
}
