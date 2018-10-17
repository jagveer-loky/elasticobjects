package org.fluentcodes.projects.elasticobjects.executor.statics;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.ValueParamsHelper;

public class ValuesEO extends ValueParamsHelper {
    public static Object set(Object[] values) throws Exception {
        EO adapter = getAdapter(0, values);
        String path = getString(1, values);
        String value = getString(2, values);
        adapter.add(path).set(value);
        return "";
    }

}
