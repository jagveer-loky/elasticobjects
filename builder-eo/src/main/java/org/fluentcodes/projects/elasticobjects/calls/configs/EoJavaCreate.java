package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.generate.GenerateJavaCall;
import org.fluentcodes.projects.elasticobjects.calls.xlsx.XlsxReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

public class EoJavaCreate {
    public static final String DATA = "/data";
    public static final String XLSX_FILE_KEY = "eo.xlsx:";
    public static EO EXECUTE(final String module, final String moduleScope, final String filter) {
        return EXECUTE(module, moduleScope, filter, false);
    }

    public static EO EXECUTE(final String module, final String moduleScope, final String filter, final boolean overWrite) {
        EO eo = READ_FOR_JAVA(module, moduleScope, filter);
        if (eo.getEo(ModelConfig.class.getSimpleName()).isEmpty()) {
            throw new EoException("Empty modelConfig entries for module='" + module + "' moduleScope='" + moduleScope + "' filter='" + filter + "'.");
        }
        GenerateJavaCall call = new GenerateJavaCall();
        call.setBuildPath("..");
        call.setFileEnding("javax");
        call.setModule(module);
        call.setOverwrite(overWrite);
        call.setModuleScope(moduleScope);
        call.setNaturalId(filter);
        System.out.println(call.execute(eo.getEo(ModelConfig.class.getSimpleName())));
        System.out.println(eo.getLog());
        return eo;
    }

    protected static EO READ_FOR_JAVA(final String module, final String moduleScope, final String filter) {
        XlsxReadCall call = new XlsxReadCall(EoXlsxRead.XLSX_FILE + ModelConfig.class.getSimpleName());
        call.setTargetPath(Path.DELIMITER + ModelConfig.class.getSimpleName() + Path.DELIMITER + "eo->naturalId.");

        EO eo = ProviderRootTestScope.createEo();
        System.out.println(call.execute(eo));

        call = new XlsxReadCall(EoXlsxRead.XLSX_FILE + FieldConfig.class.getSimpleName());
        call.setTargetPath(Path.DELIMITER + FieldConfig.class.getSimpleName() + Path.DELIMITER + "eo->naturalId.");
        System.out.println(call.execute(eo));
        System.out.println(eo.getLog());
        return eo;
    }
}
