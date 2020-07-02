<call values="map">
[
    {
        "header":"header1"
    },
    {
        "header":"header2"
    },
    {
        "header":"nohead"
    },
     {
         "header":""
     },
    {
        "headerno":"nohead"
    }
]
</call>

eq:<call loopPath="*"><call if="header eq header2"><h1>$[header]</h1>
</call></call>
like:<call loopPath="*"><call if="header like header"><h1>$[header]</h1>
</call></call>
ex:<call loopPath="*"><call if="header ex"><h1>$[header]</h1>
</call></call>
nex:<call loopPath="*"><call if="headerno ex"><h1>$[headerno]</h1>
</call></call>
ex:<call loopPath="*"><call if="header nex"><h1>$[headerno]</h1>
</call></call>
nex:<call loopPath="*"><call if="headerno nex"><h1>$[header]</h1>
</call></call>
