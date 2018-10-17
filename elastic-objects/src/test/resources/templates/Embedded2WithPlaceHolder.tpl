$[string] --> <call
    loopPath="level*">$[_parentKey]/string=$[string] - $[stringRoot]: <call
        loopPath="level*">$[_parentKey]/string=$[string]: <call
            loopPath="level*" ignoreScalar="1">$[_parentKey]/string=$[string]</call></call></call>