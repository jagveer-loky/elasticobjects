package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps.createEo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InterpreterAttributesTest {
    @Test
    public void callCallDirectiveNullThrowsEoException() {
        Assertions.assertThatThrownBy(()->{
            new InterpreterAttributes()
                    .setEo(createEo())
                    .call();
        }
        ).isInstanceOf(EoException.class);
    }

    @Test
    public void callNoEoReturnDefault() {
        String returnValue =
                (String) new InterpreterAttributes()
                            .setDefaultValue("default")
                            .call();
        assertEquals("default", returnValue);
    }

    @Test
    public void callCallDirectiveEmptyThrowsEoException() {
        Assertions.assertThatThrownBy(()->{
            new InterpreterAttributes()
                    .setCallDirective("")
                    .setEo(createEo())
                    .call();
                }
        ).isInstanceOf(EoException.class);
    }

    @Test
    public void callFileReadCall() {
        EO eo = createEo();
        final String directive = FileReadCall.class.getSimpleName() + " targetPath=\"test\" fileConfigKey=\"ContentExample\" ";
        Object result = new InterpreterAttributes()
                .setTemplateMarker(TemplateMarker.CURLY)
                .setCallDirective(directive)
                .setEo(eo)
                .call();
        assertNotNull(eo.get("test"));
    }
}
