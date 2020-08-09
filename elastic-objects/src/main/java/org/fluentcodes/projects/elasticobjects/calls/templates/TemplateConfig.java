package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.ExecutorCallList;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 02.07.2014.
 * Refactored 28.2.2015: Moved to adapter pattern
 */
public class TemplateConfig extends FileConfig {
    private final String templateKey;
    private final KeepCalls keepKey;
    private ExecutorCallList executorCallList;

    public TemplateConfig(EOConfigsCache configsCache, Builder builder) {
        super(configsCache, builder);
        this.templateKey = builder.templateKey;
        this.keepKey = builder.keep;
    }

    @Override
    public String getKey() {
        return templateKey;
    }

    public ExecutorCallList getExecutorCallList()  {
        if (executorCallList == null) {
            //executorList = new ExecutorListTemplate(createIO().read());//TODO
        }
        return executorCallList;
    }

    public String execute(EO execute, Map attributes)  {
        /*if (getExecutorList().isEmpty()) {
            execute.warn("Empty execution list for template " + templateKey);
            return "!!! Empty execution list for template " + templateKey + "!!!";
        }*/
        return getExecutorCallList().execute(execute);
    }

    public String getTemplateKey() {
        return templateKey;
    }

    public static class Builder extends FileConfig.Builder {
        private String templateKey;
        private KeepCalls keep;


        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            this.templateKey = (String) configsCache.transform(F_TEMPLATE_KEY, values);
            if (templateKey == null || templateKey.isEmpty()) {
                throw new EoException("templateKey is not defined in the map!?");
            }
            String keep = ScalarConverter.toString(values.get(A_KEEP));
            if (keep != null && !keep.isEmpty()) {
                this.keep = KeepCalls.valueOf(keep);
            }
            values.put(F_FILE_KEY, this.templateKey);
            super.prepare(configsCache, values);
        }

        @Override
        public TemplateConfig build(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            prepare(configsCache, values);
            return new TemplateConfig(configsCache, this);
        }
    }
}
