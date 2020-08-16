package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.assets.BasicTest.TEST_OBJECT;

/**
 * Created by Werner on 04.11.2016.
 */
public class EoBtMapObjectTest {
    private static final Logger LOG = LogManager.getLogger(EoBtMapObjectTest.class);

    @Test
    public void givenTestBt_whenSetBTFieldWithScalar_thenHasLog()  {
        final EO eo = ProviderRootTestScope.createEo(new BasicTest());
        eo.mapObject(S_STRING);
        Assertions
                .assertThat(eo.getLog()).isNotEmpty();
    }


}
