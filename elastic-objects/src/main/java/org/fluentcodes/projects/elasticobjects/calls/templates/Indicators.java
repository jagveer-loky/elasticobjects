package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.HashMap;
import java.util.Map;

public enum Indicators {
    VALUE("."), ATTRIBUTES("&"), VALUES("#"), JSON("@"), SYSTEM("%"), ENV("§");
    private String indicator;
    private static final Map<String, Indicators> INDICATORS_MAP = initIndicatorMap();
    Indicators(final String indicator) {
        this.indicator = indicator;
    }

    String getIndicator() {
        return indicator;
    }

    static Indicators find(final String value) {
        if (INDICATORS_MAP.containsKey(value)) {
            return INDICATORS_MAP.get(value);
        }
        throw new EoException("Could not found indicator for '" + value + "'");
    }

    private static final Map<String, Indicators> initIndicatorMap() {
        final Map<String, Indicators> indicatorsMap = new HashMap<>();
        for (Indicators indicator: Indicators.values()) {
            indicatorsMap.put(indicator.indicator, indicator);
        }
        return indicatorsMap;
    }
}
