package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.tools.xpect.IOString;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 2.10.2020.
 */
public class DirectoryConfig extends FileConfig {
    private Map<String, String> cachedContent;

    public DirectoryConfig(final EOConfigsCache provider, Map map) {
        super(provider, map);
    }

    @Override
    protected boolean hasCachedContent() {
        return cachedContent!=null && cachedContent.isEmpty();
    }

    private boolean hasCachedContent(final String fileName) {
        return hasCachedContent() && cachedContent.containsKey(fileName);
    }

    public Object read(final String fileName)  {
        if (fileName==null || fileName.isEmpty()) {
            throw new EoException("No fileName provided for DirectoryConfig read.");
        }
        if (hasCachedContent(fileName)) {
            return cachedContent.get(fileName);
        }
        if (!fileName.matches(getFileName())) {
            throw new EoException("fileName in call for read '"+ fileName + "' does not match fileName in  DirectoryConfig '" + getFileName() + "'.");
        }
        String content = FileReadCall.read(getFilePath(), fileName);
        if (isCached()) {
            if (cachedContent == null) {
                cachedContent = new HashMap<>();
            }
            cachedContent.put(fileName, content);
        }
        return content;
    }



    /**
     * Write the file direct without the usage of
     *
     * @param content
     * @
     */
    public void write(Object content)  {
        if (isCached()) {
            throw new EoException("A fileCached file could not persisted!");
        }
        URL url = createUrl();
        new IOString().setFileName(url.getFile()).write((String)content);
    }
}
