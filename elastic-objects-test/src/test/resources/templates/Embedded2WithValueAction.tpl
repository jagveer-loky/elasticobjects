$[stringRoot] --> <call
    loopPath="level0">$[_parentKey]/string0=$[string] - $[stringRoot]: <call
        loopPath="level1">$[_parentKey]/string1=$[string]: <call
            loopPath="level2">$[_parentKey]/string2=$[string]<call
            execute="ValueCall.set(content)" />, content=$[content]</call></call></call>