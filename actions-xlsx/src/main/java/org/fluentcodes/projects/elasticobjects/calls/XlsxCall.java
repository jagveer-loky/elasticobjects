package org.fluentcodes.projects.elasticobjects.calls;


import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.FileConfig;
import org.fluentcodes.projects.elasticobjects.config.HostConfig;
import org.fluentcodes.projects.elasticobjects.config.XlsxConfig;

import java.util.Map;

/**
 * Reads and writes Excelsheets.
 * Created by werner.diwischek on 18.12.17.
 */
public class XlsxCall extends ListCall {

    public XlsxCall(EOConfigsCache provider, String key) throws Exception {
        super(provider, key);
    }

    public XlsxConfig getXlsxConfig() {
        return ((XlsxConfig) getConfig());
    }

    public FileConfig getFileCache() throws Exception {
        return getXlsxConfig().getFileConfig();
    }

    public HostConfig getHostCache() throws Exception {
        return getXlsxConfig().getFileConfig().getHostConfig();
    }


    public void mapAttributes(final Map attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return;
        }
        super.mapAttributes(attributes);
    }
}
