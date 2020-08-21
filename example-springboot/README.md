Target of this repository is to show the some simple use cases to integrate 
[Elastic Objects](https://github.com/fluentcodes/elasticobjects) as a web service.

Acually it uses configurations from the 
* [core package](https://github.com/fluentcodes/elasticobjects/tree/master/elastic-objects/src/main/resources)
* [test package](https://github.com/fluentcodes/elasticobjects/tree/master/test-resources/src/main/resources)

### [ConfigController](https://github.com/fluentcodes/eo-example-springboot/blob/master/src/main/java/org/fluentcodes/projects/elasticobjects/web/ConfigController.java)
The configController just offers some endpoint to browse through available configurations.
Endpoints are 
 * &lt;url&gt;/config
 * &lt;url&gt;/config/&lt;configType&gt;
 * &lt;url&gt;/config/&lt;configType&gt;/&lt;key&gt;
 
### [EOController](https://github.com/fluentcodes/eo-example-springboot/blob/master/src/main/java/org/fluentcodes/projects/elasticobjects/web/EOController.java)
Here there is one endpoint defined for posting EO objects
 * &lt;url&gt;/eo
 
