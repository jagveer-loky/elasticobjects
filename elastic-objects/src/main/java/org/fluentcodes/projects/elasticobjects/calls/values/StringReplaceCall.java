package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
/**
 * For replacing field 'toReplace' by 'replaceBy'.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Nov 10 14:33:04 CET 2020
 */
public class StringReplaceCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
   public static final String REPLACE_BY = "replaceBy";
   public static final String TO_REPLACE = "toReplace";
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
   private  String replaceBy;
   private  String toReplace;
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
        if (array.length>1) {
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
        if (!(eo.get() instanceof String)) {
            throw new EoException("Value " + eo.getPathAsString() + " for replace is not a String but " + eo.get().getClass());
        }
        return ((String)eo.get()).replaceAll(toReplace, replaceBy);
    }

    /*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
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
