[![Open Source Helpers](https://www.codetriage.com/fluentcodes/elasticobjects/badges/users.svg)](https://www.codetriage.com/fluentcodes/elasticobjects)

> A detailed documentation with interactive examples you find at [elasticobjects.org](http://www.elasticobjects.org/examples/ExamplesStart.html). The website is an example by itself
and build by one generic spring boot endpoint and EO template calls.

<h3>EO - Elastic Objects</h3>

<p>
Service oriented architecture today is usually some typed RPC calls bound to urls by
    an application server framework. <b>Type</b> and <b>functionality</b> mapping is stuck
    in a procedural concept
    from the IT stone age and fat web application frameworks.
</p>


<h5>Elastic Objects</h5>
<p>
    Elastic Objects is a generic object wrapper skin
    with <b>typed</b> path methods to an java object skeleton.
    Typed objects are embedded in a untyped map structure.
</p>
<img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/eoTree.svg" width="200" style="margin:20px;"/>
<h5>Model Configurations</h5>
    For the access to the embedded java objects EO
    is provided by preloaded <a href="http://elasticobjects.org/configs/ModelConfig.html">model configurations</a> in JSON.
</p>
<img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/eoModel.svg" width="200" style="margin:20px;"/>

<h5>Call Types</h5>
<p>A special
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/Call.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/ModelConfig/Call">&equiv;Call</a>
 bean with a
    generic execution method offers <b>functionality</b>. Its has the following important fields: </p>

<ul>
    <li>
<a href="http://elasticobjects.org/config/FieldConfig/sourcePath">&equiv;sourcePath</a>
 for the location of the input</li>
    <li>
<a href="http://elasticobjects.org/config/FieldConfig/targetPath">&equiv;targetPath</a>
 for storing the output</li>
    <li>
<a href="http://elasticobjects.org/config/FieldConfig/condition">&equiv;condition</a>
</li>
</ul>
<img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/eoCall.svg" width="200" style="margin:20px;"/>

<h5>Rich message Concept</h5>
<p>The corresponding JSON message with embedded type information
    allows a service architecture by one generic url. This type information
    trigger a typed object instantiation on the server.
</p>

<p>
    As a client one is free to combine data and calls in a JSON tree structure
    and the message send over one generic server endpoint.
</p>


<h5>Pseudo JSON Example</h5>

<p>
    The following pseudo code would call an execute method in the <b>ACall</b> instance,
    which uses the <b>AnObject</b> object provided in <i>input</i> path
    and store the result in <i>target</i> path.

    {
        "(AnObject)input":{...},
        "(ACall)target:{"sourcePath":"input"}
    }
</p>

<h5>A Real JSON Example</h5>
To demonstrate the possibilities, here an combined example:
<ul>
    <li>
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/lists/CsvSimpleReadCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/ModelConfig/CsvSimpleReadCall">&equiv;CsvSimpleReadCall</a>
 reads
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects-test/src/main/resources/input/assets/bt/AnObject.csv"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/FileConfig/AnObject.csv">&equiv;AnObject.csv</a>
 and store it under the path "/data/csv"</li>
    <li>
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/templates/TemplateResourceCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/ModelConfig/TemplateResourceCall">&equiv;TemplateResourceCall</a>
 use /data/csv as input
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/resources/templates/table.tpl"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/FileConfig/table.tpl">&equiv;table.tpl</a>
 and store it under the path "_asTemplate"</li>
</ul>
When you press the button, the /eo-form endpoint of
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/web/WebEo.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>WebEo.java</a></nobreak> will be called using the typed JSON as input.

    {
    "data": {
        "(CsvSimpleReadCall)csv":{
             "configKey"="AnObject.csv"
        },
        "(TemplateResourceCall)abc":{
            "configKey":"table.tpl",
            "sourcePath":"/data/csv",
            "targetPath":"/_asTemplate"
        }
    },
    "asTemplate":true
    }
<p>
If you remove the "asTemplate" value the result will be the JSON returned from the server.
</p>
<p>The
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/templates/TemplateResourceCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/ModelConfig/TemplateResourceCall">&equiv;TemplateResourceCall</a>
 is part of the core module.
    Templates are just files with certain placeholders .
</p>

<h5>A Real Template Example</h5>

<p>
    In <a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/web/WebEo.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>WebEo.java</a> has another endpoint "eo-template". Here
    one can send template content directly.
</p>

<p>
    In the following example all data read example calls are integrated in
    an editable template storing values in different paths:
</p>

<ul>
    <li>
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/lists/CsvSimpleReadCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/ModelConfig/CsvSimpleReadCall">&equiv;CsvSimpleReadCall</a>
 with
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects-test/src/main/resources/input/assets/bt/AnObject.csv"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/FileConfig/AnObject.csv">&equiv;AnObject.csv</a>
</li>
    <li>
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-xlsx/src/main/java/org/fluentcodes/projects/elasticobjects/calls/xlsx/XlsxReadCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/ModelConfig/XlsxReadCall">&equiv;XlsxReadCall</a>
 with
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/data/lists/AnObject.xlsx"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/FileConfig/AnObject.xlsx:test">&equiv;AnObject.xlsx:test</a>
</li>
    <li>
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-db/src/main/java/org/fluentcodes/projects/elasticobjects/calls/db/DbQueryCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/ModelConfig/DbQueryCall">&equiv;DbQueryCall</a>
 with
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-db/src/main/resources//DbSqlConfig.json"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>DbSqlConfig.json</a></nobreak></li>
</ul>
<p>
    After each reading the
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects-test/src/main/java/org/fluentcodes/projects/elasticobjects/domain/test/AnObject.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/ModelConfig/AnObject">&equiv;AnObject</a>
 example data a
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/templates/TemplateResourceCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/ModelConfig/TemplateResourceCall">&equiv;TemplateResourceCall</a>
 will render this
    data with
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/resources/templates/table.tpl"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/FileConfig/table.tpl">&equiv;table.tpl</a>
 again.
</p>

    <h1>An Example Template</h1>

    <h2>CSV</h2>
    $[(CsvSimpleReadCall)data/csv configKey="AnObject.csv" /]
    $[(TemplateResourceCall)data/csv configKey="table.tpl" /]

    <h2>Excel</h2>
    $[(XlsxReadCall)data/xlsx configKey="AnObject.xlsx:test" /]
    $[(TemplateResourceCall)data/xlsx configKey="table.tpl" /]

    <h2>DB</h2>
    $[(DbQueryCall)data/db configKey="h2:mem:basic:AnObject" /]
    $[(TemplateResourceCall)data/db configKey="table.tpl" /]


<h5>Demo Applications </h5>

<p>Beneath the <a href="http://elasticobjects.org/configs/ModelConfig.html">model configurations page</a> you find the available
    calls.</p>

<p>Some like
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/calls/values/ConfigLinkCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/ModelConfig/ConfigLinkCall">&equiv;ConfigLinkCall</a>
 are special for this web site,
    which is a demo by itself.</p>

<p>Another demo is
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/builder/src/main/java/org/fluentcodes/projects/elasticobjects//Builder.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>Builder.java</a></nobreak>. It generates
    json configurations and java classes from an
    Excelsheet
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/builder/src/main/resources//eo.xlsx"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>eo.xlsx</a></nobreak> with templates and calls.
</p>

<p>The calls are build for the requirements of these two demo applications. But calls are
    easy to extend as
described in <a href="http://elasticobjects.org/examples/TheGreetingCall.html">TheGreetingCall.html</a>
</p>

<p>
    The "configured calls" like
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/files/FileReadCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/></a></nobreak>
<a href="http://elasticobjects.org/config/ModelConfig/FileReadCall">&equiv;FileReadCall</a>
 use their own configuration.
    The values used here you can see in <a href="http://elasticobjects.org/configs/FileConfig.html">file</a> and
    <a href="http://elasticobjects.org/configs/DbSqlConfig.html">sql</a> configurations.
</p>

<h5>Other Examples</h5>

<p>
    Under <a href="http://elasticobjects.org/examples/ExamplesStart.html">examples</a>
    you find other working editable cases.
</p>
<p>
    Under the
<nobreak><a target="github" href="https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects-test/src/test/java/org/fluentcodes/projects/elasticobjects//."> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>.</a></nobreak>elastic-objects-test you find a lot of tests.
    Here other potential applications scenarios like
    <ul>
    <li>convert</li>
    <li>merge</li>
    <li>compare</li>
</ul>
native java objects are tested.

</p>

<h5>Conclusion</h5>
<p>
    The concept behind EO with typed JSON boosts the possibilities to create applications
to another level, even if its just a tiny extension. I miss something like this when I has to
work in micro services projects.
</p>

<p>
    The implementation for EO started some year ago creating source code with Excelsheets as data base and
    templates.

</p>
<p> I restarted work on EO this july, when a project was finished. It made
    good progress. With version 0.6.0
    it's not finished, but core parts are working proper and stable.
</p>

<p>
    In November another project starts and I will have lesser time.
</p>
<p>
    So I will focus on the code generation part with new templates in other programming languages and
    a separate demo application.
</p>
<p>
    With about 270 KByte its small enough to be integrated it in an application where it's appropriated. Other tool libraries like guava
    has more than 2MByte.
</p>


<!--# Elastic Objects
<p>
Elastic Objects is an object wrapper around the skeleton of hierarchical Java objects.
<a href="elasticobjects.org/config/AnObject">AnObject</a> is a real test class for this and that....
</p>

    AnObject anObject = new AnObject();
    anObject.setMyString("value");
    EoRoot eoRoot = new EoRoot(anObject);
<p>
It offers
<b>path</b>
methods to create, set or access objects.
</p>

    eoRoot.set(anObject, "a/b/c");
    AnObject anObject2 = eoRoot.get("a/b/c");
    EO eoChild = eoRoot.getEo("a/b/c");
    AnObject anObject3 = eoChild.get();

<p>
    The path could be a typed path:
</p>

    eoRoot.set("value", "a/(AnObject)b/myString");

<p>
   And JSON serialization and deserialization uses the same pattern:
</p>

    {
        "a":{
            "(AnObject)b":{
                 "myString":"value"
            }
        }
    }
</p>
<p>
    In a service application a client is free to combine any typed data in a JSON message
    by one generic endpoint.
</p>

    {
        "(AnObject)input":{...},
        "(ACall)target:{"sourcePath":"input"}
    }

<p>
   <a href="elasticobjects.org/config/Call">Calls</a> allow to add functionality with in the same pattern,
   since calls are just beans with a
generic execute method with EO as input.
</p>-->

### Modules
Actually three modules are deployed to [Maven Central](https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects).

#### elastic-objects
The [core](https://github.com/fluentcodes/elasticobjects/tree/master/elastic-objects) has actually no dependencies beside Log4j  
and is rather small with a jar size of approximately 270 KB. It already includes the template calls.
```
    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>elastic-objects</artifactId>
        <version>0.5.0</version>
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
        <version>0.5.0</version>
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
        <version>0.5.0</version>
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