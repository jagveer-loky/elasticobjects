package org.fluentcodes.projects.elasticobjects.assets.byclass;

/**
 * Created by Werner Diwischek on 09.10.2016.
 */

public class MainClass {
    private Long id;
    private SubClass subClass;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public SubClass getSubClass() {
        return subClass;
    }

    public void setSubClass(SubClass subClass) {
        this.subClass = subClass;
    }
}
