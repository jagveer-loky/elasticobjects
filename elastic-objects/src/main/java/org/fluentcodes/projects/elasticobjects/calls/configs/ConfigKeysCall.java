package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.Expose;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Werner on 14.07.2020.
 */
public class ConfigKeysCall extends CallImpl{
    private String configType;
    private String configFilter = ".*";
    private Expose expose = Expose.NONE;
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

    public void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>4) {
            throw new EoException("Short form should have form '<configType>[,<naturalId>][,<expose>][,<targetPath>]' with max length 3 but has size " + array.length + ": '" + values + "'." );
        }
        setByString(array);
    }

    protected void setByString(final String[] array) {
        if (array == null||array.length == 0) {
            throw new EoException("Set by empty input values");
        }
        if (array.length>0) {
            setConfigType(array[0]);
        }
        if (array.length>1) {
            setConfigFilter(array[1]);
        }
        if (array.length>2) {
            setExpose(Expose.valueOf(array[2]));
        }
        if (array.length>3) {
            setTargetPath(array[3]);
        }
    }

    @Override
    public Object execute(final EO eo) {
        super.check(eo);
        if (configType == null && configClass == null) {
            throw new EoException("Problem no config type defined.");
        }
        if (ParserSqareBracket.containsStartSequence(configType)) {
            configType = new ParserSqareBracket(configType).parse(eo);
        }
        if (ParserSqareBracket.containsStartSequence(configFilter)) {
            configFilter = new ParserSqareBracket(configFilter).parse(eo);
        }
        if (configClass == null) {
            ModelConfig configTypeConfig = eo.getConfigsCache().findModel(configType);
            configClass = configTypeConfig.getModelClass();
        }
        Set<String> keys = eo.getConfigsCache().getConfigKeys(configClass, expose);
        try {
            return super.createReturnType(eo, keys
                    .stream()
                    .filter(x->x.matches(configFilter))
                    .sorted(sortOrder.getComparator())
                    .collect(Collectors.toList())
            );
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }

    public String getConfigType() {
        return configType;
    }

    public ConfigKeysCall setConfigType(String configName) {
        this.configType = configName;
        return this;
    }

    public String getConfigFilter() {
        return configFilter;
    }

    public ConfigKeysCall setConfigFilter(String configFilter) {
        this.configFilter = configFilter;
        return this;
    }

    public Expose getExpose() {
        return expose;
    }

    public ConfigKeysCall setExpose(Expose expose) {
        this.expose = expose;
        return this;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public ConfigKeysCall setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }
}
