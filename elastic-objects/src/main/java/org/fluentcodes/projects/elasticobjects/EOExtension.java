package org.fluentcodes.projects.elasticobjects;

/**
 * Created by Werner on 19.03.2017.
 */
public interface EOExtension {
    Object doBeforeMap(IEOObject adapter, Object object) ;

    void doAfterMap(IEOObject adapter) ;
}
