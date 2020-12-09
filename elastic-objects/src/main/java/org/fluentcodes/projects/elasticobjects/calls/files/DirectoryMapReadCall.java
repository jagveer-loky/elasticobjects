package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/

/**
 * Read a list from a directory. The file names will replace <i>toReplace (default "_")</i> by <i>replaceBy (default "")</i> and <i>fileEnding(default ".html")</i> will be removed. 
 * 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 07:08:15 CET 2020
 */
public class DirectoryMapReadCall extends DirectoryListReadCall  {
/*=>{}.*/
    public final static String DEFAULT_TO_REPLACE = "_";
    public final static String DEFAULT_REPLACE_BY = " ";
    public final static String DEFAULT_FILE_ENDING = ".html";

    /*==>{ALLStaticNames.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
   public static final String FILE_ENDING = "fileEnding";
   public static final String REPLACE_BY = "replaceBy";
   public static final String TO_REPLACE = "toReplace";
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
   private  String fileEnding;
   private  String replaceBy;
   private  String toReplace;
/*=>{}.*/

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
        List<String> directoryContent = super.listFiles(eo);
        for (String key: directoryContent) {
            String value = key
                    .replaceAll(".*/", "")
                    .replaceAll(usedToReplace, usedReplaceBy)
                    .replaceAll(usedFileEnding, "");
            result.put(value, key);
        }
        return result;
    }

    /*==>{ALLSetter.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
    /**
    It's used to specify the file ending in different context. 
    */

    public DirectoryMapReadCall setFileEnding(String fileEnding) {
        this.fileEnding = fileEnding;
        return this;
    }
    
    public String getFileEnding () {
       return this.fileEnding;
    }
    
    public boolean hasFileEnding () {
        return fileEnding!= null && !fileEnding.isEmpty();
    }
    /**
    replaceBy
    */

    public DirectoryMapReadCall setReplaceBy(String replaceBy) {
        this.replaceBy = replaceBy;
        return this;
    }
    
    public String getReplaceBy () {
       return this.replaceBy;
    }
    
    public boolean hasReplaceBy () {
        return replaceBy!= null && !replaceBy.isEmpty();
    }
    /**
    toReplace
    */

    public DirectoryMapReadCall setToReplace(String toReplace) {
        this.toReplace = toReplace;
        return this;
    }
    
    public String getToReplace () {
       return this.toReplace;
    }
    
    public boolean hasToReplace () {
        return toReplace!= null && !toReplace.isEmpty();
    }
/*=>{}.*/
}
