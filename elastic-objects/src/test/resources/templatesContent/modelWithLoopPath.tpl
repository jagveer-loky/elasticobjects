Read config models:
<call execute="ConfigCall.set(Model)" />
Insert with loopPath="Model/*":
<call loopPath="Model/*">
    $[_parentKey] - $[modelKey]( $[eoParams/shapeType|])</call>