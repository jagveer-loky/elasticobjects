package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;

import java.util.Set;
import java.util.stream.Collectors;

/*=>{javaHeader}|*/

/**
 * For getting a list of keys of all loaded configuration types from the cache like ConfigModel.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 09:38:48 CET 2020
 */
public class ConfigTypesCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

    /*=>{javaStaticNames}|*/
/*=>{}.*/

    /*=>{javaInstanceVars}|*/
/*=>{}.*/
    private SortOrder sortOrder = SortOrder.ASC;
    private Class<? extends ConfigConfigInterface> configClass;

    public ConfigTypesCall() {
        super();
    }

    @Override
    public Object execute(final EO eo) {
        super.check(eo);
        Set<Class<? extends ConfigConfigInterface>> keys = eo.getConfigsCache().getKeys();
        try {
            return keys
                    .stream()
                    .map(x->x.getSimpleName())
                    .sorted(sortOrder.getComparator())
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }
    /*=>{javaAccessors}|*/
/*=>{}.*/
}
