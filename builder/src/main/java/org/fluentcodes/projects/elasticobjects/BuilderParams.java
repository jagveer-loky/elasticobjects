package org.fluentcodes.projects.elasticobjects;

import com.lexicalscope.jewel.cli.Option;

/**
 * Created by werner.diwischek on 16.02.18.
 * http://jewelcli.lexicalscope.com/usage.html nice documentation
 */
public interface BuilderParams {
    String PARAMS_DEFAULT_JSON = "ParamsJava.json";
    @Option(shortName = "c",  defaultValue = PARAMS_DEFAULT_JSON)
    String getCallJson();

    @Option(shortName = "t", defaultToNull = true)
    String getTargetFileConfigKey();
    default boolean hasTargetFileConfigKey() {return getTargetFileConfigKey() != null && ! getTargetFileConfigKey().isEmpty();}

    @Option(shortName = "s",  defaultToNull = true)
    String getSourceFileConfigKey();
    default boolean hasSourceFileConfigKey() {return getSourceFileConfigKey() != null && ! getSourceFileConfigKey().isEmpty();}

    @Option(shortName = "p",  defaultToNull = true)
    String getProjectDirectory();
    default boolean hasProjectDirectory() {
        return getProjectDirectory()!=null && !getProjectDirectory().isEmpty();
    }

    @Option(shortName = "y", defaultToNull = true)
    String getConfigType();
    default boolean hasConfigType() {
        return getConfigType()!=null && !getConfigType().isEmpty();
    }

    @Option(shortName = "m", defaultToNull = true)
    String getModule();
    default boolean hasModule() {
        return getModule()!=null && !getModule().isEmpty();
    }

    @Option(shortName = "f", defaultToNull = true)
    String getFileEnding();
    default boolean hasFileEnding() {
        return getFileEnding()!=null && !getFileEnding().isEmpty();
    }

    @Option(shortName = "o", defaultToNull = true)
    String getModuleScope();
    default boolean hasModuleScope() {
        return getModuleScope()!=null && !getModuleScope().isEmpty();
    }

    @Option(shortName = "n", defaultToNull = true)
    String getNaturalId();
    default boolean hasNaturalId() {
        return getNaturalId()!=null && !getNaturalId().isEmpty();
    }
}
