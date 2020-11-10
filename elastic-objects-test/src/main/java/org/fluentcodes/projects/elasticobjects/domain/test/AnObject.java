package org.fluentcodes.projects.elasticobjects.domain.test;

/*==>{ALLImport.tpl, javaGenImport/*, JAVA}|*/
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
/*=>{}.*/

/**
 * Model class with different types of fields including generic collections, maps and {@link ASubObject} objects.
 * Created by Werner Diwischek on 29.9.2020.
 */

public class AnObject   {
    /*==>{ALLStaticNames.tpl, fieldMap/*, JAVA, override eq false}|*/
    public static final String ID = "id";
    public static final String MY_ASUB_OBJECT = "myASubObject";
    public static final String MY_ASUB_OBJECT_LIST = "myASubObjectList";
    public static final String MY_ASUB_OBJECT_MAP = "myASubObjectMap";
    public static final String MY_AN_OBJECT = "myAnObject";
    public static final String MY_BOOLEAN = "myBoolean";
    public static final String MY_DATE = "myDate";
    public static final String MY_DOUBLE = "myDouble";
    public static final String MY_FLOAT = "myFloat";
    public static final String MY_INT = "myInt";
    public static final String MY_LIST = "myList";
    public static final String MY_LONG = "myLong";
    public static final String MY_MAP = "myMap";
    public static final String MY_OBJECT = "myObject";
    public static final String MY_STRING = "myString";
    public static final String NATURAL_ID = "naturalId";
    /*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, JAVA}|*/
    private  Long id;
    private  ASubObject myASubObject;
    private  List<ASubObject> myASubObjectList;
    private  Map<String, ASubObject> myASubObjectMap;
    private  AnObject myAnObject;
    private  Boolean myBoolean;
    private  Date myDate;
    private  Double myDouble;
    private  Float myFloat;
    private  Integer myInt;
    private  ArrayList myList;
    private  Long myLong;
    private  HashMap myMap;
    private  Object myObject;
    private  String myString;
    private  String naturalId;
    /*=>{}.*/

    public Boolean isMyBoolean () {
        return this.myBoolean;
    }

    /*==>{ALLSetter.tpl, fieldMap/*, JAVA}|*/

    /**
     The id with a autonumbering
     */
    public AnObject setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getId () {
        return this.id;
    }

    public boolean hasId () {
        return id!= null;
    }

    /**
     myASubObject
     */
    public AnObject setMyASubObject(ASubObject myASubObject) {
        this.myASubObject = myASubObject;
        return this;
    }

    public ASubObject getMyASubObject () {
        return this.myASubObject;
    }

    public boolean hasMyASubObject () {
        return myASubObject!= null;
    }

    /**
     A generic parametrized example List with {@link ASubObject}
     */
    public AnObject setMyASubObjectList(List<ASubObject> myASubObjectList) {
        this.myASubObjectList = myASubObjectList;
        return this;
    }

    public List<ASubObject> getMyASubObjectList () {
        return this.myASubObjectList;
    }

    public boolean hasMyASubObjectList () {
        return myASubObjectList!= null && !myASubObjectList.isEmpty();
    }

    /**
     A generic parametrized example Map with {@link ASubObject}
     */
    public AnObject setMyASubObjectMap(Map<String, ASubObject> myASubObjectMap) {
        this.myASubObjectMap = myASubObjectMap;
        return this;
    }

    public Map<String, ASubObject> getMyASubObjectMap () {
        return this.myASubObjectMap;
    }

    public boolean hasMyASubObjectMap () {
        return myASubObjectMap!= null && !myASubObjectMap.isEmpty();
    }

    /**
     A AnObject field.
     */
    public AnObject setMyAnObject(AnObject myAnObject) {
        this.myAnObject = myAnObject;
        return this;
    }

    public AnObject getMyAnObject () {
        return this.myAnObject;
    }

    public boolean hasMyAnObject () {
        return myAnObject!= null;
    }

    /**
     myBoolean
     */
    public AnObject setMyBoolean(Boolean myBoolean) {
        this.myBoolean = myBoolean;
        return this;
    }

    public Boolean getMyBoolean () {
        return this.myBoolean;
    }

    public boolean hasMyBoolean () {
        return myBoolean!= null;
    }

    /**
     myDate
     */
    public AnObject setMyDate(Date myDate) {
        this.myDate = myDate;
        return this;
    }

    public Date getMyDate () {
        return this.myDate;
    }

    public boolean hasMyDate () {
        return myDate!= null;
    }

    /**
     myDouble
     */
    public AnObject setMyDouble(Double myDouble) {
        this.myDouble = myDouble;
        return this;
    }

    public Double getMyDouble () {
        return this.myDouble;
    }

    public boolean hasMyDouble () {
        return myDouble!= null;
    }

    /**
     myFloat
     */
    public AnObject setMyFloat(Float myFloat) {
        this.myFloat = myFloat;
        return this;
    }

    public Float getMyFloat () {
        return this.myFloat;
    }

    public boolean hasMyFloat () {
        return myFloat!= null;
    }

    /**
     myInt
     */
    public AnObject setMyInt(Integer myInt) {
        this.myInt = myInt;
        return this;
    }

    public Integer getMyInt () {
        return this.myInt;
    }

    public boolean hasMyInt () {
        return myInt!= null;
    }

    /**
     myList
     */
    public AnObject setMyList(ArrayList myList) {
        this.myList = myList;
        return this;
    }

    public ArrayList getMyList () {
        return this.myList;
    }

    public boolean hasMyList () {
        return myList!= null && !myList.isEmpty();
    }

    /**
     myLong
     */
    public AnObject setMyLong(Long myLong) {
        this.myLong = myLong;
        return this;
    }

    public Long getMyLong () {
        return this.myLong;
    }

    public boolean hasMyLong () {
        return myLong!= null;
    }

    /**
     myMap
     */
    public AnObject setMyMap(HashMap myMap) {
        this.myMap = myMap;
        return this;
    }

    public HashMap getMyMap () {
        return this.myMap;
    }

    public boolean hasMyMap () {
        return myMap!= null && !myMap.isEmpty();
    }

    /**
     A test object thing.
     */
    public AnObject setMyObject(Object myObject) {
        this.myObject = myObject;
        return this;
    }

    public Object getMyObject () {
        return this.myObject;
    }

    public boolean hasMyObject () {
        return myObject!= null;
    }

    /**
     Just a small test string used in test models.
     */
    public AnObject setMyString(String myString) {
        this.myString = myString;
        return this;
    }

    public String getMyString () {
        return this.myString;
    }

    public boolean hasMyString () {
        return myString!= null && !myString.isEmpty();
    }

    /**
     The naturalKey for all caches {@link ConfigImpl} and {@link BeanImpl}
     */
    public AnObject setNaturalId(String naturalId) {
        this.naturalId = naturalId;
        return this;
    }

    public String getNaturalId () {
        return this.naturalId;
    }

    public boolean hasNaturalId () {
        return naturalId!= null && !naturalId.isEmpty();
    }
    /*=>{}.*/
}
