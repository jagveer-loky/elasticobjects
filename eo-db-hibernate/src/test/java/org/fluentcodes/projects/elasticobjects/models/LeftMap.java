package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.Map;

/**
 * Created by Werner Diwischek on 09.10.2016.
 */

@Entity
public class LeftMap {
    private static transient Logger LOG = LogManager.getLogger(LeftMap.class);
    private Long id;
    private String leftName;
    private Map<String, RightMap> rightMap;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "leftName", unique = true, nullable = false)
    public String getLeftName() {
        return this.leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    @ManyToMany
    @JoinTable(name = "RightLeft",
            joinColumns = @JoinColumn(name = "right_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "left_id", referencedColumnName = "id")
    )
    @MapKey(name = "rightName")
    public Map<String, RightMap> getRightMap() {
        return this.rightMap;
    }

    public void setRightMap(Map<String, RightMap> rightMap) {
        this.rightMap = rightMap;
    }
}
