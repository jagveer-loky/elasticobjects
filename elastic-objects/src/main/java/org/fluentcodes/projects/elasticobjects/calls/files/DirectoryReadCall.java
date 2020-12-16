package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.xpect.IOString;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/

/**
 * Defines a file read operation for a directory configuration {@link DirectoryConfig} 
 * specified by fileConfigKey defined in {@link FileReadCall}. 
 * The fileName instance field will be validated against the fileName in the FileConfig as pattern.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 09:41:09 CET 2020
 */
public class DirectoryReadCall extends FileReadCall {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
   public static final String FILE_NAME = "fileName";
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
   private  String fileName;
/*=>{}.*/

    public DirectoryReadCall() {
        super();
    }

    public DirectoryReadCall(final String configKey) {
        super(configKey);
    }

    public DirectoryReadCall(final String configKey, final String fileName) {
        super(configKey);
        this.fileName = fileName;
    }


    @Override
    public String execute(final EO eo)  {
        String result = read(eo);
        return createReturnString(eo, result);
    }

    public String read(final EO eo)  {
        if (!hasFileName()) {
            throw new EoException("No fileName provided for DirectoryConfig read.");
        }

        DirectoryConfig config = (DirectoryConfig)init(PermissionType.READ, eo);
        if (config.hasCachedContent(fileName)) {
            return config.getCachedContent(fileName);
        }
        final String replaceFileName = ParserSqareBracket.replacePathValues(fileName, eo);
        if (replaceFileName.contains("..")) {
            throw new EoException("FileName in call for read '"+ replaceFileName + "' has some backPropagation!");
        }
        if (!replaceFileName.matches(config.getFileName())) {
            throw new EoException("fileName in call for read '"+ replaceFileName + "' does not match fileName in  DirectoryConfig '" + getFileName() + "'.");
        }

        final String filePath = ParserSqareBracket.replacePathValues(config.getFilePath() + "/" + replaceFileName, eo);
        final String content = new IOString().setFileName(filePath).read();
        if (config.getCached()) {
            config.setCachedContent(fileName, content);
        }
        return content;
    }

    /*==>{ALLSetter.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
    /**
    A fileName used in different calls and configs like {@link FileConfig} or {@link DirectoryConfig}. 
    */

    public DirectoryReadCall setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }
    
    public String getFileName () {
       return this.fileName;
    }
    
    public boolean hasFileName () {
        return fileName!= null && !fileName.isEmpty();
    }
/*=>{}.*/
}
