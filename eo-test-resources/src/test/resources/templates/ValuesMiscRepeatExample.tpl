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

<call loopPath="*">
  <call execute="ValuesMisc.repeat('#', arg1)" filterKey="/" />
</call>
