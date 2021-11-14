package org.fluentcodes.projects.elasticobjects.calls;

import java.util.Map;
/*=>{javaHeader}|*/
/**
 * 
 * A bean container class for Field values 
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 12:24:13 CET 2021
 */
public class HostBean extends PermissionBean implements HostBeanInterface  {
/*=>{}.*/

    public HostBean() {
        super();
        defaultConfigModelKey();
    }

    public HostBean(final String naturalId, final Map<String, Object> map) {
        super(naturalId, map);
        defaultConfigModelKey();
    }

    public HostBean(final Map<String, Object> map) {
        super();
        defaultConfigModelKey();
    }

    public void merge(final Map configMap) {
        super.merge(configMap);
        defaultConfigModelKey();
    }

/*=>{javaAccessors}|*/
/*=>{}.*/



    private void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(HostConfig.class.getSimpleName());
    }

    @Override
    public String toString() {
        return getNaturalId() + " -> " + getUrl();
    }
}
