package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.assertj.core.api.Assertions;

/**
 * Created by Werner on 18.11.2021.
 */
public interface IModelConfigCreateTests extends IModelConfigTests {

    void create_noEoException();

    default void assertCreateNoException() {
        Object object = getModelConfig().create();
        Assertions.assertThat(object).isNotNull();
    }
}
