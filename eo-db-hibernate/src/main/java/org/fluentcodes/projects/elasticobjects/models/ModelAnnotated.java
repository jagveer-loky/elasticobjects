package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by Werner on 25.12.2017.
 * A basic model for persistant beans.
 */
@MappedSuperclass
public class ModelAnnotated implements Model {
    private static final Logger LOG = LogManager.getLogger(ModelAnnotated.class);

    private Long id;

    @Column(name = "naturalId", unique = true, nullable = false)
    private String naturalId;

    @Column(name = "description", length = 256)
    private String description;

    @Column(name = "author", length = 124)
    private String author;

    @Column(name = "modificationDate")
    private Date modificationDate;

    @Column(name = "creationDate")
    private Date creationDate;

    /**
     * Just an empty constructor
     */
    public ModelAnnotated() {
    }

    public String getNaturalId() {
        return naturalId;
    }

    public void setNaturalId(String naturalId) {
        this.naturalId = naturalId;
    }

    @Id
    @GeneratedValue
    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (this.description != null) {
            return;
        }
        this.description = description;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date date) {
        this.modificationDate = date;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    public void setCreationDate() {
        this.creationDate = new Date();
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    public String toString(EOConfigsCache provider) {
        try {
            return new EOBuilder(provider)
                    .set(this).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
