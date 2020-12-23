package org.fluentcodes.projects.elasticobjects.domain;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfig;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.Date;
import java.util.Map;

/**
 * Created by Werner on 14.12.2017.
 * A basic model used by ordinary beans
 */
public class BaseBean implements Base {

    private Long id;
    private String description;
    private String naturalId;
    private Date creationDate;
    private String author;

    private Date modificationDate;

    /**
     * Just an empty constructor since basic
     */
    public BaseBean() {
    }

    public BaseBean(final String naturalId) {
        this.naturalId = naturalId;
    }

    public void merge(final Base map) {
        mergeId(map.getId());
        mergeNaturalId(map.getNaturalId());
        mergeDescription(map.getDescription());
        mergeCreateDate(map.getCreationDate());
        mergeAuthor(map.getAuthor());
        setModificationDate();
    }

    protected void merge(final Map map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        try {
            mergeId(map.get(ID));
            mergeNaturalId(map.get(NATURAL_ID));
            mergeDescription(map.get(DESCRIPTION));
            mergeCreateDate(map.get(CREATION_DATE));
            mergeAuthor(map.get(AUTHOR));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setModificationDate();
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
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The naturalKey for all config {@link ConfigConfig}
     */
    @Override
    public String getNaturalId() {
        return this.naturalId;
    }

    @Override
    public BaseBean setNaturalId(String naturalId) {
        this.naturalId = naturalId;
        return this;
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

    @Override
    public void setModificationDate() {
        this.modificationDate = new Date();
    }
    public void setModificationDate(Date date) {
        this.modificationDate = date;
    }

    public String toString(EOConfigsCache provider) {
        try {
            return EoRoot.ofValue(provider,this).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
