package org.fluentcodes.projects.elasticobjects.calls.files;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by werner.diwischek on 2.10.2020.
 */
public class DirectoryListReadCall extends FileReadCall{
    public DirectoryListReadCall() {
        super();
    }
    public DirectoryListReadCall(final String configKey) {
        super(configKey);
    }

    @Override
    public Object execute(final EO eo)  {
        init(eo);
        List<String> result = listFiles();
        return createReturnType(eo, result);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }


    public List<String> listFiles()  {
        if (!hasConfig()) {
            throw new EoException("No config defined for configKey " + getConfigKey());
        }
        if (!getFileConfig().hasFilePath()) {
            throw new EoException("No filePath in config defined for configKey " + getConfigKey());
        }
        if (!getFileConfig().hasFileName()) {
            throw new EoException("No fileName in config defined for configKey " + getConfigKey());
        }
        return listFiles(getFileConfig().getFilePath(), getFileConfig().getFileName());
    }

    // https://stackabuse.com/java-list-files-in-a-directory/
    public List<String> listFiles(final String filePath, final String filter)  {
        if (filePath==null || filePath.isEmpty()) {
            throw new EoException("No filePath defined");
        }
        File directory = new File(filePath);
        if (!directory.exists()) {
            List<URL> urlList = getUrlListFromClassPath(filePath);
            if (urlList.isEmpty()) {
                throw new EoException("Directory '" + filePath + "' does not exist also in class path.");
            }
            if (urlList.size()>1) {
                throw new EoException("Several directories for '" + filePath + "' found in class path: " + urlList.toString());
            }
            directory = new File(urlList.get(0).getFile());
        }

        final String[] fileNames = directory.list();
        List<String> fileNameList = new ArrayList<>();
        for(final String fileName:fileNames) {
            if (fileName.matches(filter)) {
                fileNameList.add(directory.getAbsolutePath() + Path.DELIMITER + fileName);
            }
        }
        if (fileNames.length>0 && fileNameList.isEmpty()) {
            throw new EoException("Empty result for filter '" + filter + "' and filePath '" + filePath + "' but size of files in directory is " + fileNames.length);
        }
        return fileNameList;
    }

    public static final List<URL> getUrlListFromClassPath(final String fileName) {
        List<URL> urlList = new ArrayList<>();
        try {
            Enumeration<URL> urlsFromClasspath =  Thread.currentThread().getContextClassLoader().getResources(fileName);
            while (urlsFromClasspath.hasMoreElements()) {
                urlList.add(urlsFromClasspath.nextElement());
            }
        }
        catch (Exception e) {
            throw new EoException("Null matching file in classpath for '" + fileName + "'.");
        }
        return urlList;
    }
}
