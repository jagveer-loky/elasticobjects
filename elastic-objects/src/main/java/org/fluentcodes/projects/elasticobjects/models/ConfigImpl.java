package org.fluentcodes.projects.elasticobjects.models;


import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListMapper;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 10.10.2016.
 */
public abstract class ConfigImpl extends ModelImpl implements Config {
    private static final String SCOPE = "scope";
    public static final String EXPOSE = "expose";
    private final EOConfigsCache configsCache;
    private final String module;
    private final String subModule;
    private final List<Scope> scope;
    private Expose expose;
    private boolean resolved = false;

    public ConfigImpl(EOConfigsCache configsCache, Builder builder) {
        this.module = builder.module;
        this.subModule = builder.subModule;
        this.scope = builder.scope;
        this.expose = builder.expose;
        this.configsCache = configsCache;

        super.setId(builder.id);
        super.setNaturalId(builder.naturalId);
        super.setDescription(builder.description);
        super.setCreationDate(builder.creationDate);
        super.setModificationDate(new Date());
    }

    public ConfigImpl(EOConfigsCache configsCache, Map map) {
        super(map);
        this.module = (String)map.get(ModelConfig.MODULE);
        this.subModule = (String)map.get(ModelConfig.SUB_MODULE);
        this.scope = new ArrayList<>();
        if (map.containsKey(SCOPE)) {
            List<String> scopes = (List<String>)map.get(SCOPE);
            if (scopes!=null&& !scope.isEmpty()) {
                for (String scopeValue: scopes) {
                    scope.add(Scope.valueOf((String) map.get(SCOPE)));
                }
            }
        }
        this.expose = map.containsKey(EXPOSE) ? Expose.valueOf((String)map.get(EXPOSE)) : Expose.NONE;
        this.configsCache = configsCache;
    }

    @Override
    public String getKey() {
        return getNaturalId();
    }

    @Override
    public Expose getExpose() {
        return expose;
    }

    @Override
    public void setExpose(Expose expose) {
        this.expose = expose;
    }
    @Override
    public boolean hasExpose() {
        return expose != null && expose != Expose.NONE;
    }

    protected boolean isResolved() {
        return this.resolved;
    }

    protected void setResolved() {
        this.resolved = true;
    }

    public void resolve()  {
        this.resolved = true;
    }

    /**
     * Defines a target module where generating occurs.
     */
    public String getModule() {
        return this.module;
    }
    //<call keep="JAVA" templateKey="CacheGetter.tpl">

    /**
     * Defines the target  sub module like 'main' or 'test'.
     */
    public String getSubModule() {
        return this.subModule;
    }


    /**
     * A scope for the config value.
     */
    public List<Scope> getScope() {
        return this.scope;
    }

    public boolean hasScope(final Scope scope) {
        if (scope == Scope.ALL) {
            return true;
        }
        if (this.scope == null) {
            return true;
        } else if (this.scope.isEmpty()) {
            return true;
        }
        if (this.scope.contains(scope)) {
            return true;
        }
        return false;
    }

    public EOConfigsCache getConfigsCache() {
        return configsCache;
    }


    @Override
    public String toString() {
        if (this == null) {
            return "{}";
        }
        try {
            return new EOToJSON().toJSON(getConfigsCache(), this);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String serialize() {
        if (this == null) {
            return "{}";
        }
        try {
            return new EOToJSON().setStartIndent(1).toJSON(getConfigsCache(), this);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static class Builder {
        private String module;
        private String subModule;
        private String path;
        private String mapPath;
        private List<Scope> scope;
        private String description;
        private String naturalId;
        private Long id;
        private Date creationDate;
        private Expose expose;
        private boolean expanded = false;

        public Builder() {
            super();
        }
        public String getNaturalId() {
            return naturalId;
        }
        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values)  {
            this.module = ScalarConverter.toString(values.get(ModelConfig.MODULE));
            this.subModule = ScalarConverter.toString(values.get(ModelConfig.SUB_MODULE));
            this.path = ScalarConverter.toString(values.get(ModelConfig.PATH));
            this.mapPath = ScalarConverter.toString(values.get(ListMapper.MAP_PATH));
            this.naturalId = ScalarConverter.toString(values.get(NATURAL_ID));
            this.description = ScalarConverter.toString(values.get(Model.DESCRIPTION));
            this.creationDate = ScalarConverter.toDate(values.get(CREATION_DATE));
            this.id = ScalarConverter.toLong(values.get(ID));
            String exposeAsString = ScalarConverter.toString(values.get(EXPOSE));
            if (exposeAsString == null || exposeAsString.isEmpty()) {
                expose = Expose.INFO;
            }
            else {
                expose = Expose.valueOf(exposeAsString);
            }
            Object scopeAsObject = values.get(EOParams.SCOPE);
            this.scope = new ArrayList<Scope>();
            if (scopeAsObject != null && scopeAsObject instanceof List) {
                List scopeList = (List) scopeAsObject;
                for (Object scopeObject : scopeList) {
                    String scope = ScalarConverter.toString(scopeObject);
                    if (scope == null || scope.isEmpty()) {
                        continue;
                    }
                    this.scope.add(Scope.valueOf(scope));
                }
            }
            this.expanded = ScalarConverter.toBoolean(values.get(F_EXPANDED));
        }


        public boolean isExpanded() {
            return expanded;
        }
    }

}
