<call execute="JsonCall.read(ModuleConfig.json)"/><call
loopPath="Module" >
short=$[short]
package=$[package]
dir=$[dir]
<call loopPath="sourceDirs/*" >
$[_parentKey]:
srcDir='$[srcDir]'
static:'$[static]'</call></call>