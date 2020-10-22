package org.fluentcodes.projects.elasticobjects.domain.test;
//  ===>{"(TemplateResourceCall)javaGenImport/* configKey="ALLImport.tpl" keepCall="JAVA" }|
//=>{}.

/**
 * A sub object as an example.
 * Created by Werner Diwischek on 29.9.2020.
 */
public class ASubObject   {

//  ===>{"(TemplateResourceCall).":{"sourcePath":javaGenFieldKeysStripped/*","configKey":"ALLStaticNames.tpl","keepCall":"JAVA"}|
//=>{}.

    //  ===>{"(TemplateResourceCall).":{"sourcePath":"javaGenFieldKeysStripped/*", "configKey", "ALLInstanceVars.tpl", "keepCall":"JAVA"}}|
    private  Long id;
    private  ASubObject myASubObject;
    private  String name;
    private  String myString;
//=>{}.

//  ===>{"(TemplateResourceCall).":{"sourcePath":"javaGenFieldKeysStripped/*", "configKey":"ALLSetter.tpl", "keepCall":"JAVA"}}|
    /**
     The id with a autonumbering
     */


    public final ASubObject setId(Long id) {
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
     myASubObject
     */


    public final ASubObject setMyASubObject(ASubObject myASubObject) {
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
     The name field of a class.
     */


    public final ASubObject setName(String name) {
        this.name = name;
        return this;
    }

    public final String getName () {
        return this.name;
    }

    public boolean hasName () {
        return name != null  && !name.isEmpty();
    }

    /**
     Just a small test string used in test models.
     */


    public final ASubObject setMyString(String myString) {
        this.myString = myString;
        return this;
    }

    public final String getMyString () {
        return this.myString;
    }

    public boolean hasMyString () {
        return myString != null  && !myString.isEmpty();
    }

//=>{}.
}
