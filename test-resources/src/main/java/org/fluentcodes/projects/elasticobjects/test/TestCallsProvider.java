package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.calls.*;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorList;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorListTemplate;
import org.fluentcodes.projects.elasticobjects.utils.FileUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_MAP_PATH;

/**
 * @author werner diwischek
 * @since 17.9. 2018
 */

public class TestCallsProvider {

    public static FileCall createFileCall(final String key) throws Exception {
        return new FileCall(TestObjectProvider.EO_CONFIGS_CACHE, key);
    }

    public static EO readFileCallEO(final String key, final String... attributeList) throws Exception {
        final EO eoEmpty = TestObjectProvider.createEOFromJson();
        final Map attributes = MapProvider.toMap(attributeList);
        final FileCall call = new FileCall(TestObjectProvider.EO_CONFIGS_CACHE, key);
        call.read(eoEmpty, attributes);
        return eoEmpty;
    }

    public static CallExecutor createExecutorFileRead(final String key) throws Exception {
        return createExecutorFileRead(key, null);
    }

    public static CallExecutor createExecutorFileRead(final String key, final String... attributeList) throws Exception {
        final Map attributes = MapProvider.toMap(attributeList);
        attributes.put(ExecutorListTemplate.EXECUTE, FileCall.class.getSimpleName() + ".read(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static EO executeExecutorFileRead(final String key, final String... attributeList) throws Exception {
        final CallExecutor callExecutor = createExecutorFileRead(key, attributeList);
        final EO eoEmpty = TestObjectProvider.createEOFromJson();
        callExecutor.execute(eoEmpty);
        return eoEmpty;
    }

    public static EO writeFileCallEO(final String key, final String... attributeList) throws Exception {
        final EO eoEmpty = TestObjectProvider.createEOFromJson();
        final Map attributes = MapProvider.toMap(attributeList);
        final FileCall call = new FileCall(TestObjectProvider.EO_CONFIGS_CACHE, key);
        call.write(eoEmpty, attributes);
        return eoEmpty;
    }

    public static CallExecutor createExecutorFileWrite(final String key) throws Exception {
        return createExecutorFileWrite(key, null);
    }

    public static CallExecutor createExecutorFileWrite(final String key, final String... attributeList) throws Exception {
        final Map attributes = MapProvider.toMap(attributeList);
        attributes.put(ExecutorListTemplate.EXECUTE, FileCall.class.getSimpleName() + ".write(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static EO executeExecutorFileWrite(final String key, final String... attributeList) throws Exception {
        final CallExecutor callExecutor = createExecutorFileWrite(key, attributeList);
        final EO eoEmpty = TestObjectProvider.createEOFromJson();
        callExecutor.execute(eoEmpty);
        return eoEmpty;
    }

    public static CallExecutor createExecutorValueCall(final String key, final Object... attributeList) throws Exception {
        final Map attributes = MapProvider.toMap(attributeList);
        attributes.put(ExecutorListTemplate.EXECUTE, ValueCall.class.getSimpleName() + ".set(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static EO executeExecutorValueCall(final String key, final Object... attributeList) throws Exception {
        final CallExecutor callExecutor = createExecutorValueCall(key, attributeList);
        final EO eoEmpty = TestObjectProvider.createEOFromJson();
        callExecutor.execute(eoEmpty);
        return eoEmpty;
    }

    public static ValueCall createValueCall(final String key) throws Exception {
        return new ValueCall(TestObjectProvider.EO_CONFIGS_CACHE, key);
    }

    public static void createValueCallWithEO(final String key, final EO eo) throws Exception {
        ValueCall call = createValueCall(key);
        call.set(eo);
    }

    public static EO createValueCallWithEOEmpty(final String key) throws Exception {
        EO eoEmpty = TestObjectProvider.createEOFromJson();
        createValueCallWithEO(key, eoEmpty);
        return eoEmpty;
    }

    public static EO createValueCallEO(final String key, final String... attributeList) throws Exception {
        final EO eoEmpty = TestObjectProvider.createEOFromJson();
        final Map attributes = MapProvider.toMap(attributeList);
        final ValueCall call = new ValueCall(TestObjectProvider.EO_CONFIGS_CACHE, key);
        call.set(eoEmpty, attributes);
        return eoEmpty;
    }

    public static ConfigCall createConfigCall(final String key) throws Exception {
        return new ConfigCall(TestObjectProvider.EO_CONFIGS_CACHE, key);
    }

    public static EO createConfigCallEO(final String key) throws Exception {
        final EO eoEmpty = TestObjectProvider.createEOFromJson();
        final ConfigCall call = new ConfigCall(TestObjectProvider.EO_CONFIGS_CACHE, key);
        call.set(eoEmpty);
        return eoEmpty;
    }


    public static EO createConfigCallEO(final String key, final String... attributeList) throws Exception {
        final EO eoEmpty = TestObjectProvider.createEOFromJson();
        final Map attributes = MapProvider.toMap(attributeList);
        final ConfigCall call = new ConfigCall(TestObjectProvider.EO_CONFIGS_CACHE, key);
        call.set(eoEmpty, attributes);
        return eoEmpty;
    }

    public static JsonCall createJsonCall(final String key) throws Exception {
        return new JsonCall(TestObjectProvider.EO_CONFIGS_CACHE, key);
    }

    public static ScsCall createScsCall(final String key) throws Exception {
        return new ScsCall(TestObjectProvider.EO_CONFIGS_CACHE, key);
    }

    public static TemplateCall createTemplateCall(final String key) throws Exception {
        return new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, key);
    }

    public static CallExecutor createExecutorTemplate(final String key, Object[]... attributeList) throws Exception {
        final Map attributes = MapProvider.toMap(attributeList);
        attributes.put(ExecutorListTemplate.EXECUTE, TemplateCall.class.getSimpleName() + ".execute(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static String executeTemplateCall(final String key) throws Exception {
        TemplateCall call = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, key);
        return call.execute(TestObjectProvider.createEOFromJson());
    }

    public static String executeTemplateAction(Map attributes, final String key) throws Exception {
        final TemplateCall executorAction = createTemplateCall(key);
        final EO adapter = TestObjectProvider.createEOFromJson();
        return executorAction.execute(adapter, attributes);
    }

    public static String executeTemplateAction(final String key, EO adapter) throws Exception {
        final TemplateCall action = new TemplateCall(TestObjectProvider.EO_CONFIGS_CACHE, key);
        return action.execute(adapter);
    }

    public static EO executeTemplateActionForAdapter(Map attributes, final String key) throws Exception {
        final TemplateCall templateAction = createTemplateCall(key);
        final EO adapter = TestObjectProvider.createEOFromJson();
        templateAction.execute(adapter, attributes);
        return adapter;
    }

    public final static void assertExecutorTemplate(final String templateFile) throws Exception {
        final String template = FileUtil.readFile(templateFile);
        final ExecutorList executorList = new ExecutorListTemplate(template);
        final String result = executorList.execute(TestObjectProvider.createEOFromJson());
        AssertEO.compare(result);
    }

    public final static void assertExecutorTemplate(final String templateFile, final EO adapter) throws Exception {
        final String template = FileUtil.readFile(templateFile);
        final ExecutorList executorList = new ExecutorListTemplate(template);
        final String result = executorList.execute(adapter);
        AssertEO.compare(result);
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
