<call path="." control="rest"  targetFile="$[classUrl]" targetFileUse="1" />package $[packagePath];
<call jsonKey="BeanImport.tpl" keep="JAVA" />
<call jsonKey="BeanHeader.tpl" keep="JAVA" />
public interface $[modelKey] <call if="'$[superKey]'"> extends $[superKey] </call> {
 <call jsonKey="InterfacesBeanSetter.tpl" keep="JAVA" />
}
