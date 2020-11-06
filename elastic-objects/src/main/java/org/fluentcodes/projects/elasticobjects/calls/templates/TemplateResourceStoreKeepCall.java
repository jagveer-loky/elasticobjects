package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileWriteCall;

/*==>{TemplateResourceCall->ALLHeader.tpl, ., JAVA|>}|*/
/**
 * First try to read the target file with targetFileConfigKey and use it as template if exists. If not try to use templateFileConfigKey as template with FileReadCall. After processing a FileWriteCall wth the targetConfigKey is executed.
 * Created by Werner Diwischek at date
 * @creationDate Fri Nov 06 07:40:26 CET 2020.
 * @modificationDate test
 * @author Werner Diwischek
 */
public class TemplateResourceStoreKeepCall extends TemplateCall  {
/*=>{}.*/

    /*==>{TemplateResourceCall->ALLStaticNames.tpl, fieldMap/*, JAVA, override eq false|>}|*/
   public static final String TARGET_FILE_CONFIG_KEY = "targetFileConfigKey";
   public static final String TEMPLATE_FILE_CONFIG_KEY = "templateFileConfigKey";
/*=>{}.*/

    /*==>{TemplateResourceCall->ALLInstanceVars.tpl, fieldMap/*, JAVA|>}|*/
   private  String targetFileConfigKey;
   private  String templateFileConfigKey;
/*=>{}.*/

    public TemplateResourceStoreKeepCall(String fileConfigKeyTemplate, String targetConfig) {
        super();
        this.templateFileConfigKey = fileConfigKeyTemplate;
        this.targetFileConfigKey = targetConfig;
    }

    public String execute(EO eo)  {
        // pre-check for write permissions...
        FileConfig targetConfig = eo.getConfigsCache().findFile(this.targetFileConfigKey);
        targetConfig.hasPermissions(PermissionType.WRITE, eo.getRoles());
        try {
            setContent((String) new FileReadCall(targetFileConfigKey).execute(eo));
        }
        catch (Exception e) {
            setContent((String) new FileReadCall(templateFileConfigKey).execute(eo));
        }
        if (!ParserCurlyBracket.containsStartSequence(getContent())) {
            final String targetFileName = ParserSqareBracket.replacePathValues(targetConfig.getUrlPath(), eo);
            final String message = "Skip parsing: '" + targetFileName + "' has no curly start sequences, so nothing will be done!";
            eo.debug(message);
            return message;
        }
        String parsedResult = super.execute(eo);
        return new FileWriteCall(targetFileConfigKey, parsedResult)
                .setCompare(true)
                .execute(eo);
    }

    /*==>{TemplateResourceCall->ALLSetter.tpl, fieldMap/*, JAVA|>}|*/
    /**
    A target for persisting template results in TemplateResourceStoreCall. 
    */
    public TemplateResourceStoreKeepCall setTargetFileConfigKey(String targetFileConfigKey) {
        this.targetFileConfigKey = targetFileConfigKey;
        return this;
    }
    
    public String getTargetFileConfigKey () {
       return this.targetFileConfigKey;
    }
    
    public boolean hasTargetFileConfigKey () {
        return targetFileConfigKey!= null && !targetFileConfigKey.isEmpty();
    }
    /**
    A target for persisting template results in TemplateResourceStoreCall. 
    */
    public TemplateResourceStoreKeepCall setTemplateFileConfigKey(String templateFileConfigKey) {
        this.templateFileConfigKey = templateFileConfigKey;
        return this;
    }
    
    public String getTemplateFileConfigKey () {
       return this.templateFileConfigKey;
    }
    
    public boolean hasTemplateFileConfigKey () {
        return templateFileConfigKey!= null && !templateFileConfigKey.isEmpty();
    }
/*=>{}.*/
}
