Read config models:
<call execute="ConfigCall.set(Model)" />
Insert with loopPath="Model/*" and embeded if="modelKey eq Map":
<call loopPath="Model/*"><call if="modelKey eq Map" prefix:="PREFIX_" templateKey="StaticValuesEntry.tpl" keep="JAVA"/></call>