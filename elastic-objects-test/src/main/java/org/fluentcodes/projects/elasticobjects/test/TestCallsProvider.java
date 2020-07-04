package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.calls.*;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.Executor;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_MAP_PATH;

/**
 * @author werner diwischek
 * @since 17.9. 2018
 */

public class TestCallsProvider {

    public static FileCall createFileCall(final String key)  {
        return new FileCall(TestEOProvider.EO_CONFIGS, key);
    }

    public static EO readFileCallEO(final String key, final String... attributeList)  {
        final EO eoEmpty = TestEOProvider.createEmptyMap();
        final Map attributes = EO_STATIC.toMap(attributeList);
        final FileCall call = new FileCall(TestEOProvider.EO_CONFIGS, key);
        call.read(eoEmpty, attributes);
        return eoEmpty;
    }

    public static CallExecutor createExecutorFileRead(final String key)  {
        return createExecutorFileRead(key, null);
    }

    public static CallExecutor createExecutorFileRead(final String key, final String... attributeList)  {
        final Map attributes = EO_STATIC.toMap(attributeList);
        attributes.put(Executor.EXECUTE, FileCall.class.getSimpleName() + ".read(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static EO executeExecutorFileRead(final String key, final String... attributeList)  {
        final CallExecutor callExecutor = createExecutorFileRead(key, attributeList);
        final EO eoEmpty = TestEOProvider.createEmptyMap();
        callExecutor.execute(eoEmpty);
        return eoEmpty;
    }

    public static EO writeFileCallEO(final String key, final String... attributeList)  {
        final EO eoEmpty = TestEOProvider.createEmptyMap();
        final Map attributes = EO_STATIC.toMap(attributeList);
        final FileCall call = new FileCall(TestEOProvider.EO_CONFIGS, key);
        call.write(eoEmpty, attributes);
        return eoEmpty;
    }

    public static CallExecutor createExecutorFileWrite(final String key)  {
        return createExecutorFileWrite(key, null);
    }

    public static CallExecutor createExecutorFileWrite(final String key, final String... attributeList)  {
        final Map attributes = EO_STATIC.toMap(attributeList);
        attributes.put(Executor.EXECUTE, FileCall.class.getSimpleName() + ".write(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static EO executeExecutorFileWrite(final String key, final String... attributeList)  {
        final CallExecutor callExecutor = createExecutorFileWrite(key, attributeList);
        final EO eoEmpty = TestEOProvider.createEmptyMap();
        callExecutor.execute(eoEmpty);
        return eoEmpty;
    }

    public static EO executeExecutorValueCall(final String key, final Object... attributeList)  {
        final CallExecutor callExecutor = ValueCall.createSetExecutor(key, attributeList);
        final EO eoEmpty = TestEOProvider.createEmptyMap();
        callExecutor.execute(eoEmpty);
        return eoEmpty;
    }

    public static ConfigCall createConfigCall(final String key)  {
        return new ConfigCall(TestEOProvider.EO_CONFIGS, key);
    }

    public static EO createConfigCallEO(final String key)  {
        final EO eoEmpty = TestEOProvider.createEmptyMap();
        final ConfigCall call = new ConfigCall(TestEOProvider.EO_CONFIGS, key);
        call.set(eoEmpty);
        return eoEmpty;
    }


    public static EO createConfigCallEO(final String key, final String... attributeList)  {
        final EO eoEmpty = TestEOProvider.createEmptyMap();
        final Map attributes = EO_STATIC.toMap(attributeList);
        final ConfigCall call = new ConfigCall(TestEOProvider.EO_CONFIGS, key);
        call.set(eoEmpty, attributes);
        return eoEmpty;
    }

    public static JsonCall createJsonCall(final String key)  {
        return new JsonCall(TestEOProvider.EO_CONFIGS, key);
    }

    public static ScsCall createScsCall(final String key)  {
        return new ScsCall(TestEOProvider.EO_CONFIGS, key);
    }

    public static Map setMapPath(final String mapPath) {
        Map map = new LinkedHashMap();
        map.put(F_MAP_PATH, mapPath);
        return map;
    }

    public static Map setMapPath(final Map map, final String mapPath) {
        map.put(F_MAP_PATH, mapPath);
        return map;
    }


}
