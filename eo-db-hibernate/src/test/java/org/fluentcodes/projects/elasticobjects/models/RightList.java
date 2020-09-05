package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Werner Diwischek on 09.10.2016.
 */

@Entity
public class RightList {
    private static transient Logger LOG = LogManager.getLogger(RightList.class);
    private Long id;
    private String rightName;
    private List<LeftList> leftList = new ArrayList();

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
    public List<LeftList> getLeftList() {
        return leftList;
    }

    public void setLeftList(List<LeftList> leftList) {
        this.leftList = leftList;
    }
}
