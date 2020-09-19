package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Map;

/**
 * Created 9.6.2018
 */

public class EO_STATIC {

    public static final String MAIN = "main";
    public static final String TEST = "test";

    public static final String CONFIG = "Config";
    public static final String JSON = "Json";


    public static final String F_PROVIDE_IN_ACTION = "provideInAction";
    public static final String F_DEFAULT_VALUE = "defaultValue";

    public static final String M_STRING = "String";

    public static final String M_MODEL_INTERFACE = "ModelInterface";
    public static final String M_MAP = "Map";
    public static final String M_CONFIG_IMPL = "ConfigImpl";

    public static final String F_UPPER_ID_KEY = "upper.ID";

    public static final String F_INPUT_KEY = "inputKey";
    public static final String F_UPPER_ID = "ID";
    public static final String F_VIEW_KEY = "viewKey";
    public static final String F_NAME = "name";
    public static final String F_EXPANDED = "expanded";
    public static final String F_AND = "and";
    public static final String F_PATH_PATTERN = "pathPattern";


    public static final String J_MODULE_CONFIG_JSON = "ModuleConfig.json";
    public static final String CON_SPACE = " ";

    public static final void addMap(Map map, Object... keyValues)  {
        for (int i = 0; i < keyValues.length; i++) {
            if (i == keyValues.length - 1) {
                throw new EoException("Uneven key value pairs at " + i);
            }
            map.put(keyValues[i], keyValues[i + 1]);
            i++;
        }
    }
}
