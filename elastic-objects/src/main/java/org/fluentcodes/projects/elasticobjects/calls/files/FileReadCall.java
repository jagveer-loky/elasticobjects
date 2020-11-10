package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.xpect.IOBytes;
import org.fluentcodes.tools.xpect.IOString;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigReadCommand;
/**
 * Read content of a file. Put it to targetPath in in when targetPath not equals "_asString".
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Nov 10 15:48:03 CET 2020
 */
public class FileReadCall extends FileCall implements ConfigReadCommand {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/
    public FileReadCall() {
        super();
    }

    public FileReadCall(final String fileConfigKey) {
        super(fileConfigKey);
    }
    public FileReadCall(final String hostConfigKey, final String fileConfigKey) {
        super(hostConfigKey, fileConfigKey);
    }

    @Override
    public Object execute(final EO eo)  {
        return createReturnString(eo, read(eo));
    }

    public String read(final EO eo)  {
        FileConfig fileConfig = super.init(PermissionType.READ, eo);
        if (fileConfig.hasCachedContent()) {
            return fileConfig.getCachedContent();
        }
        String filePath = fileConfig.getFilePath() + "/" + fileConfig.getFileName();
        String result = read(eo, filePath);
        if (fileConfig.isCached()) {
            fileConfig.setCachedContent(result);
        }
        return result;
    }

    protected static String read(final EO eo, String filePath)  {
        filePath = ParserSqareBracket.replacePathValues(filePath, eo);
        return new IOString().setFileName(filePath).read();
    }

    public static byte[] readBinary(final String filePath, final String fileName)  {
        if (filePath==null || filePath.isEmpty()) {
            throw new EoException("No fileName provided for DirectoryConfig read.");
        }
        if (fileName==null || fileName.isEmpty()) {
            throw new EoException("No fileName provided for DirectoryConfig read.");
        }
        return new IOBytes().setFileName(filePath + Path.DELIMITER + fileName).read();
    }
    /*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/
}
