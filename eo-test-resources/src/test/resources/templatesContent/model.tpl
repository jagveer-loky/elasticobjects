Read config models:
<call execute="ConfigCall.set(Model)" />
Insert with loopPath="*" and path="Model":
<call path="Model" loopPath="*">
    $[parentKey] - $[modelKey]
</call>