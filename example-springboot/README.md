# Spring Boot Example

Target of this module is to create a runable spring boot web application with the help of templates.
A running copy you find in [elasticobjects.org](elasticobjects.org). Originally it's a copy of a wordpress page.

Acually it uses configurations from the modules
* [elsticobjects](https://github.com/fluentcodes/elasticobjects/tree/master/elastic-objects/src/main/resources)
* [eo-test](https://github.com/fluentcodes/elasticobjects/tree/master/eo-test/src/main/resources)

The starting point is the [Start class](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/Start.java).

The pages uses path parameters so the links are pure HTML links. The spring boot example is mostly a tool
to navigate through the used configurations.

### Web Controller
There are two Web-Controller classes defined.
* [WebEo](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/web/WebEo.java) defines two generic post end points
* [WebEoGet](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/web/WebEoGet.java) defines three special get end points using the path parameter to create an EO template call.

### ConfigController
The configurations are loaded in the [EOConfigCached.java](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/web/EOConfigCached.java)
as a singleton.

### Templates
#### Page Templates
There are two templates defined for rendering the page
* [Content.tpl](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/resources/input/content/Content.tpl) for the content
+ [Configs.tpl](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/resources/input/calls/configs/Configs.tpl) for navigate throught configuration

#### Content Templates
The content will be loaded as a template within the [Content.tpl](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/resources/input/content/Content.tpl)
from
* [docs](https://github.com/fluentcodes/elasticobjects/tree/master/example-springboot/src/main/resources/input/content/docs)
* [root](https://github.com/fluentcodes/elasticobjects/tree/master/example-springboot/src/main/resources/input/content/root)

#### Template Parts
Three part are loaded straigtforward:
* [Header.html](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/resources/input/web/header.html)
* [Footer.html](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/resources/input/web/Footer.html)

The [Svg.html](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/resources/input/web/Svg.html) for the responsiveness of the example site will be loaded as a file without parsing content.

