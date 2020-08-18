package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;

/**
 * Created by werner.diwischek on 19.02.18.
 */
public class ViewParams {
    private final String viewKey;
    private final String inputKey;

    public ViewParams(Object object) {
        if (object == null || !(object instanceof Map)) {
            this.viewKey = null;
            this.inputKey = null;
            return;
        }
        Map map = (Map) object;
        this.viewKey = ScalarConverter.toString(map.get(EO_STATIC.F_VIEW_KEY));
        this.inputKey = ScalarConverter.toString(map.get(EO_STATIC.F_INPUT_KEY));
    }

    public String getViewKey() {
        return viewKey;
    }

    public String getInputKey() {
        return inputKey;
    }

}
