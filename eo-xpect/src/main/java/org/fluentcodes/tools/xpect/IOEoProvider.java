package org.fluentcodes.tools.xpect;

import java.util.Collection;
import java.util.Map;

public class IOEoProvider<T> extends IOProvider<T> {
    public IOEoProvider(Collection<String> fileNameList) {
        super(fileNameList);
        Map<String, String> fileNames = super.getFileNames();
        for (String alias: fileNames.keySet()) {
            super.put(alias, new IOJsonEo<T>().setFileName(fileNames.get(alias)));
        }
    }
}
