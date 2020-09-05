package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner Diwischek on 09.10.2016.
 */

@Entity
public class Left {
    private static transient Logger LOG = LogManager.getLogger(Left.class);
    private Long id;
    private String leftName;
    private Map<String, Right> rightMap;
    private List<Right> rightList;

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
    @MapKey(name = "rightName")
    @JoinTable(name = "RightLeft",
            joinColumns = @JoinColumn(name = "right_id"),
            inverseJoinColumns = @JoinColumn(name = "left_id")
    )
    public Map<String, Right> getRightMap() {
        return this.rightMap;
    }

    public void setRightMap(Map<String, Right> rightMap) {
        this.rightMap = rightMap;
    }

    @ManyToMany
    @JoinTable(name = "RightLeft",
            joinColumns = @JoinColumn(name = "right_id"),
            inverseJoinColumns = @JoinColumn(name = "left_id")
    )
    public List<Right> getRightList() {
        return rightList;
    }

    public void setRightList(List<Right> rightList) {
        this.rightList = rightList;
    }
}
