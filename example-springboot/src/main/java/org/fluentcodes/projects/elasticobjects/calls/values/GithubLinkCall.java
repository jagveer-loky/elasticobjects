package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserEoReplace;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

/**
 * Created by Werner on 30.9.2020.
 */
public class GithubLinkCall extends SimpleValueFromEoCall {
    private static final String GITHUB_PIC = " <img src=\"/pics/github.png\" height=\"12\" width=\"12\" \" style=\"margin:0px 4px 0px 6px;\"/>";
    private String configType;
    private String configKey;
    private Config config;
    private boolean configured = true;
    private boolean noLabel = false;
    private Boolean noGithub = false;

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
        this.configKey = Parser.replace(this.configKey, eo);
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

        builder.append("\n<nobreak><a target=\"github\" href=\"https://github.com/fluentcodes/elasticobjects/blob/master/");

        if (!configured) {
            String[] pathAndKey = configKey.split("\\|");
            if (pathAndKey.length!=3) {
                throw new EoException("A non configured github link call should has a delimiter '|' like 'filePath|fileName' but is '" + configKey + "'");
            }
            builder.append(Modules.findDirectory(pathAndKey[0]));
            builder.append(pathAndKey[1].replaceAll("\\.", "/"));
            builder.append("/");
            builder.append(pathAndKey[2]);
            builder.append("\">");
            builder.append(GITHUB_PIC);
            builder.append(pathAndKey[2]);
            builder.append("</a></nobreak>");
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
        builder.append("\">");
        builder.append(GITHUB_PIC);
        if (!noLabel) {
            builder.append(config.getNaturalId());
        }
        builder.append("</a></nobreak>");
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

    public Boolean isNoGithub() {
        return noGithub;
    }
    public Boolean getNoGithub() {
        return noGithub;
    }
    public void setNoGithub(Boolean noGithub) {
        this.noGithub = noGithub;
    }
    public void setNoGithub(boolean noGithub) {
        this.noGithub = noGithub;
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
