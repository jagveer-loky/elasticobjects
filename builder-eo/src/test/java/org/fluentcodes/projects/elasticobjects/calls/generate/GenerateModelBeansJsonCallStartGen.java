package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 20.11.2020.
 */
public class GenerateModelBeansJsonCallStartGen {
    @Test
    public void callTemplateResourceCall_JavaBuilderTpl__execute__logNoErrors() {
        GenerateModelBeansJsonCall call = new GenerateModelBeansJsonCall("eo.xlsx");
        call.setProjectDirectory("..");
        call.setLogLevel(LogLevel.INFO);
        EO eo = ProviderRootTestScope.createEo();
        eo.setLogLevel(LogLevel.INFO);
        String result = call.execute(eo);
        Assertions.assertThat(eo.hasErrors()).isFalse();
        System.out.println("Executed within " + call.getDuration() + " ms: " + result);
        System.out.println(eo.getLog());
    }
}
