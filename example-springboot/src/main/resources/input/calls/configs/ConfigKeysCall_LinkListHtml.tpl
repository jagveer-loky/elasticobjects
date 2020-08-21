$[(ConfigKeysCall)keys configType="eo->configType."/]
<html>
    <head>
    <title>Configuration keys for '$[configType]'</title>
    </head>
    <body>
        <a href="/config">Cached Configurations</a>
        <h1>Cached key for configuration '$[configType]' and scope 'TEST'</h1>
        <ul>
    $[(TemplateCall)keys/*]
        <li><a href="/config/$[/configType]/$[_value]">$[_value]</a></li>$[/]
        </ul>
    </body>
</html>