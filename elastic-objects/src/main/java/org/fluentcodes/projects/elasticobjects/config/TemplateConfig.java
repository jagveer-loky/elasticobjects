package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorList;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorListTemplate;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 02.07.2014.
 * Refactored 28.2.2015: Moved to adapter pattern
 */
public class TemplateConfig extends FileConfig {
    private final String templateKey;
    private final KeepKeys keepKey;
    private ExecutorList executorList;

    public TemplateConfig(EOConfigsCache configsCache, Builder builder) {
        super(configsCache, builder);
        this.templateKey = builder.templateKey;
        this.keepKey = builder.keep;
    }

    @Override
    public String getKey() {
        return templateKey;
    }

    public ExecutorList getExecutorList() throws Exception {
        if (executorList == null) {
            executorList = new ExecutorListTemplate(createIO().read());
        }
        return executorList;
    }

    public String execute(EO adapter, Map attributes) throws Exception {
        if (getExecutorList().isEmpty()) {
            adapter.warn("Empty execution list for template " + templateKey);
            return "!!! Empty execution list for template " + templateKey + "!!!";
        }
        return getExecutorList().execute(adapter, attributes);
    }

    public String getTemplateKey() {
        return templateKey;
    }

    public enum KeepKeys {
        JAVA("\n//", " "), TARGET("", "");
        private String startComment;
        private String endComment;

        KeepKeys(String startComment, String endComment) {
            this.startComment = startComment;
            this.endComment = endComment;
        }

        public String getStartComment() {
            return startComment;
        }

        public String getEndComment() {
            return endComment;
        }
    }

    public static class Builder extends FileConfig.Builder {
        private String templateKey;
        private KeepKeys keep;


        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values) throws Exception {
            this.templateKey = (String) configsCache.transform(F_TEMPLATE_KEY, values);
            if (templateKey == null || templateKey.isEmpty()) {
                throw new Exception("templateKey is not defined in the map!?");
            }
            String keep = ScalarConverter.toString(values.get(A_KEEP));
            if (keep != null && !keep.isEmpty()) {
                this.keep = KeepKeys.valueOf(keep);
            }
            values.put(F_FILE_KEY, this.templateKey);
            super.prepare(configsCache, values);
        }

        @Override
        public TemplateConfig build(final EOConfigsCache configsCache, final Map<String, Object> values) throws Exception {
            prepare(configsCache, values);
            return new TemplateConfig(configsCache, this);
        }
    }
}
