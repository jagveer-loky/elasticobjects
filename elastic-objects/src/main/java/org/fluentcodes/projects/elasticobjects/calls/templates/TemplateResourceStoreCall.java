package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileWriteCall;

/*==>{TemplateResourceCall->ALLHeader.tpl, ., JAVA|>}|*/
/**
 * Executes a TemplateRecourceCall and then a FileWriteCall wth the targetConfigKey.
 * Created by Werner Diwischek at date Fri Nov 06 08:23:14 CET 2020.
 */
public class TemplateResourceStoreCall extends TemplateResourceCall  {
/*=>{}.*/

    /*==>{TemplateResourceCall->ALLStaticNames.tpl, fieldMap/*, JAVA, override eq false|>}|*/
   public static final String TARGET_FILE_CONFIG_KEY = "targetFileConfigKey";
/*=>{}.*/

    /*==>{TemplateResourceCall->ALLInstanceVars.tpl, fieldMap/*, JAVA|>}|*/
   private  String targetFileConfigKey;
/*=>{}.*/

    public TemplateResourceStoreCall() {
        super();
    }

    public TemplateResourceStoreCall(String configKey) {
        super(configKey);
    }

    public String execute(EO eo)  {
        String content = super.execute(eo);
        String targetFile = new ParserSqareBracket(getTargetFileConfigKey()).parse(eo);
        return new FileWriteCall(targetFile, content)
                .setCompare(true)
                .execute(eo);
    }

    /*==>{TemplateResourceCall->ALLSetter.tpl, fieldMap/*, JAVA|>}|*/
    /**
    A target for persisting template results in TemplateResourceStoreCall. 
    */
    public TemplateResourceStoreCall setTargetFileConfigKey(String targetFileConfigKey) {
        this.targetFileConfigKey = targetFileConfigKey;
        return this;
    }
    
    public String getTargetFileConfigKey () {
       return this.targetFileConfigKey;
    }
    
    public boolean hasTargetFileConfigKey () {
        return targetFileConfigKey!= null && !targetFileConfigKey.isEmpty();
    }
/*=>{}.*/
}
