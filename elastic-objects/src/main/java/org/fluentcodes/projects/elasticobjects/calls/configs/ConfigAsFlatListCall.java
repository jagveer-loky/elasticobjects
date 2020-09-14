package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListWriteCall;

import java.util.Arrays;
import java.util.List;

/**
 * Creates a flat list from configurations.
 * Created by werner.diwischek on 8.9.2020
 */
public class ConfigAsFlatListCall extends CallImpl {
    private String configType;
    private List<String> keys;
    public ConfigAsFlatListCall() {
        super();
    }

    public String getConfigType() {
        return configType;
    }

    public ConfigAsFlatListCall setConfigType(String configType) {
        this.configType = configType;
        return this;
    }

    public List<String> getKeys() {
        return keys;
    }

    public ConfigAsFlatListCall setKeys(String... keys) {
        this.keys = Arrays.asList(keys);
        return this;
    }

    public ConfigAsFlatListCall setKeys(List<String> keys) {
        this.keys = keys;
        return this;
    }

    @Override
    public String execute(EO eo)  {
        List resultAsListMap = (List) new ConfigCall()
                .setConfigType(configType).execute(eo);
        return new ListWriteCall()
                .asString(eo, resultAsListMap, keys);
    }

}
