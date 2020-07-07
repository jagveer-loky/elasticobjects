package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Created by Werner Diwischek on 09.10.2016.
 */

public class ClassTest {
    private Long id;
    private SubClassForTest subClass;
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public SubClassForTest getSubClass() {
        return subClass;
    }

    public void setSubClass(SubClassForTest subClass) {
        this.subClass = subClass;
    }
}
