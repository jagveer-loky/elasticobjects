package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryListReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileWriteCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;

/**
 * Created by Werner on 30.9.2020.
 */
public class FaqNavCall extends DirectoryListReadCall {
    public FaqNavCall() {
        super();
    }
    public FaqNavCall(final String configKey) {
        super(configKey);
    }

    @Override
    public String execute(final EO eo) {
        List<String> files = (List<String>) super.execute(eo);
        StringBuilder builder = new StringBuilder("<ul>");
        for (String file: files) {
            String link = file.replaceAll(".*/faq", "/faq");
            String header = file.replaceAll(".*/faq/", "")
                    .replaceAll("\\.html","")
                    .replaceAll("_"," ");
            builder.append("\n<a href=\"");
            builder.append(link);
            builder.append("\">");
            builder.append(header);
            builder.append("</a>");
        }
        builder.append("</ul>");
        FileWriteCall call = new FileWriteCall("FaqNav.html");
        call.write(eo.getConfigsCache(), builder.toString());
        return builder.toString();
    }
}
