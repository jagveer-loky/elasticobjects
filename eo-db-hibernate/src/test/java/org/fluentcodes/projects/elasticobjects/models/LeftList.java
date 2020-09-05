package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Werner Diwischek on 09.10.2016.
 */

@Entity
public class LeftList {
    private static transient Logger LOG = LogManager.getLogger(LeftList.class);
    private Long id;
    private String leftName;
    private List<RightList> rightList;

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
    public List<RightList> getRightList() {
        return rightList;
    }

    public void setRightList(List<RightList> rightList) {
        this.rightList = rightList;
    }
}
