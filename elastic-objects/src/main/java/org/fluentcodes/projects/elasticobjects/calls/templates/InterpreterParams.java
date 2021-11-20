package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

import static org.fluentcodes.projects.elasticobjects.calls.templates.Parser.DEFAULT_SEPARATOR;

abstract class InterpreterParams implements InterpreterInterface{
    private EO eo;
    private TemplateMarker templateMarker;
    private String callDirective;
    private String content;
    private String defaultValue;



    EO getEo() {
        return eo;
    }

    TemplateMarker getTemplateMarker() {
        return templateMarker;
    }

    String getCallDirective() {
        return callDirective;
    }

    String getContent() {
        return content;
    }

    String getDefaultValue() {
        return defaultValue;
    }

    InterpreterParams setEo(EO eo) {
        this.eo = eo;
        return this;
    }

    InterpreterParams setTemplateMarker(TemplateMarker templateMarker) {
        this.templateMarker = templateMarker;
        return this;
    }

    InterpreterParams setCallDirective(String callDirective) {
        this.callDirective = callDirective;
        return this;
    }

    InterpreterParams setContent(String content) {
        this.content = content;
        return this;
    }

    boolean hasContent() {
        return content != null && ! content.isEmpty();
    }

    boolean hasDefaultValue() {
        return defaultValue != null && ! defaultValue.isEmpty();
    }

    boolean hasCallDirective() {
        return callDirective != null && ! callDirective.isEmpty();
    }

    InterpreterParams setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    ConfigMaps getConfigMaps() {
        return eo.getConfigsCache();
    }

    String createCallDirective() {
        final String defaultValue = hasDefaultValue() ? DEFAULT_SEPARATOR + getDefaultValue() : "";
        final String value = getIndicatorValue() +
            getTemplateMarker().getStart() +
            getCallDirective() +
                defaultValue +
            getTemplateMarker().getContinueSequence();
        return value;
    }
}
