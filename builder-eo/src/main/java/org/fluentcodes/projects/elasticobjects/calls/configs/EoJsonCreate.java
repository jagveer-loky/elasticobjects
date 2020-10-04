package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.xlsx.XlsxReadCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

public class EoJsonCreate {
    public static final String DATA = "/data";
    public static final String XLSX_FILE_KEY = "eo.xlsx:";


    public static EO EXECUTE(final String configType, final String module) {
        EO eo = READ(configType, module);
        EoJsonCreateCall call = new EoJsonCreateCall(configType);
        call.setBuildPath("..");
        call.setFileEnding("json");
        if (module != null) {
            call.setModule(module);
        }
        System.out.println(call.execute(eo.getEo(DATA)));
        System.out.println(eo.getLog());
        return eo;
    }

    protected static EO READ(final String config, final String module) {
        XlsxReadCall call = new XlsxReadCall(EoXlsxRead.XLSX_FILE + config);
        if (module != null) {
            call.setFilter("module eq " + module);
        }
        call.setTargetPath(String.join(Path.DELIMITER,new String[]{ EoXlsxRead.DATA, "eo->module.", "eo->moduleScope.","eo->naturalId."}));

        EO eo = ProviderRootTestScope.createEo();
        System.out.println(call.execute(eo));
        System.out.println(eo.getLog());
        return eo;
    }
}
