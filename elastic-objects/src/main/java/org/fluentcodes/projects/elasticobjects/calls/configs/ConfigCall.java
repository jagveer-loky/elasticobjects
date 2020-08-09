package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

import java.util.List;
import java.util.Map;

/**
 * Call set parts of the config cache to the adapter.
 * Created by werner.diwischek on 10.6.2018
 */
public class ConfigCall extends ConfigKeysCall {
    private static final Logger LOG = LogManager.getLogger(ConfigCall.class);
    private String filterModule;
    private String filterSubModule;

    public ConfigCall() {
        super();
    }

    public ConfigCall(final Class<? extends Config> configClass) {
        super(configClass);
    }

    public ConfigCall(final Class<? extends Config> configClass, final String configFilter) {
        super(configClass, configFilter);
    }

    public boolean hasFilterModule() {
        return filterModule != null && !filterModule.isEmpty();
    }

    public String getFilterModule() {
        return filterModule;
    }

    public ConfigCall setFilterModule(final String entry) {
        this.filterModule = entry;
        return this;
    }

    public boolean hasFilterSubModule() {
        return filterSubModule != null && !filterSubModule.isEmpty();
    }

    public String getFilterSubModule() {
        return filterSubModule;
    }

    public ConfigCall setFilterSubModule(final String entry) {
        this.filterSubModule = entry;
        return this;
    }

    @Override
    public List execute(EO eo)  {
        EO result = new EoRoot(eo.getConfigsCache(),List.class,Map.class);
        result.setSerializationType(JSONSerializationType.STANDARD);
        List<String> keys = super.execute(eo);
        ModelConfig model  = eo.getConfigsCache().findModel(getConfigType());
        Class configClass = model.getModelClass();
        for (String key : keys) {
            Config configEntry = (Config) eo.getConfigsCache().find(configClass, key);
            try {
                if (hasFilterModule() && (configEntry.getModule() == null || !configEntry.getModule().equals(this.getFilterModule()))) {
                    continue;
                }
                if (hasFilterSubModule() && (configEntry.getSubModule() == null || !configEntry.getSubModule().equals(this.getFilterSubModule()))) {
                    continue;
                }
            } catch (Exception e) {
                throw new EoException(e);
            }
            result.set(configEntry, key);
        }
        return (List)result.get();
    }

}
