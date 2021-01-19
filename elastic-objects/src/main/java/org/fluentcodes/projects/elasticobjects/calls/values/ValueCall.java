package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;

/*=>{javaHeader}|*/

/**
 * For setting values to EO.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 12:04:23 CET 2020
 */
public class ValueCall extends CallImpl implements CallContent,  SimpleCommand {
/*=>{}.*/

    /*=>{javaStaticNames}|*/
   public static final String CONTENT = "content";
/*=>{}.*/

    /*=>{javaInstanceVars}|*/
   private  String content;
/*=>{}.*/

    public ValueCall() {
    }

    public ValueCall(final String content) {
        this();
        this.content = content;
    }
    @Override
    public String execute(final EO eo) {
        super.check(eo);
        //Object value = eo.get();
        if (hasTargetPath()) {
            eo.set(this.content, getTargetPath());
        }
        return super.createReturnString(eo, content.toString());
    }

    /*=>{javaAccessors}|*/
    /**
    A content for different calls. In a template context the content of the markup. 
    */

    public ValueCall setContent(String content) {
        this.content = content;
        return this;
    }
    
    public String getContent () {
       return this.content;
    }
    
    public boolean hasContent () {
        return content!= null && !content.isEmpty();
    }
/*=>{}.*/


}
