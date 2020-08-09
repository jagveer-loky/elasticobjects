package org.fluentcodes.projects.elasticobjects.calls;


import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.config.XlsxConfig;

import java.util.Map;

/**
 * Reads and writes Excelsheets.
 * Created by werner.diwischek on 18.12.17.
 */
public class XlsxCall extends ListCall {

    public XlsxCall(EOConfigsCache provider, String key)  {
        super(provider, key);
    }

    public XlsxConfig getXlsxConfig() {
        return ((XlsxConfig) getConfig());
    }

    public FileConfig getFileCache()  {
        return getXlsxConfig().getFileConfig();
    }

    public HostConfig getHostCache()  {
        return getXlsxConfig().getFileConfig().getHostConfig();
    }


    public void mapAttributes(final Map attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return;
        }
        super.mapAttributes(attributes);
    }
}
