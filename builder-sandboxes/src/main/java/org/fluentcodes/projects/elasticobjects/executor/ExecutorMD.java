package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.fluentcodes.projects.elasticobjects.calls.executor.statics.ValuesMisc;

import java.util.regex.Matcher;

/**
 * Created by werner.diwischek on 29.04.18.
 */
public class ExecutorMD {

    public static Object breedCrumb(Object... values)  {
        String currentKey = ValueParamsHelper.getString(0, values);
        String[] keys = currentKey.split("\\.");
        StringBuilder builder = new StringBuilder((String) link("Home"));
        for (int i = 0; i < keys.length - 1; i++) {
            String key = keys[i];
            builder.append(" - ");
            builder.append(link(key));
        }
        return builder.toString();
    }

    public static Object header(Object... values)  {
        String header = ValueParamsHelper.getStringWithDefault(0, "", values);
        if (header.isEmpty()) {
            return "";
        }
        Integer repeat = ValueParamsHelper.getInt(1, values);
        String top = ValueParamsHelper.getStringWithDefault(2, null, values);
        StringBuilder builder = new StringBuilder("<a name=\"" + header + "\"></a>\n");
        builder.append(ValuesMisc.repeat("#", repeat));

        if (top != null) {
            builder.append(" <a href=\"#" + top + "\">&uarr;</a>");
        }
        builder.append(" ");
        builder.append(header);
        return builder.toString();
    }

    public static Object headerOption(Object... values)  {
        String option = ValueParamsHelper.getStringWithDefault(0, "", values);
        if (option.isEmpty()) {
            return "";
        }
        String optionShort = ValueParamsHelper.getStringWithDefault(1, "", values);
        String properties = ValueParamsHelper.getStringWithDefault(2, "", values);
        Integer repeat = ValueParamsHelper.getInt(3, values);
        String top = ValueParamsHelper.getStringWithDefault(4, null, values);

        StringBuilder builder = new StringBuilder("");
        builder.append("--");
        builder.append(option);
        if (!optionShort.isEmpty()) {
            builder.append(", -");
            builder.append(optionShort);
        }
        if (!properties.isEmpty()) {
            builder.append(", ");
            builder.append(properties);
        }
        return header(builder.toString(), repeat, top);
    }

    public static Object link(Object... values)  {
        String link = ValueParamsHelper.getString(0, values);
        String text = ValueParamsHelper.getStringWithDefault(1, link, values);

        return "[" + text + "](" + link + ")";
    }

    public static Object links(Object... values)  {
        String content = ValueParamsHelper.getStringWithDefault(0, "", values);
        if (content.isEmpty()) {
            return "";
        }
        String[] links = content.split("\n");
        StringBuilder builder = new StringBuilder();
        for (String link : links) {
            Matcher matcher = ValuesMisc.linkPattern.matcher(link);

            builder.append("* ");
            if (!matcher.find()) {
                builder.append(link);
            }
            String protocol = matcher.group(1);

            switch (ValuesMisc.PROTOCOLS.get(protocol)) {
                case "remote":
                    builder.append(link(link.split(",")));
                    break;
                case "wiki":
                    builder.append(link(matcher.group(2).split(",")));
                    break;
                case "git":
                    String[] commits = matcher.group(2).split(",");
                    String[] params = new String[commits.length + 1];
                    params[0] = "https://github.com/fluentcodes/sandbox-git/tree/" + commits[0];
                    params[1] = commits[0];
                    builder.append(link(params));
                    break;
                default:
                    builder.append(link(link));
                    break;
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    public static Object footer(Object... values)  {
        return "----\ngenerated [sandbox](http://fluentcodes.com/sandboxes) [markdown](https://en.wikipedia.org/wiki/Markdown) [wiki](https://en.wikipedia.org/wiki/Wiki) with [Elastic Objects](http://elasticobjects.com) by [fluentcodes](https://fluentcodes.com)";
    }
}
