package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 3.08.2020.
 */
public class StringLowerCall extends CallImpl  implements SimpleCommand {
    @Override
    public String execute(final EO eo) {
        super.check(eo);
        try {
            return lower((String) eo.get());
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }

    public static String lower(String item) {
        if (item == null) {
            throw new EoException("String is null");
        }
        if (item.isEmpty()) {
            return "";
        }

        char[] chars = item.toCharArray();
        StringBuilder builder = new StringBuilder();
        char previous = 0;
        for (char entry : chars) {
            if ((entry > 96 && entry < 123) || entry == 228 || entry == 246 || entry == 252) {
                if (previous == 45 || previous == 95) {
                    int upper = (int) entry - 32;
                    builder.append((char) upper);
                } else {
                    builder.append(entry);
                }
            } else if ((entry > 64 && entry < 91) || entry == 196 || entry == 214 || entry == 220) {
                if (previous == 45 || previous == 95) {
                    builder.append(entry);
                } else {
                    int lower = (int) entry + 32;
                    builder.append((char) lower);
                }
            } else if (entry == 45 || entry == 95) {
            }
            previous = entry;
        }
        return builder.toString();
    }
}
