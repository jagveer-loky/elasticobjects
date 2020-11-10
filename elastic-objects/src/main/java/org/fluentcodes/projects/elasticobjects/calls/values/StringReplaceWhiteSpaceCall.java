package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 3.08.2020.
 */
public class StringReplaceWhiteSpaceCall extends CallImpl  implements SimpleCommand {

    @Override
    public String execute(final EO eo) {
        super.check(eo);
        try {
            // https://alvinalexander.com/blog/post/java/remove-non-alphanumeric-characters-java-string/
            return ((String) eo.get()).replaceAll("[^a-zA-Z0-9]","_");
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
}
