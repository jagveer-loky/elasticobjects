package org.fluentcodes.projects.elasticobjects.models;

import java.util.Date;

/**
 * Created by Werner on 10.10.2017.
 */
public interface Model {

    String AUTHOR = "author";
    String CREATION_DATE = "creationDate";
    String DESCRIPTION = "description";
    String ID = "id";
    String MODIFICATION_DATE = "modificationDate";
    String NATURAL_ID = "naturalId";
;
    //<call keep="JAVA" templateKey="InterfacesBeanSetter.tpl">

    Long getId();

    void setId(Long id);

    String getDescription();

    void setDescription(String description);

    String getNaturalId();

    void setNaturalId(String naturalId);

    Date getCreationDate();

    void setCreationDate(Date creationDate);


    Date getModificationDate();

    void setCreationDate();

    String getAuthor();

    void setAuthor(String author);


}
