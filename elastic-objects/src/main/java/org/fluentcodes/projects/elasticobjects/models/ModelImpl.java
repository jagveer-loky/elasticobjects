package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.ConfigImpl;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.eo.EORoot;


import java.util.Date;

/**
 * Created by Werner on 14.12.2017.
 * A basic model used by ordinary beans
 */
public class ModelImpl implements Model {
    private static final Logger LOG = LogManager.getLogger(ModelImpl.class);
    //<call keep="JAVA" templateKey="BeanInstanceVars.tpl">

    private Long id;
    private String description;
    private String naturalId;
    private Date creationDate;
    private String author;
    //</call>
    private Date modificationDate;

    /**
     * Just an empty constructor since basic
     */
    public ModelImpl() {
    }

    //<call keep="JAVA" templateKey="BeanSetter.tpl">

    /**
     * The id with a autonumbering
     */
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * A description of the model used by every model extending BaseClassImpl.
     */
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The naturalKey for all config {@link ConfigImpl}
     */
    public String getNaturalId() {
        return this.naturalId;
    }

    public void setNaturalId(String naturalId) {
        this.naturalId = naturalId;
    }

    /**
     * Used to define the creation of an item.
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

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
    //</call>

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
            return new EORoot(provider,this).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
