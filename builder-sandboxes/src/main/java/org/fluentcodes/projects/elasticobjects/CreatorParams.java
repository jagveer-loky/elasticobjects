package org.fluentcodes.projects.elasticobjects;

import com.lexicalscope.jewel.cli.Option;

/**
 * Created by werner.diwischek on 16.02.18.
 * http://jewelcli.lexicalscope.com/usage.html nice documentation
 */
public interface CreatorParams {
    @Option(shortName = "t", defaultValue = "md.control")
    String getTemplate();


    @Option(shortName = "f", defaultValue = "addList")
    String getFilter();

}
