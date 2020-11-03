package org.fluentcodes.projects.elasticobjects.models;

//  ===>{"(TemplateResourceCall).":{"sourcePath":"javaGenImport/*", "configKey":"ALLImport.tpl", "keepCall":"JAVA"}}|

import org.fluentcodes.projects.elasticobjects.domain.Base;

import java.util.List;
import java.util.Map;
//=>{}.


/**
 * Created by Werner on 10.10.2016.
 */
public interface ConfigProperties  {
//  ===>{"(TemplateResourceCall).":{"sourcePath":"fieldKeys/*", "configKey":"INTERFACEStaticNames.tpl", "keepCall":"JAVA"}}|
    String PROPERTIES = "properties";
//=>{}.

    default boolean hasProperties() {
        return getProperties()!=null && !getProperties().isEmpty();
    }

    /**
     Properties for configurations.
     */
    Map getProperties();

//=>{}.
}
