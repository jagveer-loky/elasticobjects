package org.fluentcodes.projects.elasticobjects.models;

/*==>{ALLImport.tpl, javaGenImport/*, JAVA}|*/

import org.fluentcodes.projects.elasticobjects.UnmodifiableMap;
import org.fluentcodes.projects.elasticobjects.domain.BaseConfig;

import java.util.List;
import java.util.Map;

/*=>{}.*/

/**
 * Created by Werner on 10.10.2016.
 */
public abstract class ConfigConfig extends BaseConfig implements ConfigConfigInterface, ConfigProperties {
    /*==>{ALLStaticNames.tpl, fieldBeans/*, JAVA, override eq false}|*/
    /*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldBeans/*, JAVA}|*/
    private  Expose expose;
    private final  String module;
    private final  String moduleScope;
    private final  Map properties;
    private final  List<Scope> scope;
    private final String configModelKey;
    /*=>{}.*/

    public ConfigConfig(Config config) {
        super(config);
        this.module = config.getModule();
        this.moduleScope = config.getModuleScope();
        this.scope = config.getScope();
        this.expose = config.getExpose();
        this.configModelKey = config.getConfigModelKey();
        this.properties = new UnmodifiableMap(config.getProperties());
    }

    /*==>{ALLSetter.tpl, fieldBeans/*, JAVA}|*/

    /**
     expose
     */
    @Override
    public ConfigConfig setExpose(Expose expose) {
        this.expose = expose;
        return this;
    }

    @Override
    public Expose getExpose () {
        return this.expose;
    }

    /**
     Defines a target module where generating occurs.
     */

    @Override
    public String getModule () {
        return this.module;
    }


    public String getConfigModelKey() {
        return configModelKey;
    }

    /**
     Defines scope of the configuration within module, eg 'test' or 'main' .
     */

    @Override
    public String getModuleScope () {
        return this.moduleScope;
    }

    /**
     Properties for configurations.
     */

    @Override
    public Map getProperties () {
        return this.properties;
    }

    /**
     A scope for the cache value.
     */

    @Override
    public List<Scope> getScope () {
        return this.scope;
    }

    /*=>{}.*/
}
