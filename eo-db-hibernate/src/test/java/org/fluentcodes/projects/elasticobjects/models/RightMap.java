package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Werner Diwischek on 09.10.2016.
 */

@Entity
public class RightMap {
    private static transient Logger LOG = LogManager.getLogger(RightMap.class);
    private Long id;
    private String rightName;
    private Map<String, LeftMap> leftMap = new HashMap();

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "rightName", unique = true, nullable = false)
    public String getRightName() {
        return this.rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    @ManyToMany
    @JoinTable(name = "RightLeft",
            joinColumns = @JoinColumn(name = "left_id"),
            inverseJoinColumns = @JoinColumn(name = "right_id")
    )
    @MapKey(name = "leftName")
    public Map<String, LeftMap> getLeftMap() {
        return this.leftMap;
    }

    public void setLeftMap(Map<String, LeftMap> leftMap) {
        this.leftMap = leftMap;
    }


}
