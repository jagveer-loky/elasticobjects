package org.fluentcodes.projects.elasticobjects.domain.test;

/*=>{javaHeader}|*/
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 Model class with different types of fields including generic collections, maps and {@link ASubObject} objects. *
 * @author Werner Diwischek
 * @creationDate null
 * @modificationDate Thu Jan 07 05:19:42 CET 2021
 */
public class AnObject  {/*=>{}.*/

    /*=>{javaStaticNames}|*/
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

    /*=>{javaInstanceVars}|*/
   /* The id with a autonumbering */
   private Long id;
   /* myASubObject */
   private ASubObject myASubObject;
   /* A generic parametrized example List with @ASubObject */
   private List<ASubObject> myASubObjectList;
   /* A generic parametrized example Map with @ASubObject */
   private Map<String, ASubObject> myASubObjectMap;
   /* A AnObject field. */
   private AnObject myAnObject;
   /* myBoolean */
   private Boolean myBoolean;
   /* myDate */
   private Date myDate;
   /* myDouble */
   private Double myDouble;
   /* myFloat */
   private Float myFloat;
   /* myInt */
   private Integer myInt;
   /* myList */
   private ArrayList myList;
   /* myLong */
   private Long myLong;
   /* myMap */
   private HashMap myMap;
   /* A test object thing.  */
   private Object myObject;
   /* Just a small test string used in test models.  */
   private String myString;
   /* The natural key in @Base */
   private String naturalId;
/*=>{}.*/

    public Boolean isMyBoolean () {
        return this.myBoolean;
    }

    /*=>{javaAccessors}|*/
   public Long getId() {
      return this.id;
   }

   public AnObject setId(final Long id) {
      this.id = id;
      return this;
    }

   public boolean hasId() {
      return this.id != null;
   }
   public ASubObject getMyASubObject() {
      return this.myASubObject;
   }

   public AnObject setMyASubObject(final ASubObject myASubObject) {
      this.myASubObject = myASubObject;
      return this;
    }

   public boolean hasMyASubObject() {
      return this.myASubObject != null;
   }
   public List<ASubObject> getMyASubObjectList() {
      return this.myASubObjectList;
   }

   public AnObject setMyASubObjectList(final List<ASubObject> myASubObjectList) {
      this.myASubObjectList = myASubObjectList;
      return this;
    }

   public boolean hasMyASubObjectList() {
      return this.myASubObjectList != null && this.myASubObjectList.isEmpty();
   }
   public Map<String, ASubObject> getMyASubObjectMap() {
      return this.myASubObjectMap;
   }

   public AnObject setMyASubObjectMap(final Map<String, ASubObject> myASubObjectMap) {
      this.myASubObjectMap = myASubObjectMap;
      return this;
    }

   public boolean hasMyASubObjectMap() {
      return this.myASubObjectMap != null && this.myASubObjectMap.isEmpty();
   }
   public AnObject getMyAnObject() {
      return this.myAnObject;
   }

   public AnObject setMyAnObject(final AnObject myAnObject) {
      this.myAnObject = myAnObject;
      return this;
    }

   public boolean hasMyAnObject() {
      return this.myAnObject != null;
   }
   public Boolean getMyBoolean() {
      return this.myBoolean;
   }

   public AnObject setMyBoolean(final Boolean myBoolean) {
      this.myBoolean = myBoolean;
      return this;
    }

   public boolean hasMyBoolean() {
      return this.myBoolean != null;
   }
   public Date getMyDate() {
      return this.myDate;
   }

   public AnObject setMyDate(final Date myDate) {
      this.myDate = myDate;
      return this;
    }

   public boolean hasMyDate() {
      return this.myDate != null;
   }
   public Double getMyDouble() {
      return this.myDouble;
   }

   public AnObject setMyDouble(final Double myDouble) {
      this.myDouble = myDouble;
      return this;
    }

   public boolean hasMyDouble() {
      return this.myDouble != null;
   }
   public Float getMyFloat() {
      return this.myFloat;
   }

   public AnObject setMyFloat(final Float myFloat) {
      this.myFloat = myFloat;
      return this;
    }

   public boolean hasMyFloat() {
      return this.myFloat != null;
   }
   public Integer getMyInt() {
      return this.myInt;
   }

   public AnObject setMyInt(final Integer myInt) {
      this.myInt = myInt;
      return this;
    }

   public boolean hasMyInt() {
      return this.myInt != null;
   }
   public ArrayList getMyList() {
      return this.myList;
   }

   public AnObject setMyList(final ArrayList myList) {
      this.myList = myList;
      return this;
    }

   public boolean hasMyList() {
      return this.myList != null && this.myList.isEmpty();
   }
   public Long getMyLong() {
      return this.myLong;
   }

   public AnObject setMyLong(final Long myLong) {
      this.myLong = myLong;
      return this;
    }

   public boolean hasMyLong() {
      return this.myLong != null;
   }
   public HashMap getMyMap() {
      return this.myMap;
   }

   public AnObject setMyMap(final HashMap myMap) {
      this.myMap = myMap;
      return this;
    }

   public boolean hasMyMap() {
      return this.myMap != null && this.myMap.isEmpty();
   }
   public Object getMyObject() {
      return this.myObject;
   }

   public AnObject setMyObject(final Object myObject) {
      this.myObject = myObject;
      return this;
    }

   public boolean hasMyObject() {
      return this.myObject != null;
   }
   public String getMyString() {
      return this.myString;
   }

   public AnObject setMyString(final String myString) {
      this.myString = myString;
      return this;
    }

   public boolean hasMyString() {
      return this.myString != null && this.myString.isEmpty();
   }
   public String getNaturalId() {
      return this.naturalId;
   }

   public AnObject setNaturalId(final String naturalId) {
      this.naturalId = naturalId;
      return this;
    }

   public boolean hasNaturalId() {
      return this.naturalId != null && this.naturalId.isEmpty();
   }
/*=>{}.*/
}
