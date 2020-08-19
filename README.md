
<div align="right" clear="left">
<a name="page"/>
<font size="2">
<a href="#path">Path</a><br>
<a href="#typed-json">Typed JSON</a><br>
<a href="#under-the-hood">Under the Hood</a><br>
<a href="#further-documentation">Further-Documentation</a><br>
<a href="#packages">Packages</a><br>
<a href="#status">Status</a><br>
</font>
</div>

# Elastic Objects

Elastic Objects is a small java application framework for handling (compound) objects via [path](#path).

It could read or write typed [JSON](#typed-json) for string representation which offers
* [embedded type directives](#typed) for looseless data exchange without webservers or REST.
* [unmapped fields](#unmapped) for integration of extra information in JSON like comments.
* special types for the execution of [Call Beans](#calls).

<div align="right" style="font-size:10px"><a href="#page"><font size="2">top</font></a></div>

#### Path
 [EO](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/EO.java) allows creating, accessing and modifing complex Java objects via path. Non existing elements will be created automatically. 

    EO child = eo.set("value","level0/level1/level2/level3");
    assertThat(child.get()).isEqualTo("value");
    assertThat(eo.get("level0/level1/level2/level3")).isEqualTo("value");
<div align="right" style="font-size:10px">
    <a href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/EoSetScalarTest.java">
<font size="1">example</font>
</a></div>

One can integrate typed objects in a complex structure and access it without loosing the type.

    BasicTest bt = new BasicTest()
       .setTestString("value");
    eo.set(bt, "level0");
    assertThat(eo.get("level0/testString")).isEqualTo("value");
    assertThat(eo.getEo("level0").getModelClass()).isEqualTo(BasicTest.class);
<div align="right" style="font-size:10px">
<a href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/assets/EoMapSetBtTest.java">
<font size="1">example</font>
</a></div>

##### Object Conversion

Objects will be automatically mapped to the existing model class. This allows easy merge and conversion of objects with same field names.

In the following example a Map value will be set by [BasicTest](https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/main/java/org/fluentcodes/projects/elasticobjects/assets/BasicTest.java) object:

    final EO eo = ProviderRootTestScope.createEo(Map.class);
    final BasicTest bt = new BasicTest()
       .setTestString("value");
    eo.mapObject(bt);
    assertThat(((Map)eo.get())get()"testString")).isEqualTo("value");
    assertThat(eo.getModelClass()).isEqualTo(Map.class);

<div align="right" style="font-size:10px">
<font size="1">
<a href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/assets/EoMapObjectBtTest.java">example</a>
</font>
</div>

The last example the other way round setting a [BasicTest](https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/main/java/org/fluentcodes/projects/elasticobjects/assets/BasicTest.java) value with a Map object:

    final EO eo = ProviderRootTestScope.createEo(BasicTest.class);
    final Map map = new HashMap()
    map.put("testString", "value");
    eo.mapObject(map);
    assertThat(((BasicTest)eo.get()).getTestString()).isEqualTo("value");
    assertThat(eo.getModelClass()).isEqualTo(BasicTest.class);

<div align="right" style="font-size:10px">
<a href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/assets/EoMapObjectBtTest.java">
<font size="1">example</font>
</a></div>

Elastic objects without JSON serialization could be used as a tool in a java native solution.

<div align="right" style="font-size:10px"><a href="#page"><font size="2">top</font></a></div>

#### Typed JSON
The serialization/deserialization implementation extends some limitations of standard json.

It allows
* [embed type](#typed) directives in the name allows typesafe transfer
* [embed information](#unmapped) with names starting with "_" (e.g _comment)
* [embed calling functionality](#calls) with special call classes implementing an execute method.

##### Untyped
Standard JSON will be interpreted to standard untyped objects like map or list.
```
{
	"level0":{
		"testString":"value"
    }
}
```

        eo.mapObject(jsonString);
        assertThat(eo.get("level0/testString")).isEqualTo("value");
        assertThat(eo.getEo("level0").getModelClass()).isEqualTo(Map.class);

 <div align="right" style="font-size:10px">
<a href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/EoMapObjectMapJsonTest.java">
<font size="1">example</font>
</a></div>

##### Typed
The type directive is embedded in the name in java-style: (Type)name
```
{
	"(BasicTest)level0":{
	    "testString":"value"
    }
}
```
    
        eo.mapObject(jsonString);
        assertThat(eo.get("level0/testString")).isEqualTo("value");
        assertThat(((BasicTest)eo.get("level0")).getTestString()).isEqualTo("value");
        assertThat(eo.getEo("level0").getModelClass()).isEqualTo(BasicTest.class);

<div align="right" style="font-size:10px">
<a href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/assets/EoMapObjectTest.java">
<font size="1">example</font>
</a></div>

##### Unmapped
All fieldnames starting with _ will not be mapped to the underlying object:
```
{
	"(BasicTest)level0":{  
	    "testString":"value",  
	    "_comment":"_comment is not a field of the BasicTest.class"
    }
}
```


        eo.mapObject(jsonString);
        assertThat(eo.get("level0/_comment")).isEqualTo("_comment is not a field of the BasicTest.class");
        assertThat(((BasicTest)eo.get("level0")).getTestString()).isEqualTo("value");


<div align="right" style="font-size:10px">
<a href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/assets/EoMapObjectTest.java">
<font size="1">example</font>
</a></div>

##### Calls
For very type implementing [Call](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/Call.java) this could be executed.

Here JSON includes [SinusValueCall](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/values/SinusValueCall.java)  
with SourcePath "source". It will compute the sinus from source value 1 and set it to target.

```
{
  "(Double)source":1,
  "(SinusValueCall)target": {
    "sourcePath": "/source"
  }
}
```

        eo.mapObject(jsonString);
        eo.execute();
        assertThat(eo.get("target")).isEqualTo(0.8414709848078965);

 <div align="right" style="font-size:10px">
<a href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/calls/values/SinusValueCallTest.java">
<font size="1">example</font>
</a></div>

The used [SinusValueCall](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/values/SinusValueCall.java)
is minimal and no restrictions for execution need to be made, since only a value is set to object.

Other examples for these simple calls you can find under the [calls/values](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/values/) package.
A special call for setting configuration values is under [calls/configs](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/configs/).


##### Configured Calls
When it comes to read or write something on the server, it's more complex concerning access permissions and configurations.  
The following configured calls are implemented:

* [File Access](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/files)
* [JSON File Access](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/json)
* [String Separated File Access](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/scs)
* [Templates](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/templates)

These calls use also configurations with a permission part. 

### Under The Hood


<div align="right" style="font-size:10px"><a href="#page"><font size="2">top</font></a></div>


### Further documentation

An in depth documentation is created on this [github wiki](https://github.com/fluentcodes/elasticobjects/wiki) and the site http://elasticobjects.com



<div align="right" style="font-size:10px"><a href="#page"><font size="2">top</font></a></div>


### Packages
Actually you find here three modules deployed on [Maven Central](https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects).

#### elastic-objects
The [core](https://github.com/fluentcodes/elasticobjects/tree/master/elastic-objects) has actually no dependencies beside Log4j and is rather small with a jar size of approximately 160 KB.
```
    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>elastic-objects</artifactId>
        <version>0.2.0-SNAPSHOT</version>
    </dependency>
```

<div align="right" style="font-size:10px">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/elastic-objects">
<font size="1">mvn repository</font>
</a></div>

#### eo-csv
[eo-csv](https://github.com/fluentcodes/elasticobjects/tree/master/eo-csv) offers calls and configurations for reading and writing csv files using [OpenCsv](https://mvnrepository.com/artifact/com.opencsv/opencsv).


    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>eo-csv</artifactId>
        <version>0.2.0-SNAPSHOT</version>
    </dependency>

<div align="right" style="font-size:10px">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/eo-csv">
<font size="1">mvn repository</font>
</a></div>
#### eo-xlsx

[eo-xlsx](https://github.com/fluentcodes/elasticobjects/tree/master/eo-xlsx) offers calls and configurations for reading and writing xlsx files using [Apache POI](https://mvnrepository.com/artifact/org.apache.poi/poi).


    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>eo-xlsx</artifactId>
        <version>0.2.0-SNAPSHOT</version>
    </dependency>

<div align="right" style="font-size:10px">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/eo-xlsx">
<font size="1">mvn repository</font>
</a></div>

<div align="right" style="font-size:10px"><a href="#page"><font size="2">top</font></a></div>

### Status
After a lot of breaks the java version is now in a state I could accept as "fit" to the concept. It's basic mechanism works direct and with minimal implementation flourish.


### Links
* https://help.github.com/articles/licensing-a-repository/
