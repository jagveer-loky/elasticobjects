package org.fluentcodes.projects.elasticobjects.calls.xlsx;


import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListReadCall;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;

import java.util.List;
import java.util.Map;

/**
 * Reads and writes Excelsheets.
 * Created by werner.diwischek on 18.12.17.
 */
public class XlsxReadCall extends ListReadCall {

    public XlsxReadCall()  {
        super();
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }

    public Object execute(EO eo) {
        resolve(eo.getConfigsCache());
        List rawInput = ((XlsxConfig)getConfig()).read();
        if (rawInput == null|| rawInput.isEmpty()) {
            return rawInput;
        }
        return transform(rawInput);
    }
}
