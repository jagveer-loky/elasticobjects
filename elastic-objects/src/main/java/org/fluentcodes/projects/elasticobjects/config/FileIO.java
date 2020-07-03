package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.EoException;
import org.fluentcodes.projects.elasticobjects.utils.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;

/**
 * Created by Werner on 09.10.2016.
 */
public class FileIO implements IOInterface {

    private FileConfig fileConfig;

    public FileIO(final FileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }

    public String read()  {
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
     * @
     */
    public void write(String content)  {
        if (fileConfig.isCached()) {
            throw new EoException("A fileCached file could not persisted!");
        }
        URL url = fileConfig.getUrl();
        File filePath = new File(url.getPath());
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }
        write(url, content);
    }

    public void write(Object content)  {
        if (fileConfig.isCached()) {
            throw new EoException("A fileCached file could not persisted!");
        }
        if (content instanceof String) {
            write((String) content);
        }
        //
    }

    private void write(URL url, String content)  {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(url.getFile()));
            out.write(content);
        }
        catch (Exception e) {
            throw new EoException(e);
        }
        finally {
            if (out != null) {
                try {
                out.close();
                }
                catch (Exception e) {
                    throw new EoException(e);
                }
            }
        }
    }
}
