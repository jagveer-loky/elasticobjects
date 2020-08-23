$[(TemplateResourceCall). configKey="Header.html"/]
    <header class="entry-header">
        <div id="rootNav">
            <ul>
            $[(TemplateCall)navigationItem/* ]
            <li><a href="$[_value]">$[_value]</a></li>
            $[/]
            </ul>
        </div>
    </header>
    <div class="entry-content" id="entry-content">
        $[(TemplateResourceCall). configKey="eo->selectedItem."/]
       <hr/>
     </div>
$[(TemplateResourceCall). configKey="Footer.html"/]
