package org.fluentcodes.projects.elasticobjects.domain;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Date;
import java.util.Map;

/**
 * Created by Werner on 10.10.2017.
 */
public interface Base extends BaseConfigInterface {

    String AUTHOR = "author";
    String CREATION_DATE = "creationDate";
    String DESCRIPTION = "description";
    String ID = "id";
    String MODIFICATION_DATE = "modificationDate";
    String NATURAL_ID = "naturalId";

    void setId(Long id);

    default void mergeId(Object value) {
        if (value == null) {
            return;
        }
        if (hasId()) {
            return;
        }
        setId(ScalarConverter.toLong(value));
    }

    void setDescription(String description);

    default String mergeDescription(Object value) {
        if (value == null) {
            return getDescription();
        }
        if (hasDescription()) {
            return getDescription();
        }
        setDescription(ScalarConverter.toString(value));
        return getDescription();
    }

    Base setNaturalId(String naturalId);

    default void mergeNaturalId(Object value) {
        if (value == null) {
            return;
        }
        if (hasNaturalId()) {
            return;
        }
        setNaturalId(ScalarConverter.toString(value));
    }

     void setCreationDate(Date creationDate);

    default void mergeCreateDate(Object value) {
        if (value == null) {
            return;
        }
        if (hasCreationDate()) {
            return;
        }
        setCreationDate(ScalarConverter.toDate(value));
    }

    void setCreationDate();

    void setModificationDate();
    void setAuthor(String author);

    default void mergeAuthor(Object value) {
        if (value == null) {
            return;
        }
        if (hasAuthor()) {
            return;
        }
        setAuthor(ScalarConverter.toString(value));
    }


}
