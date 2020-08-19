package org.fluentcodes.projects.elasticobjects.assets.byclass;

/**
 * Created by Werner Diwischek on 09.10.2016.
 */

public class SuperExampleClass {
    private Long id;
    private FieldExampleClass subClass;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public FieldExampleClass getSubClass() {
        return subClass;
    }

    public void setSubClass(FieldExampleClass subClass) {
        this.subClass = subClass;
    }
}
