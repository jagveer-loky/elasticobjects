package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.domain.Base;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_BOOLEAN;
import static org.fluentcodes.projects.elasticobjects.domain.Base.NATURAL_ID;

/**
 * Created by Werner on 27.8.2018.
 */
public class FieldPropertiesTest {

    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(FieldConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(FieldConfig.class);
    }

}
