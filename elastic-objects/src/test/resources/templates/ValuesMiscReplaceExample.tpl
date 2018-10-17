<call values="map">
[
    {
        "arg1":3,
        "arg2":3
    },
    {
        "arg1":4,
        "arg2":3
    }
]
</call>
<call execute="ValuesMisc.replace(adapter,term)" mapPath="result" term="$[arg1] + $[arg2]" loopPath="*"/>
<call loopPath="*">
"$[result]"
</call>
