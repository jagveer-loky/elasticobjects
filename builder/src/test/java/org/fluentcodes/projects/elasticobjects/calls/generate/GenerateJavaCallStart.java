package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.HostCall;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigAsFlatListCall;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigCall;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigKeysCall;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigOpenApiCall;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigTypesCall;
import org.fluentcodes.projects.elasticobjects.calls.csv.CsvReadCall;
import org.fluentcodes.projects.elasticobjects.calls.csv.CsvWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.db.DbModelCall;
import org.fluentcodes.projects.elasticobjects.calls.db.DbModelDeleteCall;
import org.fluentcodes.projects.elasticobjects.calls.db.DbModelReadCall;
import org.fluentcodes.projects.elasticobjects.calls.db.DbModelWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.db.DbSqlCall;
import org.fluentcodes.projects.elasticobjects.calls.db.DbSqlExecuteCall;
import org.fluentcodes.projects.elasticobjects.calls.db.DbSqlReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryMapReadCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.GenerateJavaCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.JavaCommentCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.JavaExtendsCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.JavaFieldFinalCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.JavaFieldNotEmptyCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.JavaFieldOverrideCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.JavaFieldTypeCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.JavaImplementsCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.json.GenerateJsonConfigCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.calls.xlsx.XlsxReadCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Ignore;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.generate.GenerateCall.FILE_ENDING;
import static org.fluentcodes.projects.elasticobjects.domain.Base.NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.Config.MODULE;
import static org.fluentcodes.projects.elasticobjects.models.Config.MODULE_SCOPE;

/**
 * @author Werner Diwischek
 * @since 9.10.2020.
 */
public class GenerateJavaCallStart {
    @Test
    public void callTemplateResourceCall_JavaBuilderTpl__execute__logEmpty() {
        TemplateResourceCall call = new TemplateResourceCall("JavaBuilder.tpl");
        EO eo = ProviderRootTestScope.createEo();
        eo.set(".*", MODULE);
        eo.set(".*", MODULE_SCOPE);
        eo.set("", FILE_ENDING);
        eo.set(XlsxReadCall.class.getSimpleName() , NATURAL_ID);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("/java/org/");
        System.out.println(result);
    }
}
