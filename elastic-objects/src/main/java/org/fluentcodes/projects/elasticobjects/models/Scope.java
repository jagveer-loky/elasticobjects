package org.fluentcodes.projects.elasticobjects.models;

import java.util.List;
import java.util.Optional;

/**
 * Created by werner.diwischek on 09.12.17.
 */
public enum Scope {
    DEV(), TEST(), QS(), INT(), STAGE(), PROD(), ALL(), CREATE();

    Scope() {

    }

    public boolean shouldLoaded(List<Scope> scope) {
        if (scope == null) {
            return true;
        }
        if (this == ALL) {
            return true;
        }
        if (scope.size() == 0) {
            return true;
        }
        if (scope.contains(ALL)) {
            return true;
        }
        return scope.contains(this);
    }

    public Optional<String> filter(String naturalId) {
        if (!naturalId.startsWith("(")) {
            return Optional.of(naturalId);
        }
        if (!naturalId.startsWith("(" + name() + ")")) {
            return Optional.empty();
        }
        return Optional.of(naturalId.replace("(" + name() + ")", ""));
    }

}
