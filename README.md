
<div align="right" clear="left">
<a name="page"/>
<font size="2">
<a href="#path">Path</a><br>
<a href="#typed-json">Typed JSON</a><br>
<a href="#under-the-hood">Under the Hood</a><br>
<a href="#further-documentation">Further-Documentation</a><br>
<a href="#modules">Modules</a><br>
<a href="#status">Status</a><br>
</font>
</div>

>... while you're programming, you are learning. It's often the case that it can take a year of programming on a project before you understand what the best design approach should have been ...
>
> -- <cite>Martin Fowler</cite>

# Elastic Objects

Elastic Objects offers access to java objects via [path](#path).

It read and write typed [JSON](#typed-json) for string representation which offers
* [embedded type directives](#typed) for looseless data exchange e.g. "(BasicTest)fieldName":{...}
* [unmapped fields](#unmapped) for integration of extra information in JSON e.g. "_comment":"xyz"
* execution of [Call Beans](#calls) e.g. "(SinusValueCall)result":{...}

You find a running example with spring boot on [elasticobjects.org](elasticobjects.org). The sources are accessible in the module example-spingboot.

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

##### Embedded Objects
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
<a href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/assets/EoBtMapObjectTest.java">
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
<a href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/assets/EoBtMapObjectTest.java">
<font size="1">example</font>
</a></div>

EO will use the following unmapped fields:
* _logLevel: A log level with default WARN
* _errorLevel: The highest log level of messages
* _logs: List of messages.
* _calls: List of calls (see following)


##### Calls
Every type implementing [Call](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/Call.java)  could be executed.

In the following example JSON includes [SinusValueCall](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/values/SinusValueCall.java)  
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
* [List files](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/lists)
* [Templates](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/templates)

These calls use also configurations with a permission part.

Other calls using some frameworks will deployed separately. Actually you find here the modules for
* [CSV](https://github.com/fluentcodes/elasticobjects/blob/master/eo-csv)
* [Excel](https://github.com/fluentcodes/elasticobjects/blob/master/eo-xlsx)
* [JDBC](https://github.com/fluentcodes/elasticobjects/blob/master/eo-db)

A description and examples of these calls you will find on [github wiki](https://github.com/fluentcodes/elasticobjects/wiki) and the site http://elasticobjects.com.

<div align="right" style="font-size:10px"><a href="#page"><font size="2">top</font></a></div>

### Under The Hood

The type directives are just strings refering to model configurations. If a new EO-Object is created a reference to the configuration object [EOConfigsCache](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/models/EOConfigsCache.java)  is required.

It initially contains a map with  [ModelConfig](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/models/ModelConfig.java) and a map with [FieldConfig](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/models/FieldConfig.java) objects.

If the  [Scope](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/models/Scope.java)  is not DEV then all files called  [ModelConfig.json](https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/main/resources/ModelConfig.json) and  [FieldConfig.json](https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/main/resources/FieldConfig.json) are initially loaded from the class path.

To avoid defining configurations before using EO its also possible to create a simple class list with  [Model.json](https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/resources/Models.json).

The same procedure happens on demand with other configurations like [FileConfig.java](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/files/FileConfig.java) with e.g. [FileConfig.java](https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/main/resources/FileConfig.json)

#### ModelConfig
The configuration entry contains the list "fieldKeys" with field names. Those names reference to a field configuration. So field configurations can defined independent
of an underlying class.

The following example shows the configuration for [SubTest](https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/main/java/org/fluentcodes/projects/elasticobjects/assets/SubTest.java) class.

```
  "SubTest": {
    "packagePath": "org.fluentcodes.projects.elasticobjects.assets",
    "modelKey": "SubTest",
    "fieldKeys": [
      "id",
      "subTest",
      "name",
      "testString"
    ],
    "eoParams": {
      "shapeType": "BEAN",
      "create": true
    },
    "module": "eo-test",
    "subModule": "main",
    "author": "Werner Diwischek"
  }
```

#### FieldConfig
The following code shows the field definition for the subTest field with the Model [SubTest](https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/main/java/org/fluentcodes/projects/elasticobjects/assets/SubTest.java)
```
  "subTest": {
    "fieldKey": "subTest",
    "modelKeys": "SubTest",
    "module": "eo-test",
    "subModule": "main",
  }
```

#### Exchange Configurations
The model configuration are rather bloated since it's originally used to generate code from a list. The jsons are generated by itself, so module and submodule fields are required in the list. Since the configurations are loaded from the classpath these fields give a hint, from where the configuration comes from.

In the version 0.3.0 a more stream lined version for
configuration exchange is planned.

It could have similar structure than json scheme definition but will keep the separation of class and field definitions.


<div align="right" style="font-size:10px"><a href="#page"><font size="2">top</font></a></div>

### Further documentation

An in depth documentation is created on this [github wiki](https://github.com/fluentcodes/elasticobjects/wiki) and the site http://elasticobjects.com



<div align="right" style="font-size:10px"><a href="#page"><font size="2">top</font></a></div>


### Modules
Actually three modules are deployed to [Maven Central](https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects).

#### elastic-objects
The [core](https://github.com/fluentcodes/elasticobjects/tree/master/elastic-objects) has actually no dependencies beside Log4j and is rather small with a jar size of approximately 160 KB.
```
    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>elastic-objects</artifactId>
        <version>0.2.0</version>
    </dependency>
```

<div align="right" style="font-size:10px">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/elastic-objects">
<font size="1">mvn repository</font>
</a></div>

#### eo-test
The objectives [eo-test](https://github.com/fluentcodes/elasticobjects/tree/master/eo-test) is providing all tests for elastic-object module together with a main package providing test helper and test objects to other modules.

#### examples-springboot
[examples-springboot](https://github.com/fluentcodes/elasticobjects/tree/master/examples-springboot) are the sources for the spring boot web example on [elasticobjects.org](elasticobjects.org).

#### eo-csv
[eo-csv](https://github.com/fluentcodes/elasticobjects/tree/master/eo-csv) offers calls and configurations for reading and writing csv files using [OpenCsv](https://mvnrepository.com/artifact/com.opencsv/opencsv).


    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>eo-csv</artifactId>
        <version>0.2.0</version>
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
        <version>0.2.0</version>
    </dependency>

<div align="right" style="font-size:10px">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/eo-xlsx">
<font size="1">mvn repository</font>
</a></div>

<div align="right" style="font-size:10px"><a href="#page"><font size="2">top</font></a></div>

### Status
After a break of more than one year due to project work the java version is now in a state I would see as "fit to the concept". It's basic mechanism works direct and with minimal implementation flourish.

A simple [performance test](https://github.com/fluentcodes/elasticobjects/blob/fdab76ad2e593e4a4e5cf012986cc7852d6dfd8c/eo-test/src/test/resources/performance-Sun%20Aug%2016%2010:05:28%20CEST%202020.info) with [EOPerformance.java](https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/performance/EOPerformance.java) compare serialization and deserialization speed with
* [Jackson](https://github.com/FasterXML/jackson) and
* [Gson](https://github.com/google/gson)

```
Create 10000 times

** Map **
--> toJson
EO     : 2186 ms
Jackson: 126 ms
Gson   : 130 ms
<-- from JSON
EO     : 453 ms
Jackson: 136 ms
Gson   : 99 ms

```

Serialization with [EoToJson](https://github.com/fluentcodes/elasticobjects/blob/fdab76ad2e593e4a4e5cf012986cc7852d6dfd8c/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/EOToJSON.java) is about 20 times slower and Deserialization with [JsonToEo](https://github.com/fluentcodes/elasticobjects/blob/fdab76ad2e593e4a4e5cf012986cc7852d6dfd8c/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/JSONToEO.java) 5 times.

Performance had not the priority until now. The slowness in serialization is due to the fact, that the
object has to be wrapped by EO before serializing. Another reason is the slowness of StringBuilder. I suppose the change to StringWriter will offer a quick win.

Since EO allows more than just simple type mapping I suppose it will never reach the performance of the other frameworks.

### Links
* https://tech.signavio.com/2017/json-type-information
* https://www.json.org/json-en.html
* https://de.wikipedia.org/wiki/JSON-LD
* http://restcookbook.com/Mediatypes/json/
* https://gkulshrestha.wordpress.com/2013/11/16/embedding-type-information-in-json-posted-to-web-api/
* https://www.newtonsoft.com/json/help/html/T_Newtonsoft_Json_TypeNameHandling.htm
* https://de.wikipedia.org/wiki/JavaScript_Object_Notation
* http://jsonp.eu/
* https://github.com/json-path/JsonPath