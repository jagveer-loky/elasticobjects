package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
/**
 * Serialize the value of the sourcePath as JSON and write it to a file
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Nov 10 16:15:09 CET 2020
 */
public class JsonWriteCall extends FileWriteCall  {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
   public static final String INDENT = "indent";
   public static final String SERIALIZATION_TYPE = "serializationType";
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
   private  Integer indent;
   private  JSONSerializationType serializationType;
/*=>{}.*/

    public JsonWriteCall()  {
        super();
    }

    public JsonWriteCall(final String configKey) {
        super(configKey);
    }

    @Override
    public String execute(final EO eo)  {
        if (!hasSerializationType()) {
            serializationType = JSONSerializationType.STANDARD;
        }
        if (!hasIndent()) {
            indent = 1;
        }
        setContent(new EOToJSON()
                .setSerializationType(serializationType)
                .setStartIndent(indent)
                .toJSON(eo));
        return super.execute(eo);
    }

    /*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
    /**
    The number of indent for serialization level.
    */

    public JsonWriteCall setIndent(Integer indent) {
        this.indent = indent;
        return this;
    }

    public Integer getIndent () {
       return this.indent;
    }
    
    public boolean hasIndent () {
        return indent!= null;
    }
    /**
    The types of Serialization. 
    */

    public JsonWriteCall setSerializationType(JSONSerializationType serializationType) {
        this.serializationType = serializationType;
        return this;
    }
    
    public JSONSerializationType getSerializationType () {
       return this.serializationType;
    }
    
    public boolean hasSerializationType () {
        return serializationType!= null;
    }
/*=>{}.*/
}
