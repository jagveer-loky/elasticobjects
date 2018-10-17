package org.fluentcodes.projects.elasticobjects.config;

import java.util.List;

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

}
