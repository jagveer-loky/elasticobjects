package org.fluentcodes.projects.elasticobjects.calls.files;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by werner.diwischek on 10.10.2020.
 */
public class DirectoryMapReadCall extends DirectoryListReadCall{
    public final static String FILE_ENDING = "fileEnding";
    public final static String DEFAULT_TO_REPLACE = "_";
    public final static String DEFAULT_REPLACE_BY = " ";
    public final static String DEFAULT_FILE_ENDING = ".html";
    private String toReplace;
    private String replaceBy;
    private String fileEnding;

    public DirectoryMapReadCall() {
        super();
    }
    public DirectoryMapReadCall(final String configKey) {
        super(configKey);
    }

    @Override
    public Object execute(final EO eo)  {
        Map<String, String> result = mapDirectory(eo);
        return createReturnType(eo, result);
    }

    public Map<String, String> mapDirectory(final EO eo)  {
        final String usedToReplace = hasToReplace()? this.toReplace: DEFAULT_TO_REPLACE;
        final String usedReplaceBy = hasReplaceBy()? this.replaceBy: DEFAULT_REPLACE_BY;
        final String usedFileEnding = hasFileEnding()? this.fileEnding: DEFAULT_FILE_ENDING;
        Map<String, String> result= new LinkedHashMap<>();
        DirectoryListReadCall call = new DirectoryListReadCall(getConfigKey());
        List<String> directoryContent = (List<String>)call.execute(eo);
        for (String key: directoryContent) {
            String value = key
                    .replaceAll(".*/", "")
                    .replaceAll(usedToReplace, usedReplaceBy)
                    .replaceAll(usedFileEnding, "");
            result.put(value, key);
        }
        return result;
    }

    public boolean hasToReplace() {
        return toReplace!=null && !toReplace.isEmpty();
    }
    public boolean hasReplaceBy() {
        return replaceBy!=null && !replaceBy.isEmpty();
    }
    public boolean hasFileEnding() {
        return fileEnding!=null && !fileEnding.isEmpty();
    }
    public String getToReplace() {
        return toReplace;
    }

    public void setToReplace(String toReplace) {
        this.toReplace = toReplace;
    }

    public String getReplaceBy() {
        return replaceBy;
    }

    public void setReplaceBy(String replaceBy) {
        this.replaceBy = replaceBy;
    }

    public String getFileEnding() {
        return fileEnding;
    }

    public void setFileEnding(String fileEnding) {
        this.fileEnding = fileEnding;
    }
}
