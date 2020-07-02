<call values="map">
[
    {
        "arg1":3
    },
    {
        "arg1":4
    }
]
</call>
<call execute="ValuesMath.sin(arg1)" mapPath="result" loopPath="*"/>
<call loopPath="*">
    sinus($[arg1]) = $[result]
</call>
