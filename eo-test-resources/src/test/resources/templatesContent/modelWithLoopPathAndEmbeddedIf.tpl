Read config models:
<call execute="ConfigCall.set(Model)" />
Insert with loopPath="Model/*" and embeded if="modelKey eq Map":
<call loopPath="Model/*"><call
    if-loop="modelKey eq Map">
$[_parentKey] - $[modelKey]( $[eoParams/shapeType|])</call></call>