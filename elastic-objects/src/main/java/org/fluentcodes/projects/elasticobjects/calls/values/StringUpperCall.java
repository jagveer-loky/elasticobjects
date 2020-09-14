package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 3.08.2020.
 */
public class StringUpperCall extends SimpleValueFromEoCall {
    private static final Logger LOG = LogManager.getLogger(StringUpperCall.class);

    @Override
    public String execute(final EO eo) {
        super.check(eo);
        try {
            return upper((String)eo.get());
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }

    public static String upper(String item) {
        if (item == null || item.isEmpty()) {
            throw new EoException("Null or empty value");
        }

        char[] chars = item.toCharArray();
        StringBuilder builder = new StringBuilder();
        char previous = 0;
        for (char entry : chars) {
            if ((entry > 96 && entry < 123) || entry == 228 || entry == 246 || entry == 252) {
                int upper = (int) entry - 32;
                builder.append((char) upper);
            } else if ((entry > 47 && entry < 58) || (entry > 64 && entry < 91) || entry == 196 || entry == 214 || entry == 220) {
                if ((previous > 47 && previous < 58) || (previous > 96 && previous < 123) || previous == 228 || previous == 246 || previous == 252) {
                    builder.append("_");
                }
                builder.append(entry);
            } else if (entry < 65 && !(entry > 47 && entry < 58)) {
                builder.append("_");
            } else if (entry > 90 && entry < 97) {
                builder.append("_");
            }
            previous = entry;
        }
        return builder.toString();
    }


}
