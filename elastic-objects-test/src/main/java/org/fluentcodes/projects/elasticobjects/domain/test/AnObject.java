package org.fluentcodes.projects.elasticobjects.domain.test;

//
//
//
// ===>{"(TemplateResourceCall).":{"sourcePath":"javaImport/*", "configKey":"ALLImport.java", "keepCall":"JAVA" }}|
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
//=>{}.

/**
 * Model class with different types of fields including generic collections, maps and {@link ASubObject} objects.
 * Created by Werner Diwischek on 29.9.2020.
 */

public class AnObject   {

//
//
//
// ===>{"(TemplateResourceCall).":{"sourcePath":"fieldKeys/*", "configKey":"BEANStaticNames.java","keepCall":"JAVA"}|
   public static final String ID = "id"; 
   public static final String MY_AN_OBJECT = "myAnObject"; 
   public static final String MY_ASUB_OBJECT = "myASubObject"; 
   public static final String MY_ASUB_OBJECT_LIST = "myASubObjectList"; 
   public static final String MY_ASUB_OBJECT_MAP = "myASubObjectMap"; 
   public static final String MY_LIST = "myList"; 
   public static final String MY_MAP = "myMap"; 
   public static final String MY_INT = "myInt"; 
   public static final String MY_STRING = "myString"; 
   public static final String MY_LONG = "myLong"; 
   public static final String MY_DATE = "myDate"; 
   public static final String MY_BOOLEAN = "myBoolean"; 
   public static final String MY_FLOAT = "myFloat"; 
   public static final String MY_DOUBLE = "myDouble"; 
   public static final String MY_OBJECT = "myObject"; 
//=>{}.

//
//
//
// ===>{"(TemplateResourceCall).":{"sourcePath":"fieldKeys/*", "configKey":"BEANInstanceVars.tpl", "keepCall":"JAVA"}}|
   private Long id;
   private String naturalId;
   private AnObject myAnObject; 
   private ASubObject myASubObject; 
   private List<ASubObject> myASubObjectList; 
   private Map<String, ASubObject> myASubObjectMap; 
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
//=>{}.

    public final Boolean isMyBoolean () {
        return this.myBoolean;
    }
// ===>{"(TemplateResourceCall).":{"sourcePath":"fieldKeys/*", "configKey":"BEANSetter.tpl", "keepCall":"JAVA"}|


    public String getNaturalId() {
        return naturalId;
    }

    public void setNaturalId(String naturalId) {
        this.naturalId = naturalId;
    }

    /**
    The id with a autonumbering
    */
    public final AnObject setId(Long id) {
        this.id = id;
        return this;
    }
    public final Long getId () {
            return this.id;
    }
    public boolean hasId () {
        return id != null ;
    }

    /**
    A AnObject field.
    */
    public final AnObject setMyAnObject(AnObject myAnObject) {
        this.myAnObject = myAnObject;
        return this;
    }
    public final AnObject getMyAnObject () {
            return this.myAnObject;
    }
    public boolean hasMyAnObject () {
        return myAnObject != null ;
    }

    /**
    myASubObject
    */
    public final AnObject setMyASubObject(ASubObject myASubObject) {
        this.myASubObject = myASubObject;
        return this;
    }
    public final ASubObject getMyASubObject () {
            return this.myASubObject;
    }
    public boolean hasMyASubObject () {
        return myASubObject != null ;
    }

    /**
    A generic parametrized example List with {@link ASubObject}
    */
    public final AnObject setMyASubObjectList(List<ASubObject> myASubObjectList) {
        this.myASubObjectList = myASubObjectList;
        return this;
    }
    public final List<ASubObject> getMyASubObjectList () {
            return this.myASubObjectList;
    }
    public boolean hasMyASubObjectList () {
        return myASubObjectList != null  && !myASubObjectList.isEmpty();
    }

    /**
    A generic parametrized example Map with {@link ASubObject}
    */
    public final AnObject setMyASubObjectMap(Map<String, ASubObject> myASubObjectMap) {
        this.myASubObjectMap = myASubObjectMap;
        return this;
    }
    public final Map<String, ASubObject> getMyASubObjectMap () {
            return this.myASubObjectMap;
    }
    public boolean hasMyASubObjectMap () {
            return myASubObjectMap != null  && !myASubObjectMap.isEmpty();
    }

    /**
    myList
    */
    public final AnObject setMyList(ArrayList myList) {
        this.myList = myList;
        return this;
    }
    public final ArrayList getMyList () {
            return this.myList;
    }
    public boolean hasMyList () {
        return myList != null  && !myList.isEmpty();
    }

    /**
    myMap
    */
    public final AnObject setMyMap(HashMap myMap) {
        this.myMap = myMap;
        return this;
    }
    public final HashMap getMyMap () {
            return this.myMap;
    }
    public boolean hasMyMap () {
        return myMap != null  && !myMap.isEmpty();
    }

    /**
    myInt
    */
    public final AnObject setMyInt(Integer myInt) {
        this.myInt = myInt;
        return this;
    }
    public final Integer getMyInt () {
            return this.myInt;
    }
    public boolean hasMyInt () {
        return myInt != null ;
    }

    /**
    Just a small test string used in test models. 
    */
    public final AnObject setMyString(String myString) {
        this.myString = myString;
        return this;
    }
    public final String getMyString () {
            return this.myString;
    }
    public boolean hasMyString () {
        return myString != null  && !myString.isEmpty();
    }

    /**
    myLong
    */
    public final AnObject setMyLong(Long myLong) {
        this.myLong = myLong;
        return this;
    }
    public final Long getMyLong () {
            return this.myLong;
    }
    public boolean hasMyLong () {
        return myLong != null ;
    }

    /**
    myDate
    */
    public final AnObject setMyDate(Date myDate) {
        this.myDate = myDate;
        return this;
    }
    public final Date getMyDate () {
            return this.myDate;
    }
    public boolean hasMyDate () {
        return myDate != null ;
    }

    /**
    myBoolean
    */
    public final AnObject setMyBoolean(Boolean myBoolean) {
        this.myBoolean = myBoolean;
        return this;
    }
    public final Boolean getMyBoolean () {
            return this.myBoolean;
    }
    public boolean hasMyBoolean () {
        return myBoolean != null ;
    }

    /**
    myFloat
    */
    public final AnObject setMyFloat(Float myFloat) {
        this.myFloat = myFloat;
        return this;
    }
    public final Float getMyFloat () {
            return this.myFloat;
    }
    public boolean hasMyFloat () {
        return myFloat != null ;
    }

    /**
    myDouble
    */
    public final AnObject setMyDouble(Double myDouble) {
        this.myDouble = myDouble;
        return this;
    }
    public final Double getMyDouble () {
            return this.myDouble;
    }
    public boolean hasMyDouble () {
        return myDouble != null ;
    }

    /**
    A test object thing. 
    */
    public final AnObject setMyObject(Object myObject) {
        this.myObject = myObject;
        return this;
    }
    public final Object getMyObject () {
            return this.myObject;
    }
    public boolean hasMyObject () {
        return myObject != null ;
    }

//=>{}.

}
