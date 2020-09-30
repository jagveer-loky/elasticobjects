package org.fluentcodes.projects.elasticobjects.assets;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Model class with different types of fields including generic collections, maps and {@link ASubObject} objects.
 * Created by Werner Diwischek on 09.10.2016.
 */

public class AnObject {
    public static final String MY_STRING = "myString";
    public static final String MY_DATE = "myDate";
    public static final String MY_BOOLEAN = "myBoolean";
    public static final String MY_INTEGER = "myInt";
    public static final String MY_DOUBLE = "myDouble";
    public static final String MY_FLOAT = "myFloat";
    public static final String MY_LONG = "myLong";
    public static final String MY_OBJECT = "myObject";
    public static final String MY_LIST = "myList";
    public static final String MY_MAP = "myMap";
    public static final String MY_A_SUB_OBJECT_MAP = "myASubObjectMap";
    public static final String MY_A_SUB_OBJECT_LIST = "myASubObjectList";
    public static final String MY_A_SUB_OBJECT = "myASubObject";

    private Long id;
    private AnObject myAnObject;
    private ASubObject myASubObject;
    private ArrayList<ASubObject> myASubObjectList;
    private HashMap<String, ASubObject> myASubObjectMap;
    private ArrayList myList;
    private HashMap myMap;
    private Integer myInt;
    private String myString;
    private Long myLong;
    private Date myDate;
    private Boolean myBoolean;
    private Float myFloat;
    private Double myDouble;
    private Object myObject;

    /**
     * The id with a autonumbering
     */
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public AnObject getMyAnObject() {
        return this.myAnObject;
    }

    public void setMyAnObject(AnObject myAnObject) {
        this.myAnObject = myAnObject;
    }


    public ASubObject getMyASubObject() {
        return this.myASubObject;
    }

    public void setMyASubObject(ASubObject myASubObject) {
        this.myASubObject = myASubObject;
    }

    /**
     * A generic parametrized example List with {@link ASubObject}
     */
    public ArrayList<ASubObject> getMyASubObjectList() {
        return this.myASubObjectList;
    }

    public void setMyASubObjectList(ArrayList<ASubObject> myASubObjectList) {
        this.myASubObjectList = myASubObjectList;
    }

    /**
     * A generic parametrized example Map with {@link ASubObject}
     */
    public HashMap<String, ASubObject> getMyASubObjectMap() {
        return this.myASubObjectMap;
    }

    public void setMyASubObjectMap(HashMap<String, ASubObject> myASubObjectMap) {
        this.myASubObjectMap = myASubObjectMap;
    }


    public ArrayList getMyList() {
        return this.myList;
    }

    public void setMyList(ArrayList myList) {
        this.myList = myList;
    }


    public HashMap getMyMap() {
        return this.myMap;
    }

    public void setMyMap(HashMap myMap) {
        this.myMap = myMap;
    }


    public Integer getMyInt() {
        return this.myInt;
    }

    public void setMyInt(Integer myInt) {
        this.myInt = myInt;
    }

    /**
     * Just a small test string used in test models.
     */
    public String getMyString() {
        return this.myString;
    }

    public AnObject setMyString(String myString) {
        this.myString = myString;
        return this;
    }


    public Long getMyLong() {
        return this.myLong;
    }

    public void setMyLong(Long myLong) {
        this.myLong = myLong;
    }


    public Date getMyDate() {
        return this.myDate;
    }

    public void setMyDate(Date myDate) {
        this.myDate = myDate;
    }


    public Boolean isMyBoolean() {
        return this.myBoolean;
    }

    public void setMyBoolean(Boolean myBoolean) {
        this.myBoolean = myBoolean;
    }


    public Float getMyFloat() {
        return this.myFloat;
    }

    public void setMyFloat(Float myFloat) {
        this.myFloat = myFloat;
    }


    public Double getMyDouble() {
        return this.myDouble;
    }

    public void setMyDouble(Double myDouble) {
        this.myDouble = myDouble;
    }

    /**
     * A test object thing.
     */
    public Object getMyObject() {
        return this.myObject;
    }

    public void setMyObject(Object myObject) {
        this.myObject = myObject;
    }
    //</call>

}
