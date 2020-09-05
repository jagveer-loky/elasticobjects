package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner Diwischek on 09.10.2016.
 */

@Entity
public class Right {
    private static transient Logger LOG = LogManager.getLogger(Right.class);
    private Long id;
    private String rightName;
    private Map<String, Left> leftMap = new HashMap();
    private List<Left> leftList = new ArrayList<>();

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
    @MapKey(name = "leftName")
    @JoinTable(name = "RightLeft",
            joinColumns = @JoinColumn(name = "left_id"),
            inverseJoinColumns = @JoinColumn(name = "right_id")
    )
    public Map<String, Left> getLeftMap() {
        return this.leftMap;
    }

    public void setLeftMap(Map<String, Left> leftMap) {
        this.leftMap = leftMap;
    }

    @ManyToMany
    @JoinTable(name = "RightLeft",
            joinColumns = @JoinColumn(name = "left_id"),
            inverseJoinColumns = @JoinColumn(name = "right_id")
    )
    public List<Left> getLeftList() {
        return leftList;
    }

    public void setLeftList(List<Left> leftList) {
        this.leftList = leftList;
    }
}
