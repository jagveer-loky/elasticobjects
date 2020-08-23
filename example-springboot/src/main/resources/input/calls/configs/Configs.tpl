$[(ConfigTypesCall)configurationType /]
$[(ConfigKeysCall)configurationList configType="eo->configType." configFilter="eo->configFilter."/]
$[(ConfigCall)configuration configType="eo->configType." configFilter="eo->configSelected."/]

$[(TemplateResourceCall). configKey="Header.html"/]
    <header class="entry-header">
        <div id="rootNav">
            <input type="text" value="$[configFilter]" onchange="
            var newLocation = window.location + 'x';
            document.location.href = newLocation.replace(/=.*/, '=' + this.value);
            "/>
            <ul>
            $[(TemplateCall)configurationType/* ]
            <li><a href="/config/$[_value]/_?configFilter=$[/configFilter]">$[_value]</a>
            $[(TemplateCall)/configurationList/* condition="eo->_value. eq eo->/configType."]
                <br/> - <a href="/config/$[/configType]/$[_value]?configFilter=$[/configFilter]">$[_value]</a>
                $[/]
                </li>
                $[/]
            </ul>
        </div>
    </header>
    <div class="entry-content" id="entry-content">
        <xmp style="background-color:lightgray;border:1px solid darkgray;">$[(JsonAsStringCall)configuration/0 /]</xmp>

        <form action="/eo" method="POST">
        <textarea name="eo" cols="60" rows="6" style="background-color:red">
            ($[configuration/0/modelKeys])
        </textarea>
        <input type="submit" value="post"/>
       </form>
       <hr/>
     </div>
$[(TemplateResourceCall). configKey="Footer.html"/]
