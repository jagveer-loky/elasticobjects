<call execute="ConfigAction.set($[configType])" module="$[module]" subModule="$[subModule]" mapPath="config"/>
<call path="config"}{insert
    loopPath="*"}
    public static final $[prefix]<call execute="ValuesMisc.upper(_parentKey)" /> ="$[_parentKey]";</call></call>{insert
execute="Config.set($[configType])" module="$[module]" subModule="$[subModule]" mapPath="config"/>
