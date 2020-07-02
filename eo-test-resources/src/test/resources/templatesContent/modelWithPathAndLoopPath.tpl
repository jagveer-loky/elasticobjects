Read config models:
<call execute="ConfigCall.set(Model)" />
Insert with path="Model" and loopPath="*":
<call path="Model" loopPath="*">
    $[_parentKey] - $[modelKey]( $[eoParams/shapeType|])</call>