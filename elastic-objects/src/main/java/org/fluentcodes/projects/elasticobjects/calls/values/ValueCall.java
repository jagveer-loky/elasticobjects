package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
/**
 * For setting values to EO.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Nov 10 14:51:17 CET 2020
 */
public class ValueCall extends CallImpl implements CallContent,  SimpleCommand {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
   public static final String CONTENT = "content";
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
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

    /*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
    /**
    A content for different calls. In a template context the content of the markup. 
    */
    @Override
    public ValueCall setContent(String content) {
        this.content = content;
        return this;
    }
    @Override
    public String getContent () {
       return this.content;
    }
    @Override
    public boolean hasContent () {
        return content!= null && !content.isEmpty();
    }
/*=>{}.*/


}
