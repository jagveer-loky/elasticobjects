package org.fluentcodes.projects.elasticobjects.calls.commands;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

public interface GenerateCommand extends Call {
    GenerateCommand setModule(String moduleScope);
    GenerateCommand setModuleScope(String moduleScope);
    GenerateCommand setFileEnding(String moduleScope);

    default void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>5) {
            throw new EoException("Short form should have form '<module>[,<moduleScope>][,<condition>][,<keepCall>]' with max length 4 but has size " + array.length + ": '" + values + "'." );
        }
        if (array.length>0) {
            setModule(array[0]);
        }
        if (array.length>1) {
            setModuleScope( array[1]);
        }
        if (array.length>2) {
            setFileEnding( array[2]);
        }
    }
}
