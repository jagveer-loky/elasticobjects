package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.values.SimpleValueFromEoCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

import java.util.Arrays;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;

/**
 * Created by Werner on 3.11.2020.
 */
public class JavaImplementsCall extends SimpleValueFromEoCall {

    @Override
    public String execute(final EO eo) {
        if (eo.get()==null) {
            return "";
        }
        try {
            List<String> interfaces = (eo.get() instanceof String) ? Arrays.asList(((String)eo.get()).split(",")) : (List<String>) eo.get();
            StringBuilder interfacePart = new StringBuilder("implements");
            for (String interfaceKey : interfaces) {
                interfacePart.append(" " + interfaceKey + ", ");
            }
            return interfacePart.toString().replaceAll(", $", "");
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
}
