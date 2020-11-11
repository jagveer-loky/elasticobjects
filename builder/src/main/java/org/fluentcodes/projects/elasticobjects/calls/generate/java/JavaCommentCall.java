package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
/**
 * Replaces new lines in description with a starting " * " for new line. 
 * Foreach pattern {@link &gt;Class>[} .,] a comment link {{@link link} &gt;Class>} will be generated. 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:41:22 CET 2020
 */
public class JavaCommentCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

/*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
/*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
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

        String comment = ((String)eo.get()).replaceAll("\n", "\n * ");

        if (comment.contains("@")) {
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
        return comment;
    }
/*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/
}
