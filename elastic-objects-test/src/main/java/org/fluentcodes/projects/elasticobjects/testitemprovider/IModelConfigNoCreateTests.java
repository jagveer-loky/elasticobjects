package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 18.11.2021.
 */
public interface IModelConfigNoCreateTests extends IModelConfigTests {

    void create_throwsEoException();

    default void assertCreateThrowingException() {
        Assertions
                .assertThatThrownBy(() -> {
                    getModelConfig().create();
                })
                .isInstanceOf(EoException.class)
                .hasMessageContaining("ModelConfig has no create flag -> no empty instance will created for '" + getModelConfig().getModelKey() + "'");
    }
}
