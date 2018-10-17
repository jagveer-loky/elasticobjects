package org.fluentcodes.projects.elasticobjects.executor.statics;

/**
 * Created by werner.diwischek on 06.02.18.
 */
public class ValuesContentHtml extends ValuesContent {

    public static Object link(Object... values) throws Exception {
        String link = getString(0, values);
        String text = getStringWithDefault(1, link, values);
        return "<a href=\"" + link + "\">" + text + "</a> ";
    }
}
