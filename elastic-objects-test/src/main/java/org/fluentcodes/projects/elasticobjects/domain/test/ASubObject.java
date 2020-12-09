package org.fluentcodes.projects.elasticobjects.domain.test;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/

/**
 * 
 *
 * @author Werner Diwischek
 * @creationDate Thu Nov 19 23:59:32 CET 2020
 * @modificationDate Wed Dec 09 19:00:56 CET 2020
 */
public class ASubObject  {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldBeans/*, super eq false, JAVA}|*/
   public static final String ID = "id";
   public static final String MY_ASUB_OBJECT = "myASubObject";
   public static final String MY_STRING = "myString";
   public static final String NAME = "name";
   public static final String NATURAL_ID = "naturalId";
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldBeans/*, super eq false, JAVA}|*/
   private Long id;
   private ASubObject myASubObject;
   private String myString;
   private String name;
   private String naturalId;
/*=>{}.*/

    /*==>{ALLSetter.tpl, fieldBeans/*, super eq false, JAVA}|*//* The id with a autonumbering*/
   public ASubObject setId(final Long id) {
      this.id = id;
      return this;
    }

   public Long getId() {
      return this.id;
   }

   public boolean hasId() {
      return this.id != null;
   }

/* myASubObject*/
   public ASubObject setMyASubObject(final ASubObject myASubObject) {
      this.myASubObject = myASubObject;
      return this;
    }

   public ASubObject getMyASubObject() {
      return this.myASubObject;
   }

   public boolean hasMyASubObject() {
      return this.myASubObject != null;
   }

/* Just a small test string used in test models. */
   public ASubObject setMyString(final String myString) {
      this.myString = myString;
      return this;
    }

   public String getMyString() {
      return this.myString;
   }

   public boolean hasMyString() {
      return this.myString != null && this.myString.isEmpty();
   }

/* The name field of a class. */
   public ASubObject setName(final String name) {
      this.name = name;
      return this;
    }

   public String getName() {
      return this.name;
   }

   public boolean hasName() {
      return this.name != null && this.name.isEmpty();
   }

/* The natural key in @Base*/
   public ASubObject setNaturalId(final String naturalId) {
      this.naturalId = naturalId;
      return this;
    }

   public String getNaturalId() {
      return this.naturalId;
   }

   public boolean hasNaturalId() {
      return this.naturalId != null && this.naturalId.isEmpty();
   }


/*=>{}.*/
}
