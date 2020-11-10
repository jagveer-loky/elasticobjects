package org.fluentcodes.projects.elasticobjects.domain.test;
/*==>{ALLImport.tpl, javaGenImport/*, JAVA}|*/
/*=>{}.*/

/**
 * A sub object as an example.
 * Created by Werner Diwischek on 29.9.2020.
 */
public class ASubObject   {

    /*==>{ALLStaticNames.tpl, fieldMap/*, JAVA, override eq false}|*/
    public static final String ID = "id";
    public static final String MY_ASUB_OBJECT = "myASubObject";
    public static final String MY_STRING = "myString";
    public static final String NAME = "name";
    public static final String NATURAL_ID = "naturalId";
    /*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, JAVA}|*/
    private  Long id;
    private  ASubObject myASubObject;
    private  String myString;
    private  String name;
    private  String naturalId;
    /*=>{}.*/

    /*==>{ALLSetter.tpl, fieldMap/*, JAVA}|*/

    /**
     The id with a autonumbering
     */
    public ASubObject setId(Long id) {
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
    public ASubObject setMyASubObject(ASubObject myASubObject) {
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
     Just a small test string used in test models.
     */
    public ASubObject setMyString(String myString) {
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
     The name field of a class.
     */
    public ASubObject setName(String name) {
        this.name = name;
        return this;
    }

    public String getName () {
        return this.name;
    }

    public boolean hasName () {
        return name!= null && !name.isEmpty();
    }

    /**
     The naturalKey for all caches {@link ConfigImpl} and {@link BeanImpl}
     */
    public ASubObject setNaturalId(String naturalId) {
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
