package org.fluentcodes.projects.elasticobjects.assets;

/**
 * Created by Werner Diwischek on 09.10.2016.
 */

public class ASubObject {
    private Long id;
    private ASubObject subTest;
    private String name;
    private String myString;
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
    public ASubObject getSubTest() {
        return this.subTest;
    }

    public void setSubTest(ASubObject subTest) {
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
    public String getMyString() {
        return this.myString;
    }

    public void setMyString(String myString) {
        this.myString = myString;
    }
    //</call>

}
