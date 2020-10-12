[![Open Source Helpers](https://www.codetriage.com/fluentcodes/elasticobjects/badges/users.svg)](https://www.codetriage.com/fluentcodes/elasticobjects)
<div align="right" clear="left">
<a name="page"/>
<font size="2">
<a href="#modules">Modules</a><br>
<a href="#status">Status</a><br>
</font>
</div>

>... while you're programming, you are learning. It's often the case that it can take a year of programming on a project before you understand what the best design approach should have been ...
>
> -- <cite>Martin Fowler</cite>

# Elastic Objects
<p>
Service oriented architecture today is usually some typed RPC calls bound to urls by
    an application server framework. <b>Type</b> and <b>functionality</b> mapping
    is stuck in a procedural concept
    from the IT stone age and fat web application frameworks.
</p>
<h4>Rich message Concept</h4>
<p>
    The idea of Elastic Objects is to embed <b>type</b> information in a JSON message
    passed by on generic url. The type
    information trigger an typed object instantiation on the server. A special type with a
    generic execution method offers <b>functionality</b>.
</p>
<p>
    The following pseudo code would call an execute method in the <b>ACall</b> instance,
    which uses the <b>AnObject</b> object provided in <i>input</i> and store the result in <i>target</i>.

    <textarea style="height:100px">
{
    "(AnObject)input":{...},
    "(ACall)target:{"sourcePath":"input"}
}
    </textarea>
</p>
<p>
    As a client one is free to combine data and calls in the object tree structure and send
    this object through one generic endpoint.
</p>
<p>
    And on the server side one can offer any functionality. Since the generic endpoint
    is not concerned doing something,
    there is a much better test coverage without application server framework.
</p>
## www.elasticobjects.org
A detailed documentation you will find
[elasticobjects.org](http://www.elasticobjects.org/examples/ExamplesStart.html).
This documentation is build by one generic spring boot endpoint and EO.

### Modules
Actually three modules are deployed to [Maven Central](https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects).

#### elastic-objects
The [core](https://github.com/fluentcodes/elasticobjects/tree/master/elastic-objects) has actually no dependencies beside Log4j and is rather small with a jar size of approximately 160 KB.
```
    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>elastic-objects</artifactId>
        <version>0.4.0</version>
    </dependency>
```

<div align="right" style="font-size:10px">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/elastic-objects">
<font size="1">mvn repository</font>
</a></div>

#### elastic-objects-test
The objectives [elastic-objects-test](https://github.com/fluentcodes/elasticobjects/tree/master/elastic-objects-test) is providing all tests for elastic-object module together with a main package providing test helper and test objects to other modules.

#### examples-springboot
[examples-springboot](https://github.com/fluentcodes/elasticobjects/tree/master/examples-springboot)
are the sources for the spring boot web example on
[elasticobjects.org](elasticobjects.org).

#### eo-csv
[eo-csv](https://github.com/fluentcodes/elasticobjects/tree/master/eo-csv) offers calls and configurations for reading and writing csv files using [OpenCsv](https://mvnrepository.com/artifact/com.opencsv/opencsv).


    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>eo-csv</artifactId>
        <version>0.4.0</version>
    </dependency>
    
    <div align="right" style="font-size:10px">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/eo-csv">
<font size="1">mvn repository</font>
</a></div>

#### eo-db
[examples-springboot](https://github.com/fluentcodes/elasticobjects/tree/master/eo-db)
is provide the execution of some sql configurations as list or as query.

#### eo-xlsx
[eo-xlsx](https://github.com/fluentcodes/elasticobjects/tree/master/eo-xlsx) offers calls and configurations for reading and writing xlsx files using [Apache POI](https://mvnrepository.com/artifact/org.apache.poi/poi).


    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>eo-xlsx</artifactId>
        <version>0.4.0</version>
    </dependency>

#### builder
[builder](https://github.com/fluentcodes/elasticobjects/tree/master/eo-xlsx) offers calls and configurations  
for generating sources like java classes or json configuration by a simple Excel file.

<div align="right" style="font-size:10px"><a href="#page"><font size="2">top</font></a></div>

### Status
After a break of more than one year due to project work the java version is now in a state I would see as "fit to the concept". It's basic mechanism works direct and with minimal implementation flourish.

A simple [performance test](https://github.com/fluentcodes/elasticobjects/blob/fdab76ad2e593e4a4e5cf012986cc7852d6dfd8c/elastic-objects-test/src/test/resources/performance-Sun%20Aug%2016%2010:05:28%20CEST%202020.info) with [EOPerformance.java](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects-test/src/test/java/org/fluentcodes/projects/elasticobjects/performance/EOPerformance.java) compare serialization and deserialization speed with
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