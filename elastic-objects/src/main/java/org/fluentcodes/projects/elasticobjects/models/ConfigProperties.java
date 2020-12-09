package org.fluentcodes.projects.elasticobjects.models;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
import java.util.Map;
/**
 * Created by Werner on 20.11.2020.
 */
public interface ConfigProperties  {
    /*=>{}.*/
    /*==>{INTERFACEStaticNames.tpl, fieldBeans/*, super eq false, JAVA}|*/
    String PROPERTIES = "properties";
/*=>{}.*/

    /*==>{INTERFACESetter.tpl, fieldBeans/*, super eq false, JAVA}|*/
    default boolean hasProperties() {
        return getProperties()!=null && !getProperties().isEmpty();
    }

    /**
     Properties for configurations.
     */
    Map getProperties();
    /*=>{}.*/
}
