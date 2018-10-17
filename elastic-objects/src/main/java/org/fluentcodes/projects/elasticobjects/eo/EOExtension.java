package org.fluentcodes.projects.elasticobjects.eo;

/**
 * Created by Werner on 19.03.2017.
 */
public interface EOExtension {
    Object doBeforeMap(EO adapter, Object object) throws Exception;

    void doAfterMap(EO adapter) throws Exception;
}
