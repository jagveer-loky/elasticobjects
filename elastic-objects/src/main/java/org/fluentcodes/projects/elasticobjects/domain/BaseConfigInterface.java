package org.fluentcodes.projects.elasticobjects.domain;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Date;
import java.util.Map;

/**
 * Created by Werner on 10.10.2017.
 */
public interface BaseConfigInterface {
    Long getId();
    default boolean hasId() {
        return getId() == null;
    }

    String getDescription();

    default boolean hasDescription() {
        return getDescription()!=null && !getDescription().isEmpty();
    }

    String getNaturalId();

    default boolean hasNaturalId() {
        return getNaturalId()!=null && !getNaturalId().isEmpty();
    }
    Date getCreationDate();
    default boolean hasCreationDate() {
        return getCreationDate() != null;
    }

    Date getModificationDate();

    String getAuthor();
    default boolean hasAuthor() {
        return getAuthor() !=null && !getAuthor().isEmpty();
    }
}
