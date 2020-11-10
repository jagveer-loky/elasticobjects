package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigReadCommand;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigReadWriteCommand;

/**
 * Created by werner.diwischek on 8.11.2020.
 */

public class FileReadWriteCall extends CallImpl implements ConfigReadWriteCommand {
    public static final String SOURCE_FILE_CONFIG_KEY = "sourceFileConfigKey";
    public static final String TARGET_FILE_CONFIG_KEY = "targetFileConfigKey";
    private String sourceFileConfigKey;
    private String targetFileConfigKey;
    private boolean compare;

    public FileReadWriteCall() {
        super();
        compare = false;
    }

    public FileReadWriteCall(final String sourceFileConfigKey, final String targetFileConfigKey) {
        super();
        this.sourceFileConfigKey = sourceFileConfigKey;
        this.targetFileConfigKey = targetFileConfigKey;
        compare = false;
    }

    @Override
    public Object execute(final EO eo)  {
        return write(eo, read(eo));
    }

    protected String read(final EO eo) {
        return (String)new FileReadCall(sourceFileConfigKey).execute(eo);
    }

    protected String write(final EO eo, final String content) {
        return new FileWriteCall(targetFileConfigKey)
                .setContent(content)
                .setCompare(compare)
                .execute(eo);
    }

    public String getSourceFileConfigKey() {
        return sourceFileConfigKey;
    }

    @Override
    public FileReadWriteCall setSourceFileConfigKey(String sourceFileConfigKey) {
        this.sourceFileConfigKey = sourceFileConfigKey;
        return this;
    }

    public String getTargetFileConfigKey() {
        return targetFileConfigKey;
    }

    @Override
    public FileReadWriteCall setTargetFileConfigKey(String targetFileConfigKey) {
        this.targetFileConfigKey = targetFileConfigKey;
        return this;
    }

    public boolean isCompare() {
        return compare;
    }

    public void setCompare(boolean compare) {
        this.compare = compare;
    }
}
