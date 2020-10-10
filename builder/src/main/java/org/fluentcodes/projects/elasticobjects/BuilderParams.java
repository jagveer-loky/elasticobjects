package org.fluentcodes.projects.elasticobjects;

import com.lexicalscope.jewel.cli.Option;

/**
 * Created by werner.diwischek on 16.02.18.
 * http://jewelcli.lexicalscope.com/usage.html nice documentation
 */
public interface BuilderParams {
    @Option(shortName = "b",  defaultToNull = true)
    String getBuilder();

    @Option(shortName = "p",  defaultToNull = true)
    String getBuildPath();

    @Option(shortName = "m", defaultToNull = true)
    String getConfigType();

    @Option(shortName = "m", defaultToNull = true)
    String getModule();
    default boolean hasModule() {
        return getModule()!=null && !getModule().isEmpty();
    }

    @Option(shortName = "s", defaultValue = "main")
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
