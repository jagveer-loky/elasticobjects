package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.utils.FileUtil;
import org.fluentcodes.projects.elasticobjects.utils.ReplaceUtil;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Werner on 09.10.2016.
 */
public class FileIO implements IOInterface {

    private FileConfig fileConfig;
    public FileIO(final FileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }

    public String read() throws Exception {
        if (fileConfig.hasCachedContent()) {
            return fileConfig.getCachedContent();
        }
        URL url = fileConfig.getUrl();
        String content = FileUtil.readFile(url);
        if (fileConfig.isCached()) {
            fileConfig.setCachedContent(content);
        }
        return content;
    }
    /**
     * Write the file direct without the usage of
     *
     * @param content
     * @throws Exception
     */
    public void write(String content) throws Exception {
        if (fileConfig.isCached()) {
            throw new Exception("A fileCached file could not persisted!");
        }
        URL url = fileConfig.getUrl();
        File filePath = new File(url.getPath());
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }
        write(url, content);
    }

    public void write(Object content) throws Exception {
        if (fileConfig.isCached()) {
            throw new Exception("A fileCached file could not persisted!");
        }
        if (content instanceof String) {
            write((String) content);
        }
        //
    }

    private void write(URL url, String content) throws Exception {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(url.getFile()));
            out.write(content);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
