package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.calls.TemplateCall;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.Executor;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorList;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorListTemplate;
import org.fluentcodes.projects.elasticobjects.utils.FileUtil;

import java.util.Map;

public class TestTemplateProvider {

    public static EO executeTemplateActionForAdapter(Map attributes, final String key)  {
        final TemplateCall templateAction = createTemplateCall(key);
        final EO adapter = TestEOProvider.create();
        templateAction.execute(adapter, attributes);
        return adapter;
    }

    public final static void assertExecutorTemplate(final String templateFile)  {
        final String template = FileUtil.readFile(templateFile);
        final ExecutorList executorList = new ExecutorListTemplate(template);
        final String result = executorList.execute(TestEOProvider.create());
        AssertEO.compare(result);
    }

    public final static void assertExecutorTemplate(final String templateFile, final EO adapter)  {
        final String template = FileUtil.readFile(templateFile);
        final ExecutorList executorList = new ExecutorListTemplate(template);
        final String result = executorList.execute(adapter);
        AssertEO.compare(result);
    }

    public static String executeTemplateAction(final String key, EO adapter)  {
        final TemplateCall action = new TemplateCall(TestEOProvider.EO_CONFIGS, key);
        return action.execute(adapter);
    }

    public static TemplateCall createTemplateCall(final String key)  {
        return new TemplateCall(TestEOProvider.EO_CONFIGS, key);
    }

    public static CallExecutor createExecutorTemplate(final String key, Object[]... attributeList)  {
        final Map attributes = EO_STATIC.toMap(attributeList);
        attributes.put(Executor.EXECUTE, TemplateCall.class.getSimpleName() + ".execute(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static String executeTemplateCall(final String key)  {
        TemplateCall call = new TemplateCall(TestEOProvider.EO_CONFIGS, key);
        return call.execute(TestEOProvider.create());
    }

    public static String executeTemplateAction(Map attributes, final String key)  {
        final TemplateCall executorAction = createTemplateCall(key);
        final EO adapter = TestEOProvider.create();
        return executorAction.execute(adapter, attributes);
    }
}
