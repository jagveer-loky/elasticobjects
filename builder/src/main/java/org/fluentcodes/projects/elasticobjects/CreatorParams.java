package org.fluentcodes.projects.elasticobjects;

import com.lexicalscope.jewel.cli.Option;
import org.fluentcodes.projects.elasticobjects.paths.Path;

import java.util.List;

/**
 * Created by werner.diwischek on 16.02.18.
 * http://jewelcli.lexicalscope.com/usage.html nice documentation
 */
public interface CreatorParams {
    String MODELS_MAP_PATH = Path.DELIMITER + "models";
    String FIELDS_MAP_PATH = Path.DELIMITER + "fields";

    @Option(shortName = "e", defaultValue = "JavaControl.tpl")
    String getExecute();

    @Option(shortName = "m", defaultValue = "builder-eo")
    String getModule();

    @Option(shortName = "s", defaultValue = "test")
    String getSubModule();

    @Option(shortName = "f", defaultValue = ".*")
    String getFilter();

    @Option(shortName = "a", defaultValue = {"models.xlsx:Model"})
    List<String> getActionKeys();


}
