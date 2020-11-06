package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.xpect.IORuntimeException;

/*==>{TemplateResourceCall->ALLHeader.tpl, ., JAVA|>}|*/
/**
 * Parses the content loaded by {@link DirectoryReadCall}.
 * This will use a FileConfig entry using {@link DirectoryConfig} instance. 
 *
 * @author Werner Diwischek
 * @creationDate Sat Oct 10 00:00:00 CEST 2020
 * @modificationDate Fri Nov 06 10:56:45 CET 2020
 */
public class TemplateDirResourceCall extends TemplateResourceCall  {
/*=>{}.*/

    /*==>{TemplateResourceCall->ALLStaticNames.tpl, fieldMap/*, JAVA, override eq false|>}|*/
   public static final String FILE_NAME = "fileName";
/*=>{}.*/

    /*==>{TemplateResourceCall->ALLInstanceVars.tpl, fieldMap/*, JAVA|>}|*/
   private  String fileName;
/*=>{}.*/

    public TemplateDirResourceCall() {
        super();
    }
    public TemplateDirResourceCall(final String configKey) {
        super(configKey);
    }
    public TemplateDirResourceCall(final String configKey, final String fileName) {
        super(configKey);
        this.fileName = fileName;
    }
    @Override
    public void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>5) {
            throw new EoException("Short form should have form '<configKey>[, <sourcePath>][,<targetPath>][,<condition>]' with max length 3 but has size " + array.length + ": '" + values + "'." );
        }
        if (array.length>0) {
            setTemplateFileConfigKey(array[0]);
        }
        if (array.length>1) {
            this.fileName = array[1];
        }
        if (array.length>2) {
            setSourcePath( array[2]);
        }
        if (array.length>3) {
            setTargetPath( array[2]);
        }
        if (array.length>4) {
            setCondition( array[4]);
        }
        if (array.length>5) {
            setKeepCall(KeepCalls.valueOf(array[5]));
        }
        if (!hasSourcePath()) {
            setSourcePath(PathElement.SAME);
        }
        if (!hasTargetPath()) {
            setTargetPath(PathElement.TEMPLATE);
        }
    }

    @Override
    public String execute(EO eo)  {
        if (!init(eo)) {
            return "";
        }
        if (!hasFileName()) {
            throw new EoException("No fileName defined for '" + getTemplateFileConfigKey() + "'");
        }
        if (!(hasTemplateFileConfigKey())) {
            throw new EoException("Problem that TemplateResourceCall with fileName '" + getFileName() + "' expects a configKey value.");
        }
        final String directoryKey = ParserSqareBracket.replacePathValues(getTemplateFileConfigKey(),eo);
        final String fileName = ParserSqareBracket.replacePathValues(getFileName(),eo);

        try {
            String result = new TemplateCall((String)new DirectoryReadCall(directoryKey)
                    .setFileName(fileName)
                    .setLogLevel(getLogLevel())
                    .execute(eo))
                    .execute(eo);
            return result;
        }
        catch (EoException|IORuntimeException e) {
            if (hasLogLevel() && getLogLevel().equals(LogLevel.NONE)) {
                return "";
            }
            throw e;
        }
    }

    /*==>{TemplateResourceCall->ALLSetter.tpl, fieldMap/*, JAVA|>}|*/
    /**
    fileName
    */
    public TemplateDirResourceCall setFileName(String fileName) {
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
