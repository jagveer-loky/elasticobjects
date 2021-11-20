package org.fluentcodes.projects.elasticobjects.calls.templates;

public interface InterpreterInterface {
    Object call();
    Indicators getIndicator();
    default String getIndicatorValue() {
        return getIndicator().getIndicator();
    }

}
