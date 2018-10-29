package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

//<call keep="JAVA" templateKey="BeanImport.tpl" }

//</call>

//<call keep="JAVA" templateKey="BeanHeader.tpl" }

/**
 * Model class with different types of fields including generic collections, maps and {@link SubTest} objects.
 * Created by Werner Diwischek on 09.10.2016.
 */
//</call>

public class BasicTest {
    public static final int SIZE = 15;
    private static transient Logger LOG = LogManager.getLogger(BasicTest.class);

    //<call keep="JAVA" templateKey="BeanInstanceVars.tpl" }
    private Long id;
    private BasicTest basicTest;
    private SubTest subTest;
    private ArrayList<SubTest> subTestList;
    private HashMap<String, SubTest> subTestMap;
    private ArrayList untypedList;
    private HashMap untypedMap;
    private Integer testInt;
    private String testString;
    private Long testLong;
    private Date testDate;
    private Boolean testBoolean;
    private Float testFloat;
    private Double testDouble;
    private Object testObject;
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
    public BasicTest getBasicTest() {
        return this.basicTest;
    }

    public void setBasicTest(BasicTest basicTest) {
        this.basicTest = basicTest;
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
     * A generic parametrized example List with {@link SubTest}
     */
    public ArrayList<SubTest> getSubTestList() {
        return this.subTestList;
    }

    public void setSubTestList(ArrayList<SubTest> subTestList) {
        this.subTestList = subTestList;
    }

    /**
     * A generic parametrized example Map with {@link SubTest}
     */
    public HashMap<String, SubTest> getSubTestMap() {
        return this.subTestMap;
    }

    public void setSubTestMap(HashMap<String, SubTest> subTestMap) {
        this.subTestMap = subTestMap;
    }

    /**
     *
     */
    public ArrayList getUntypedList() {
        return this.untypedList;
    }

    public void setUntypedList(ArrayList untypedList) {
        this.untypedList = untypedList;
    }

    /**
     *
     */
    public HashMap getUntypedMap() {
        return this.untypedMap;
    }

    public void setUntypedMap(HashMap untypedMap) {
        this.untypedMap = untypedMap;
    }

    /**
     *
     */
    public Integer getTestInt() {
        return this.testInt;
    }

    public void setTestInt(Integer testInt) {
        this.testInt = testInt;
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

    /**
     *
     */
    public Long getTestLong() {
        return this.testLong;
    }

    public void setTestLong(Long testLong) {
        this.testLong = testLong;
    }

    /**
     *
     */
    public Date getTestDate() {
        return this.testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    /**
     *
     */
    public Boolean isTestBoolean() {
        return this.testBoolean;
    }

    public void setTestBoolean(Boolean testBoolean) {
        this.testBoolean = testBoolean;
    }

    /**
     *
     */
    public Float getTestFloat() {
        return this.testFloat;
    }

    public void setTestFloat(Float testFloat) {
        this.testFloat = testFloat;
    }

    /**
     *
     */
    public Double getTestDouble() {
        return this.testDouble;
    }

    public void setTestDouble(Double testDouble) {
        this.testDouble = testDouble;
    }

    /**
     * A test object thing.
     */
    public Object getTestObject() {
        return this.testObject;
    }

    public void setTestObject(Object testObject) {
        this.testObject = testObject;
    }
    //</call>

}
