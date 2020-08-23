package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserEoReplace;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Werner on 14.07.2020.
 */
public class ConfigKeysCall extends CallImpl<List>{
    private String configType;
    private String configFilter = ".*";
    private SortOrder sortOrder = SortOrder.ASC;
    private Class<? extends Config> configClass;

    public ConfigKeysCall() {
        super();
    }

    public ConfigKeysCall(final Class<? extends Config> configClass) {
        super();
        this.configClass = configClass;
        this.configType = configClass.getSimpleName();
    }

    public ConfigKeysCall(final String configType) {
        super();
        this.configType = configType;
    }

    public ConfigKeysCall(final Class<? extends Config> configClass, final String configFilter) {
        this(configClass);
        this.configFilter = configFilter;
    }

    @Override
    public List<String> execute(final EO eo) {
        super.check(eo);
        if (configType == null && configClass == null) {
            throw new EoException("Problem no config type defined.");
        }
        if (configType.startsWith("eo->")) {
            configType = new ParserEoReplace(configType).parse(eo);
        }
        if (configFilter.startsWith("eo->")) {
            configFilter = new ParserEoReplace(configFilter).parse(eo);
        }
        if (configClass == null) {
            ModelConfig configTypeConfig = eo.getConfigsCache().findModel(configType);
            configClass = configTypeConfig.getModelClass();
        }
        Set<String> keys = eo.getConfigsCache().getConfigKeys(configClass);
        try {
            return keys
                    .stream()
                    .filter(x->x.matches(configFilter))
                    .sorted(sortOrder.getComparator())
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configName) {
        this.configType = configName;
    }

    public String getConfigFilter() {
        return configFilter;
    }

    public void setConfigFilter(String configFilter) {
        this.configFilter = configFilter;
    }
}
