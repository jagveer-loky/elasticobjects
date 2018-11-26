# Elastic Objects

Elastic Objects is a small applicaton java framework
for handling complex objects.
Basically its a wrapper
for to creating, accessing and modifing Java Object via keys/path.
A type safe and ordered JSON serialization and deserialization is provided.

With this object wrapper it's possible to write generic classes called calls
e.g. for file or db access.


## Synopsis
ElasticObject can be used directly in Java concatenating and accessing objects:
```
EO child = eo.add("level0/level1/level2/key")
   .set("value");
assertEquals("value", child.get();
assertEquals("value", eo.get("level0/level1/level2/key");
```


The serialization and deserialization is ordered so objects can be compared
```
eo.add()
   .map(jsonString);
String serialzed = EOToJSON(eo);
assertEquals(jsonString, serialized);
```


It can be used to convert Objects types via fieldnames.
```
final Map<String, Object> map = new HashMap<>();
map.put("testFloat", 1.1);
eo.add()
   .setModels(BasicTest.class)
   .map(map);
assertEquals(BasicTest.class, eo.getModelClass());
assertEquals(1.1F, eo.get("testFloat");
```


A special JSON format I use the file extension "jsn".
It's  used to store type information:
```
final String jsnString = "(BasicTest)_data\":{\"testFloat\":1.1}"
eo.add().map(jsnString);
assertEquals(BasicTest.class, eo.getModelClass());
assertEquals(1.1F, eo.get("testFloat");
```

### Calls
 Several type of calls using EO for loading primitive data type and template calls
 are available:

```
final TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE);
action.setContent("key='$[key]'<call path=\"level0/level1\">level0/level1/key='$[key]'</call>"template);

EO root = TestObjectProvider.createEO();
root.add("key").set("value");
root.add("level0/level1/key").set("value with path");

final String result = action.execute(root);
Assert.assertEquals(INFO_COMPARE_FAILS, "key='value'level0/level1/key='value with path'",result);
```

#### Calls with key
Despite of this simple direct java call example, calls are usually predefined
by configruation files referenced by a key. This accomplish serializing of
the calls e.g. for loading data.

```
final String result = TestCallsProvider.executeTemplateCall("SimpleInsertWithPathAndJson");
Assert.assertEquals("
key0='test'
Start call: path='level0' --> key0='testOther'  :End call
level0/key0='testOther'",result)
```

Here we reference the template 'SimpleInsertWithPath.tpl' by key:
```
<call execute="JsonCall.read(SimpleInsertWithPath)" />
key0='$[key0]'
Start call: <call path="level0">path='level0' --> key0='$[key0]' </call> :End call
level0/key0='$[level0/key0]'
```
It contains the call to read SimpleInsertWithPath.json:
```
{
  "key0": "test",
  "level0": {
    "key0": "testOther"
  }
}
```



### Web Integration
The core of elasticobject are the configurations which should be initialized only once.
One could do this in a static way as used it in the tests, since there
should be at least depencencies as possible.

In an application environment one can just inject the configurations
 as a singelton. I've
started a demo application with an endpoint eo receiving serialized
objects:

```
    @Value("${elasticobjects.scope:QS}")
    private String scope;
    @Autowired
    private EOConfigsCache cache;

    ....

    @RequestMapping(value = "/eo", method = RequestMethod.POST)
    public String adapterPost(
            @RequestParam(value = "eo", required = false) String eoAsString,
            @RequestParam(value = "logLevel", required = false) String logLevelAsString
    ) {
        if (eoAsString == null) {
            return "No 'eo' is set!";
        }
        if (eoAsString.isEmpty()) {
            return "'eo' is empty!";
        }

        String[] roles = getRoles();  // default value {"guest"}
        LogLevel logLevel = getLevel(logLevelAsString);  // default WARN

        try {
            final EORoot eo = (EORoot) new EOBuilder(configsCache)
                    .setLogLevel(logLevel)
                    .map(eoAsString);

            eo.setRoles(Arrays.asList(roles));  // roles are seet by application
            eo.executeCalls();

            final String result = new EOToJSON()
                    .setStartIndent(1)
                    .setSerializationType(JSONSerializationType.EO)
                    .toJSON(eo);
            return result;
        } catch (Exception e) {
            return "Exception occured! " + e.getMessage();
        }
    }

```
With this we have an generic endpoint, where requests like the following would be executed.
```
{
  "_calls": [
     {
       "execute": "ScsCall.read(source.csv)"
     }
  ]
}
```


## JAR
The jar has actually no dependencies beside Log4j and is rather small with approximately 250 KB.
The version 0.1.2 you find on [Maven Central](https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects).

```
    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>elastic-objects</artifactId>
        <version>0.1.2</version>
    </dependency>
```

## Documentation
A depth documentation is created on this [github wiki](https://github.com/fluentcodes/elasticobjects/wiki)
and the site http://elasticobjects.com

## Links
* https://help.github.com/articles/licensing-a-repository/
