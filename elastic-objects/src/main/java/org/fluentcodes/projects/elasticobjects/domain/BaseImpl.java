package org.fluentcodes.projects.elasticobjects.domain;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ConfigImpl;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

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

    public BaseImpl(final String naturalId) {
        this.naturalId = naturalId;
    }

    public BaseImpl(Map map) {
        merge(map);
    }

    public void merge(final Base map) {
        mergeId(map.getId());
        mergeNaturalId(map.getNaturalId());
        mergeDescription(map.getDescription());
        mergeCreateDate(map.getCreationDate());
        mergeAuthor(map.getAuthor());
        this.modificationDate = new Date();
    }

    public void merge(final Map map) {
        if (map == null) {
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
        this.modificationDate = new Date();
    }
   /**
     * The id with a autonumbering
     */
   public boolean hasId() {
       return id == null;
   }
    @Override
    public Long getId() {
        return this.id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void mergeId(Object value) {
        if (value == null) {
            return;
        }
        if (hasId()) {
            return;
        }
        this.id = ScalarConverter.toLong(value);
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

    public String mergeDescription(Object value) {
        if (value == null) {
            return description;
        }
        if (hasDescription()) {
            return description;
        }
        description = ScalarConverter.toString(value);
        return description;
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

    public void mergeNaturalId(Object value) {
        if (value == null) {
            return;
        }
        if (hasNaturalId()) {
            return;
        }
        this.naturalId = ScalarConverter.toString(value);
    }

    /**
     * Used to define the creation of an item.
     */
    public boolean hasCreationDate() {
        return creationDate!=null;
    }

    @Override
    public Date getCreationDate() {
        return this.creationDate;
    }
    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void mergeCreateDate(Object value) {
        if (value == null) {
            return;
        }
        if (hasCreationDate()) {
            return;
        }
        this.creationDate = ScalarConverter.toDate(value);
    }

    public boolean hasAuthor() {
        return author !=null && !author.isEmpty();
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    public void mergeAuthor(Object value) {
        if (value == null) {
            return;
        }
        if (hasAuthor()) {
            return;
        }
        this.author = ScalarConverter.toString(value);
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
