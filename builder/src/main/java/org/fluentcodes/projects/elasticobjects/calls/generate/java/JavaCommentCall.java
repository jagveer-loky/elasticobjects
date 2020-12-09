package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
/**
 * Replaces new lines in description with a starting " * " for new line. 
 * Foreach pattern {@link &gt;Class>[} .,] a comment link  &gt;Class>} will be generated.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:41:22 CET 2020
 */
public class JavaCommentCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

/*==>{ALLStaticNames.tpl, fieldBeans/*, override eq false, JAVA|>}|*/
/*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/
    private static final Pattern linkPattern = Pattern.compile("@([^\\s\\.,]*)([\\s\\.,])");


    @Override
    public String execute(final EO eo) {
        if (eo == null) {
            return "Null eo for " + this.getClass().getSimpleName();
        }
        if (eo.get() == null) {
            return "Null value in eo " + this.getClass().getSimpleName();
        }
        if (!(eo.getModel().isScalar())) {
            return "Non scalar value in eo " + this.getClass().getSimpleName();
        }
        if (eo.getModelClass() != String.class) {
            return "Non string value in eo " + this.getClass().getSimpleName();
        }
        return REPLACE_LINKS((String)eo.get(DESCRIPTION));
    }
    public static String FIELD_COMMENT(final String comment) {
        return "/* " + REPLACE_LINKS(comment) + "*/";
    }


    public static String REPLACE_LINKS(final String comment) {
        String result = comment.replaceAll("\n", "\n * ");
        if (!result.contains("@")) {
            return result;
        }
        StringBuilder replaced = new StringBuilder();
        Matcher matcher = linkPattern.matcher(comment);
        int end = 0;
        while (matcher.find()) {
            replaced.append(comment.substring(end, matcher.start()));
            end = matcher.end();
            replaced.append("{@link " + matcher.group(1) + "}");
            replaced.append(matcher.group(2));
        }
        replaced.append(comment.substring(end, comment.length()));
        return replaced.toString();
    }
/*==>{ALLSetter.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/
}
