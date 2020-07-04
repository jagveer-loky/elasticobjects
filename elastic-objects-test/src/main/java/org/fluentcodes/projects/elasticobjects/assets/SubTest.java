package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//<call keep="JAVA" templateKey="BeanImport.tpl" }

//</call>

//<call keep="JAVA" templateKey="BeanHeader.tpl" }

/**
 * Created by Werner Diwischek on 09.10.2016.
 */
//</call>

public class SubTest {
    private static transient Logger LOG = LogManager.getLogger(SubTest.class);

    //<call keep="JAVA" templateKey="BeanInstanceVars.tpl" }

    private Long id;
    private SubTest subTest;
    private String name;
    private String testString;
//</call>

    //<call keep="JAVA" templateKey="BeanSetter.tpl" }

    /**
     * The id with a autonumbering
     */
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     */
    public SubTest getSubTest() {
        return this.subTest;
    }

    public void setSubTest(SubTest subTest) {
        this.subTest = subTest;
    }

    /**
     * The name field of a class.
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Just a small test string used in test models.
     */
    public String getTestString() {
        return this.testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }
    //</call>

}
