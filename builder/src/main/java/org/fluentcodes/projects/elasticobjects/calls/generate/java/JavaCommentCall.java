package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Werner on 6.11.2020.
 */
public class JavaCommentCall extends CallImpl implements SimpleCommand {
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
}
