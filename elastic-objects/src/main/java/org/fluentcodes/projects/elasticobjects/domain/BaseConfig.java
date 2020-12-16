package org.fluentcodes.projects.elasticobjects.domain;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.Date;
import java.util.Map;

/**
 * Created by Werner on 14.12.2017.
 * A basic model used by ordinary beans
 */
public class BaseConfig implements BaseConfigInterface {
    private final Long id;
    private final String description;
    private final String naturalId;
    private final Date creationDate;
    private final String author;

    private final Date modificationDate;

    /**
     * Just an empty constructor since basic
     */

    public BaseConfig(final Base bean) {
        this.author = bean.getAuthor();
        this.creationDate = bean.getCreationDate();
        this.description = bean.getDescription();
        this.id = bean.getId();
        this.modificationDate = bean.getModificationDate();
        this.naturalId = bean.getNaturalId();
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getNaturalId() {
        return this.naturalId;
    }

    @Override
    public Date getCreationDate() {
        return this.creationDate;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public Date getModificationDate() {
        return modificationDate;
    }

    public String toString(EOConfigsCache provider) {
        try {
            return new EoRoot(provider,this).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
