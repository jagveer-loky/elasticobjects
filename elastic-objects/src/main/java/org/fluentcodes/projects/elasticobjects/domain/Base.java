package org.fluentcodes.projects.elasticobjects.domain;

import java.util.Date;

/**
 * Created by Werner on 10.10.2017.
 */
public interface Base {

    String AUTHOR = "author";
    String CREATION_DATE = "creationDate";
    String DESCRIPTION = "description";
    String ID = "id";
    String MODIFICATION_DATE = "modificationDate";
    String NATURAL_ID = "naturalId";

    Long getId();

    void setId(Long id);

    String getDescription();

    void setDescription(String description);

    String getNaturalId();

    Base setNaturalId(String naturalId);

    Date getCreationDate();

    void setCreationDate(Date creationDate);
    Date getModificationDate();

    void setCreationDate();

    String getAuthor();

    void setAuthor(String author);


}
