[![Open Source Helpers](https://www.codetriage.com/fluentcodes/elasticobjects/badges/users.svg)](https://www.codetriage.com/fluentcodes/elasticobjects)

>... while you're programming, you are learning. It's often the case that it can take a year of programming on a project before you understand what the best design approach should have been ...
>
> -- <cite>Martin Fowler</cite>

A detailed documentation with interactive examples you find
[elasticobjects.org](http://www.elasticobjects.org/examples/ExamplesStart.html).
The documentation is build via generic spring boot endpoint and EO template calls.

# Elastic Objects
<p>
Elastic Objects is an object wrapper around the skeleton of a hierarchical Java object. It offers
<b>path</b>
methods to create, set or access objects.
</p>

    eoRoot.set(otherObject, "a/b/c");
    AnObject anObject2 = eoRoot.get("a/b/c");
    EO eoChild = eoRoot.getEo("a/b/c");
    AnObject anObject3 = eoChild.get();

<p>
    The path could be typed paths:
</p>

    eoRoot.set("value", "a/(AnObject)b/myString");

<p>
   And JSON serialization and deserialization uses the same pattern:
</p>
   {
       "a":{
           "(AnObject)b":{
               "myString":"value
           }
       }
   }
</p>
<p>
    In a service application a client is free to combine any typed data in a JSON message via a generic endpoint.
</p>
    {
        "(AnObject)input":{...},
        "(ACall)target:{"sourcePath":"input"}
    }
<p>
   Call allow to add functionality with in the same pattern, since calls are just beans with a
generic execute method.
</p>

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