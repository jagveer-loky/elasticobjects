package org.fluentcodes.tools.xpect;

import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.Collection;
import java.util.Map;

public class IOEoProvider<T> extends IOProvider<T> {
    public IOEoProvider(EOConfigsCache cache, Collection<String> fileNameList) {
        super(fileNameList);
        Map<String, String> fileNames = super.getFileNames();
        for (String alias: fileNames.keySet()) {
            super.put(alias, new IOJsonEo<T>(cache).setFileName(fileNames.get(alias)));
        }
    }
}
