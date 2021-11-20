package org.fluentcodes.projects.elasticobjects.domain.test;

/*.{javaHeader}|*/
/**
 * 
 * A sub object as an example. 
 * @author Werner Diwischek
 * @creationDate Fri Nov 20 00:00:00 CET 2020
 * @modificationDate Tue Jan 19 12:14:01 CET 2021
 */
public class ASubObject  {
/*.{}.*/

    /*!!Problem invoke getter with 'javaStaticNames' and model 'ModelBeanGen':null!!*/
   public static final String ID = "id";
   public static final String MY_ASUB_OBJECT = "myASubObject";
   public static final String MY_STRING = "myString";
   public static final String NAME = "name";
   public static final String NATURAL_ID = "naturalId";
/*!!Could not create 'String' value from 'ASubObject(BEAN)' (ModelBeanGen)!!*/

    /*!!Problem invoke getter with 'javaInstanceVars' and model 'ModelBeanGen':null!!*/
   /* The id with a autonumbering */
   private Long id;
   /* myASubObject */
   private ASubObject myASubObject;
   /* Just a small test string used in test models.  */
   private String myString;
   /* The name field of a class.  */
   private String name;
   /* The natural key in @Base */
   private String naturalId;
/*!!Could not create 'String' value from 'ASubObject(BEAN)' (ModelBeanGen)!!*/

    /*!!Problem invoke getter with 'javaAccessors' and model 'ModelBeanGen':null!!*/
   public Long getId() {
      return this.id;
   }

   public ASubObject setId(final Long id) {
      this.id = id;
      return this;
    }

   public boolean hasId() {
      return this.id != null;
   }
   public ASubObject getMyASubObject() {
      return this.myASubObject;
   }

   public ASubObject setMyASubObject(final ASubObject myASubObject) {
      this.myASubObject = myASubObject;
      return this;
    }

   public boolean hasMyASubObject() {
      return this.myASubObject != null;
   }
   public String getMyString() {
      return this.myString;
   }

   public ASubObject setMyString(final String myString) {
      this.myString = myString;
      return this;
    }

   public boolean hasMyString() {
      return this.myString != null && this.myString.isEmpty();
   }
   public String getName() {
      return this.name;
   }

   public ASubObject setName(final String name) {
      this.name = name;
      return this;
    }

   public boolean hasName() {
      return this.name != null && this.name.isEmpty();
   }
   public String getNaturalId() {
      return this.naturalId;
   }

   public ASubObject setNaturalId(final String naturalId) {
      this.naturalId = naturalId;
      return this;
    }

   public boolean hasNaturalId() {
      return this.naturalId != null && this.naturalId.isEmpty();
   }
/*!!Could not create 'String' value from 'ASubObject(BEAN)' (ModelBeanGen)!!*/
}
