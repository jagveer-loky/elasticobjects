package org.fluentcodes.projects.elasticobjects.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ConfigImpl;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.Date;
import java.util.Map;

/**
 * Created by Werner on 14.12.2017.
 * A basic model used by ordinary beans
 */
public class BaseImpl implements Base {

    private Long id;
    private String description;
    private String naturalId;
    private Date creationDate;
    private String author;

    private Date modificationDate;

    /**
     * Just an empty constructor since basic
     */
    public BaseImpl() {
    }
    public BaseImpl(Map map) {
        try {
            this.id = (Long) map.get(ID);
            this.naturalId = (String) map.get(NATURAL_ID);
            this.description = (String) map.get(DESCRIPTION);
            this.creationDate = (Date) map.get(CREATION_DATE);
            this.author = (String) map.get(AUTHOR);
            this.modificationDate = new Date();
        }
        catch (Exception e) {
            throw new EoInternalException("Problem setting field with " + map.get(NATURAL_ID));
        }
    }
   /**
     * The id with a autonumbering
     */
    @Override
    public Long getId() {
        return this.id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * A description of the model used by every model extending BaseClassImpl.
     */
    @Override
    public String getDescription() {
        return this.description;
    }
    public boolean hasDescription() {
        return description!=null && !description.isEmpty();
    }
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The naturalKey for all config {@link ConfigImpl}
     */
    @Override
    public String getNaturalId() {
        return this.naturalId;
    }

    @Override
    public BaseImpl setNaturalId(String naturalId) {
        this.naturalId = naturalId;
        return this;
    }
    public boolean hasNaturalId() {
        return naturalId !=null && ! naturalId.isEmpty();
    }

    /**
     * Used to define the creation of an item.
     */
    @Override
    public Date getCreationDate() {
        return this.creationDate;
    }
    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public void setCreationDate() {
        this.creationDate = new Date();
    }

    @Override
    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date date) {
        this.modificationDate = date;
    }

    public String toString(EOConfigsCache provider) {
        try {
            return new EoRoot(provider,this).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
