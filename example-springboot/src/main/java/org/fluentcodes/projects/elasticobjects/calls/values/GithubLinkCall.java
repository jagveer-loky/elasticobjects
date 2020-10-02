package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserEoReplace;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

/**
 * Created by Werner on 30.9.2020.
 */
public class GithubLinkCall extends SimpleValueFromEoCall {
    private String configType;
    private Class configClass;
    private String configKey;
    private Config config;
    private boolean configured = true;
    private boolean noLabel = false;
    private boolean noGithub = false;

    public GithubLinkCall() {
        super();
    }
    public GithubLinkCall(final String configKey) {
        this(configKey, "ModelConfig");
    }
    public GithubLinkCall(final String configKey, final String configType) {
        super();
        setTargetPath(TARGET_AS_STRING);
        this.configKey = configKey;
        this.configType = configType;
    }
    @Override
    public String execute(final EO eo) {
        super.check(eo);
        if (!hasConfigKey()) {
            throw new EoException("No key is set. Could not find value");
        }
        this.configKey = new ParserEoReplace(this.configKey).parse(eo);
        Class configClass = null;
        if (!hasConfigType() || configType.equals("ModelConfig")) {
            configClass = ModelConfig.class;
            configType= configClass.getSimpleName();
        }
        else if (configType.equals("FileConfig")) {
            configClass = FileConfig.class;
        }
        else if (configType.equals("FieldConfig")||configType.equals("HostConfig")||configType.equals("DbSqlConfig")) {
            // just a link within
        }
        else if (configType.equals("NONE")) {
            configured = false;
        }
        else {
            throw new EoException("Not a valid configType: '" + configType + "'.");
        }
        StringBuilder builder = new StringBuilder();
        if (configClass == null && configured) {
            return "";
        }

        builder.append("\n<a href=\"https://github.com/fluentcodes/elasticobjects/blob/master/");

        if (!configured) {
            String[] pathAndKey = configKey.split("\\|");
            if (pathAndKey.length!=3) {
                throw new EoException("A non configured github link call should has a delimiter '|' like 'filePath|fileName' but is '" + configKey + "'");
            }
            if (pathAndKey[0].equals("spring")) {
                builder.append("example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/");
            }
            else if (pathAndKey[0].equals("spring-test")) {
                builder.append("example-springboot/src/test/java/org/fluentcodes/projects/elasticobjects/");
            }
            else if (pathAndKey[0].equals("spring-resources")) {
                builder.append("example-springboot/src/main/resources/");
            }
            else if (pathAndKey[0].equals("sp-input")) {
                builder.append("example-springboot/input/");
            }
            else if (pathAndKey[0].equals("eo")) {
                builder.append("elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/");
            }
            else if (pathAndKey[0].equals("eo-resources")) {
                builder.append("elastic-objects/src/main/resources/");
            }
            else if (pathAndKey[0].equals("eo-xlsx-test")) {
                builder.append("eo-xlsx/src/test/java/org/fluentcodes/projects/elasticobjects/");
            }
            else if (pathAndKey[0].equals("eo-test-resources")) {
                builder.append("eo-test/src/test/resources/");
            }
            builder.append(pathAndKey[1].replaceAll("\\.", "/"));
            builder.append("/");
            builder.append(pathAndKey[2]);
            builder.append("\">&equiv;");
            builder.append(pathAndKey[2]);
            builder.append("</a>");
            return builder.toString();
        }
        this.config = (Config) eo.getConfigsCache().find(configClass, configKey);
        builder.append(config.getModule());
        if (configClass.equals(ModelConfig.class)) {
            builder.append("/src/");
            builder.append(config.getModuleScope());
            builder.append("/java/");
            builder.append(((ModelConfig) config).getPackagePath().replaceAll("\\.", "/"));
            builder.append("/");
            builder.append(((ModelConfig) config).getModelKey());
            builder.append(".java");
        } else if (configClass.equals(FileConfig.class)) {
            String filePath = ((FileConfig) config).getFilePath();
            if (filePath.startsWith("web") || filePath.startsWith("data")) {
                builder.append("/input/");
                builder.append(((FileConfig) config).getFilePath());
                builder.append("/");
                builder.append(((FileConfig) config).getFileName());
            }
            else {
                builder.append("/src/");
                builder.append(config.getModuleScope());
                builder.append("/resources/");
                builder.append(((FileConfig) config).getFilePath());
                builder.append("/");
                builder.append(((FileConfig) config).getFileName());
            }
        }
        builder.append("\">&equiv;");
        if (!noLabel) {
            builder.append(config.getNaturalId());
        }
        builder.append("</a>");
        return builder.toString();
    }

    public boolean hasConfigType() {
        return configType !=null && !configType.isEmpty();
    }
    public boolean hasConfigKey() {
        return configKey !=null && !configKey.isEmpty();
    }
    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    @Override
    public void setPathByTemplate(final Path path) {
        this.configKey = path.getFirstEntry();
    }

    public boolean isNoGithub() {
        return noGithub;
    }

    public void setNoGithub(boolean noGithub) {
        this.noGithub = noGithub;
    }

    protected Class getConfigClass() {
        return configClass;
    }

    protected Config getConfig() {
        return config;
    }
    protected void setNoLabel() {
        noLabel = true;
    }

    protected boolean isConfigured() {
        return configured;
    }
}
