package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.ExecutorCallList;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;

/**
 * Created by Werner on 02.07.2014.
 * Refactored 28.2.2015: Moved to adapter pattern
 */
public class TemplateConfig extends FileConfig {
    public static final String KEEP_CALL = "keepCall";
    private final KeepCalls keepCall;
    private ExecutorCallList executorCallList;

    public TemplateConfig(EOConfigsCache configsCache, Builder builder) {
        super(configsCache, builder);
        this.keepCall = builder.keepCall;
    }

    public ExecutorCallList getExecutorCallList()  {
        if (executorCallList == null) {
            //executorList = new ExecutorListTemplate(createIO().read());//TODO
        }
        return executorCallList;
    }

    public KeepCalls getKeepCall() {
        return keepCall;
    }

    public String execute(EO execute, Map attributes)  {
        /*if (getExecutorList().isEmpty()) {
            execute.warn("Empty execution list for template " + templateKey);
            return "!!! Empty execution list for template " + templateKey + "!!!";
        }*/
        return getExecutorCallList().execute(execute);
    }

    public static class Builder extends FileConfig.Builder {
        private KeepCalls keepCall;


        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            String keepCall = ScalarConverter.toString(values.get(KEEP_CALL));
            if (keepCall != null && !keepCall.isEmpty()) {
                this.keepCall = KeepCalls.valueOf(keepCall);
            }
            super.prepare(configsCache, values);
        }

        @Override
        public TemplateConfig build(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            prepare(configsCache, values);
            return new TemplateConfig(configsCache, this);
        }
    }
}
