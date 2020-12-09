package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/

/**
 * For replacing field 'toReplace' by 'replaceBy'.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 11:43:01 CET 2020
 */
public class StringReplaceCall extends CallImpl implements CallContent {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
   public static final String REPLACE_BY = "replaceBy";
   public static final String TO_REPLACE = "toReplace";
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
   private  String replaceBy;
   private  String toReplace;
   private String content;
/*=>{}.*/

    public StringReplaceCall() {
        super();
    }
    public StringReplaceCall(final String toReplace, final String replaceBy) {
        super();
        this.toReplace = toReplace;
        this.replaceBy = replaceBy;
    }

    @Override
    public void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>3) {
            throw new EoException("Short form should have form '<sourcePath>[,<toReplace>][,<replaceBy>]' with max length 3 but has size " + array.length + ": '" + values + "'." );
        }
        if (array.length>0) {
            setSourcePath(array[0]);
        }
        if (array.length>1) {
            setToReplace(array[1]);
        }
        if (array.length>2) {
            setReplaceBy(array[2]);
        }
    }

    @Override
    public String execute(final EO eo) {
        super.check(eo);
        if (eo.get() == null) {
            throw new EoException("Value " + eo.getPathAsString() + " is null ");
        }
        if (toReplace == null) {
            throw new EoException("toReplace is null! ");
        }
        if (replaceBy == null) {
            throw new EoException("replaceBy is null! ");
        }
        String content = null;
        if (hasContent()) {
            content = (String) new TemplateCall(getContent())
                    .setSourcePath(getSourcePath())
                    .execute(eo);
        }
        else {
            if (!(eo.get() instanceof String)) {
                throw new EoException("Value " + eo.getPathAsString() + " for replace is not a String but " + eo.get().getClass());
            }
            content = (String) eo.get();
        }
        return (content.replaceAll(toReplace, replaceBy));
    }

    /*==>{ALLSetter.tpl, fieldBeans/*, super eq false, JAVA|>}|*/

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public StringReplaceCall setContent(String content) {
        this.content = content;
        return this;
    }

    /**
    replaceBy
    */

    public StringReplaceCall setReplaceBy(String replaceBy) {
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

    public StringReplaceCall setToReplace(String toReplace) {
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
