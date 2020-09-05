<call path="." control="rest"  targetFile="$[classUrl]" targetFileUse="1" />package $[packagePath];

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
<call jsonKey="BeanImport.tpl" keep="JAVA" />
<call jsonKey="BeanHeader.tpl" keep="JAVA" />
public class $[modelKey] <call if="'$[superKey|]'"> extends $[superKey] </call> <call if="'$[interfaces|]'"> implements $[interfaces] </call> {
 private static transient Logger LOG = LogManager.getLogger($[modelKey].class);

 <call jsonKey="BeanInstanceVars.tpl" keep="JAVA" />

 <call jsonKey="BeanSetter.tpl" keep="JAVA" />

}
