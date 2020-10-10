package org.fluentcodes.projects.elasticobjects.calls.xlsx;


import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListReadCall;
import org.fluentcodes.projects.elasticobjects.models.Config;

/**
 * Reads and writes Excelsheets.
 * Created by werner.diwischek on 18.12.17.
 */
public class XlsxReadCall extends ListReadCall {

    public XlsxReadCall()  {
        super();
    }

    public XlsxReadCall(final String configKey)  {
        super(configKey);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }

    public Object execute(EO eo) {
        init(eo);
        return ((XlsxConfig)getConfig()).read(eo, this);
    }
}
