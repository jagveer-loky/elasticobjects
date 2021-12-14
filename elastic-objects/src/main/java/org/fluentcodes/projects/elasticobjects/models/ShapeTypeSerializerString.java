package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.regex.Pattern;

public class ShapeTypeSerializerString implements ShapeTypeSerializerInterface {
    public static final Pattern NEWLINE_PATTERN = Pattern.compile("\n");
    public static final Pattern ESCAPE_PATTERN = Pattern.compile("\"");
    public static final Pattern REMOVE_PATTERN = Pattern.compile("\r");
    @Override
    public String asJson(Object value) {
        String string = NEWLINE_PATTERN
                .matcher(ScalarConverter.toString(asString(value)))
                .replaceAll("\\\\n");
        string = ESCAPE_PATTERN
                .matcher(string)
                .replaceAll("\\\\\"");
        return "\"" + REMOVE_PATTERN
                .matcher(string)
                .replaceAll("") + "\"";
    }

}
